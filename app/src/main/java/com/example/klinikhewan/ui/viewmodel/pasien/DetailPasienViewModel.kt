package com.example.klinikhewan.ui.viewmodel.pasien

import Pasien
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.repository.PasienRepository
import com.example.klinikhewan.ui.view.pasien.DestinasiPsnDetailPsn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailPsnUiState {
    data class Success(val pasien: Pasien) : DetailPsnUiState()
    object Error : DetailPsnUiState()
    object Loading : DetailPsnUiState()
}

class DetailPsnViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn: PasienRepository
) : ViewModel() {

    private val _id_hewan: String = checkNotNull(savedStateHandle[DestinasiPsnDetailPsn.ID_HEWAN])

    // StateFlow untuk menyimpan status UI
    private val _detailPsnUiState = MutableStateFlow<DetailPsnUiState>(DetailPsnUiState.Loading)
    val detailPsnUiState: StateFlow<DetailPsnUiState> = _detailPsnUiState

    init {
        getDetailPasien()
    }

    fun getDetailPasien() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailPsnUiState.value = DetailPsnUiState.Loading

                // Fetch mahasiswa data dari repository
                val pasien = psn.getPasienById(_id_hewan)

                if (pasien != null) {
                    // Jika data ditemukan, emit sukses
                    _detailPsnUiState.value = DetailPsnUiState.Success(pasien)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailPsnUiState.value = DetailPsnUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailPsnUiState.value = DetailPsnUiState.Error
            }
        }
    }
}

//memindahkan data dari entity ke ui
fun Pasien.toDetailPsnUiEvent(): InsertPsnUiEvent {
    return InsertPsnUiEvent(
        id_hewan = id_hewan,
        nama_hewan = nama_hewan,
        id_jenis_hewan = id_jenis_hewan,
        pemilik = pemilik,
        kontak_pemilik = kontak_pemilik,
        tanggal_lahir = tanggal_lahir,
        catatan_kesehatan = catatan_kesehatan
    )
}