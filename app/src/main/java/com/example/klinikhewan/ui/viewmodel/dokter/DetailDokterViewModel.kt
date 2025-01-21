package com.example.klinikhewan.ui.viewmodel.dokter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.repository.DokterRepository
import com.example.klinikhewan.ui.view.dokter.DestinasiDtrDetailDtr
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailDtrUiState {
    data class Success(val dokter: Dokter) : DetailDtrUiState()
    object Error : DetailDtrUiState()
    object Loading : DetailDtrUiState()
}

class DetailDtrViewModel(
    savedStateHandle: SavedStateHandle,
    private val dtr: DokterRepository
) : ViewModel() {

    private val _id: String = checkNotNull(savedStateHandle[DestinasiDtrDetailDtr.ID_DOKTER])

    // StateFlow untuk menyimpan status UI
    private val _detailDtrUiState = MutableStateFlow<DetailDtrUiState>(DetailDtrUiState.Loading)
    val detailDtrUiState: StateFlow<DetailDtrUiState> = _detailDtrUiState

    init {
        getDetailDokter()
    }

    fun getDetailDokter() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailDtrUiState.value = DetailDtrUiState.Loading

                // Fetch mahasiswa data dari repository
                val dokter = dtr.getDokterById(_id)

                if (dokter != null) {
                    // Jika data ditemukan, emit sukses
                    _detailDtrUiState.value = DetailDtrUiState.Success(dokter)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailDtrUiState.value = DetailDtrUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailDtrUiState.value = DetailDtrUiState.Error
            }
        }
    }
}

//memindahkan data dari entity ke ui
fun Dokter.toDetaiDtrlUiEvent(): InsertDtrUiEvent {
    return InsertDtrUiEvent(
        id_dokter = id_dokter,
        nama_dokter = nama_dokter,
        spesialisasi = spesialisasi,
        kontak = kontak
    )
}