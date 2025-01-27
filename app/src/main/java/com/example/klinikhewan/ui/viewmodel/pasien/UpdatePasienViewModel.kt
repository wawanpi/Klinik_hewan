package com.example.klinikhewan.ui.viewmodel.pasien

import Pasien
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.repository.JenisHewanRepository
import com.example.klinikhewan.repository.PasienRepository
import com.example.klinikhewan.ui.view.pasien.DestinasiUpdatePsn
import kotlinx.coroutines.launch

class UpdatePsnViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn: PasienRepository,
    private val jh: JenisHewanRepository
) : ViewModel() {

    val id_hewan: String = checkNotNull(savedStateHandle[DestinasiUpdatePsn.ID_HEWAN])

    var psnuiState = mutableStateOf(InsertPsnUiState())
        private set
    var jhlist by mutableStateOf<List<JenisHewan>>(listOf())
        private set

    init {
        ambilPasien()
        fetchJenisHewan() // Tambahkan fetching jenis hewan
    }

    private fun ambilPasien() {
        viewModelScope.launch {
            try {
                val pasien = psn.getPasienById(id_hewan)
                pasien?.let {
                    psnuiState.value = it.toInsertPsnUIEvent()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchJenisHewan() {
        viewModelScope.launch {
            try {
                jhlist = jh.getAllJenisHewan().data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePsn(id_hewan: String, pasien: Pasien) {
        viewModelScope.launch {
            try {
                psn.updatePasien(id_hewan, pasien)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePsnState(insertPsnUiEvent: InsertPsnUiEvent) {
        val currentState = psnuiState.value
        psnuiState.value = currentState.copy(insertPsnUiEvent = insertPsnUiEvent)
    }
}

fun Pasien.toInsertPsnUIEvent(): InsertPsnUiState = InsertPsnUiState(
    insertPsnUiEvent = InsertPsnUiEvent(
        nama_hewan = this.nama_hewan,
        id_hewan = this.id_hewan,
        id_jenis_hewan = this.id_jenis_hewan ?: "",
        pemilik = this.pemilik,
        kontak_pemilik = this.kontak_pemilik,
        tanggal_lahir = this.tanggal_lahir ?: "",
        catatan_kesehatan = this.catatan_kesehatan ?: ""
    )
)
