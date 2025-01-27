package com.example.klinikhewan.ui.viewmodel.perawatan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.Perawatan
import com.example.klinikhewan.repository.PerawatanRepository
import com.example.klinikhewan.ui.view.perawatan.DestinasiPrnDetailPrn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class DetailPrnUiState {
    data class Success(val perawatan: Perawatan) : DetailPrnUiState()
    object Error : DetailPrnUiState()
    object Loading : DetailPrnUiState()
}

class DetailPrnViewModel(
    savedStateHandle: SavedStateHandle,
    private val prn: PerawatanRepository
) : ViewModel() {

    private val _id_perawatan: String = checkNotNull(savedStateHandle[DestinasiPrnDetailPrn.ID_PERAWATAN])

    // StateFlow untuk menyimpan status UI
    private val _detailPrnUiState = MutableStateFlow<DetailPrnUiState>(DetailPrnUiState.Loading)
    val detailPrnUiState: StateFlow<DetailPrnUiState> = _detailPrnUiState

    init {
        getDetailPerawatan()
    }

    fun getDetailPerawatan() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailPrnUiState.value = DetailPrnUiState.Loading

                // Fetch mahasiswa data dari repository
                val perawatan = prn.getPerawatanById(_id_perawatan)

                if (perawatan != null) {
                    // Jika data ditemukan, emit sukses
                    _detailPrnUiState.value = DetailPrnUiState.Success(perawatan)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailPrnUiState.value = DetailPrnUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailPrnUiState.value = DetailPrnUiState.Error
            }
        }
    }
}

//memindahkan data dari entity ke ui
fun Perawatan.toDetailPrnUiEvent(): InsertPrnUiEvent {
    return InsertPrnUiEvent(
        id_perawatan = id_perawatan,
        id_hewan = id_hewan,
        id_dokter = id_dokter,
        tanggal_perawatan = tanggal_perawatan,
        detail_perawatan = detail_perawatan
    )
}