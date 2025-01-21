package com.example.klinikhewan.ui.viewmodel.dokter

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.repository.DokterRepository
import com.example.klinikhewan.ui.view.dokter.DestinasiDtrDetailDtr
import kotlinx.coroutines.launch

class UpdateDtrViewModel(
    savedStateHandle: SavedStateHandle,
    private val dtr: DokterRepository
) : ViewModel() {

    // Retrieve the NIM from SavedStateHandle
    val id_dokter: String = checkNotNull(savedStateHandle[DestinasiDtrDetailDtr.ID_DOKTER])

    var DtruiState = mutableStateOf(InsertDtrUiState())
        private set

    init {
        ambilDokter()
    }

    // Fetch the student data using NIM
    private fun ambilDokter() {
        viewModelScope.launch {
            try {
                val dokter = dtr.getDokterById(id_dokter)
                dokter?.let {
                    DtruiState.value = it.toInsertDtrUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the mahasiswa information
    fun updateDtr(id_dokter: String, dokter: Dokter) {
        viewModelScope.launch {
            try {
                dtr.updateDokter(id_dokter, dokter)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertUiEvent
    fun updateDtrState(insertDtrUiEvent: InsertDtrUiEvent) {
        DtruiState.value = DtruiState.value.copy(insertDtrUiEvent = insertDtrUiEvent)
    }
}

fun Dokter.toInsertDtrUIEvent(): InsertDtrUiState = InsertDtrUiState(
    insertDtrUiEvent = this.toDetaiDtrlUiEvent()
)