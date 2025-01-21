package com.example.klinikhewan.ui.viewmodel.jenisHewan

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.repository.JenisHewanRepository
import com.example.klinikhewan.ui.view.jenisHewan.DestinasiUpdateJh

import kotlinx.coroutines.launch

class UpdateJhViewModel(
    savedStateHandle: SavedStateHandle,
    private val jh: JenisHewanRepository
) : ViewModel() {

    // Retrieve the NIM from SavedStateHandle
    val id_jenis_hewan: String = checkNotNull(savedStateHandle[DestinasiUpdateJh.ID_JENSI_HEWAN])

    var JhuiState = mutableStateOf(InsertJhUiState())
        private set

    init {
        ambilJenisHewan()
    }

    // Fetch the student data using NIM
    private fun ambilJenisHewan() {
        viewModelScope.launch {
            try {
                val jenisHewan = jh.getJenisHewanById(id_jenis_hewan)
                jenisHewan?.let {
                    JhuiState.value = it.toInsertJhUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the mahasiswa information
    fun updateJh(id_jenis_hewan: String, jenisHewan: JenisHewan) {
        viewModelScope.launch {
            try {
                jh.updateJenisHewan(id_jenis_hewan , jenisHewan)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertUiEvent
    fun updateJhState(insertJhUiEvent: InsertJhUiEvent) {
        JhuiState.value = JhuiState.value.copy(insertJhUiEvent = insertJhUiEvent)
    }
}

fun JenisHewan.toInsertJhUIEvent(): InsertJhUiState = InsertJhUiState(
    insertJhUiEvent = this.toDetailJhUiEvent()
)