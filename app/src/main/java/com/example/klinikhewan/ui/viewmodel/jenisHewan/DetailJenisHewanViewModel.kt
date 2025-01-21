package com.example.klinikhewan.ui.viewmodel.jenisHewan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.repository.JenisHewanRepository
import com.example.klinikhewan.ui.view.jenisHewan.DestinasiJhDetailJh
import com.example.klinikhewan.ui.viewmodel.jenisHewan.InsertJhUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class DetailJhUiState {
    data class Success(val jenisHewan: JenisHewan) : DetailJhUiState()
    object Error : DetailJhUiState()
    object Loading : DetailJhUiState()
}

class DetailJhViewModel(
    savedStateHandle: SavedStateHandle,
    private val jh: JenisHewanRepository
) : ViewModel() {

    private val _id_jenis_hewan: String = checkNotNull(savedStateHandle[DestinasiJhDetailJh.ID_JENSI_HEWAN])

    // StateFlow untuk menyimpan status UI
    private val _detailJhUiState = MutableStateFlow<DetailJhUiState>(DetailJhUiState.Loading)
    val detailJhUiState: StateFlow<DetailJhUiState> = _detailJhUiState

    init {
        getDetailJenisHewan()
    }

    fun getDetailJenisHewan() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailJhUiState.value = DetailJhUiState.Loading

                // Fetch mahasiswa data dari repository
                val jenisHewan = jh.getJenisHewanById(_id_jenis_hewan)

                if (jenisHewan != null) {
                    // Jika data ditemukan, emit sukses
                    _detailJhUiState.value = DetailJhUiState.Success(jenisHewan)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailJhUiState.value = DetailJhUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailJhUiState.value = DetailJhUiState.Error
            }
        }
    }
}

//memindahkan data dari entity ke ui
fun JenisHewan.toDetailJhUiEvent(): InsertJhUiEvent {
    return InsertJhUiEvent(
        id_jenis_hewan = id_jenis_hewan,
        nama_jenis_hewan = nama_jenis_hewan,
        deskripsi = deskripsi
    )
}