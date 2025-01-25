package com.example.klinikhewan.ui.viewmodel.perawatan

import Pasien
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.model.Perawatan
import com.example.klinikhewan.repository.DokterRepository
import com.example.klinikhewan.repository.PasienRepository
import com.example.klinikhewan.repository.PerawatanRepository
import com.example.klinikhewan.ui.view.perawatan.DestinasiUpdatePrn
import kotlinx.coroutines.launch

class UpdatePrnViewModel(
    savedStateHandle: SavedStateHandle,
    private val perawatanRepository: PerawatanRepository,
    private val pasienRepository: PasienRepository,
    private val dokterRepository: DokterRepository
) : ViewModel() {

    val id_perawatan: String = checkNotNull(savedStateHandle[DestinasiUpdatePrn.ID_PERWATAN])

    var prnUiState = mutableStateOf(InsertPrnUiState())
        private set

    var psnlist by mutableStateOf<List<Pasien>>(listOf())
        private set

    var dtrlist by mutableStateOf<List<Dokter>>(listOf())
        private set

    init {
        fetchPerawatan()
        fetchPasien()
        fetchDokter()
    }

    private fun fetchPerawatan() {
        viewModelScope.launch {
            try {
                val perawatan = perawatanRepository.getPerawatanById(id_perawatan)
                perawatan?.let {
                    prnUiState.value = it.toInsertPrnUiState()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchPasien() {
        viewModelScope.launch {
            try {
                psnlist = pasienRepository.getAllPasien().data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchDokter() {
        viewModelScope.launch {
            try {
                dtrlist = dokterRepository.getAllDokter().data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePrnState(insertPrnUiEvent: InsertPrnUiEvent) {
        val currentState = prnUiState.value
        prnUiState.value = currentState.copy(insertPrnUiEvent = insertPrnUiEvent)
    }

    fun updatePrn(id_perawatan: String, perawatan: Perawatan) {
        viewModelScope.launch {
            try {
                perawatanRepository.updatePerawatan(id_perawatan, perawatan)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun Perawatan.toInsertPrnUiState(): InsertPrnUiState = InsertPrnUiState(
    insertPrnUiEvent = InsertPrnUiEvent(
        id_perawatan = id_perawatan,
        id_hewan = id_hewan,
        id_dokter = id_dokter,
        tanggal_perawatan = tanggal_perawatan,
        detail_perawatan = detail_perawatan
    )
)
