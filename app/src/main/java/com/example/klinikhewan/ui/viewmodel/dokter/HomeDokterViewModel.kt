package com.example.klinikhewan.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.repository.DokterRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeDtrUiState {
    data class Success(val dokter: List<Dokter>) : HomeDtrUiState() // Kalau data berhasil diambil
    object Error : HomeDtrUiState() // Kalau ada error
    object Loading : HomeDtrUiState() // Kalau lagi loading
}

class HomeDtrViewModel(private val dokterRepository: DokterRepository) : ViewModel() {
    // State untuk nyimpen kondisi UI
    var dtrUIState: HomeDtrUiState by mutableStateOf(HomeDtrUiState.Loading)
        private set

    // Langsung ambil data pas ViewModel dibuat
    init {
        getDtr()
    }

    // Ambil data dokter dari repository
    fun getDtr() {
        viewModelScope.launch { // Coroutine biar proses ambil data di background
            dtrUIState = HomeDtrUiState.Loading // Kasih tau UI lagi loading

            dtrUIState = try {
                val response = dokterRepository.getAllDokter()
                HomeDtrUiState.Success(response.data) // Kalau berhasil, set ke sukses
            } catch (e: IOException) {
                HomeDtrUiState.Error // Kalau ada error karena koneksi
            } catch (e: HttpException) {
                HomeDtrUiState.Error // Kalau error dari server
            }
        }
    }

    // Hapus dokter berdasarkan ID
    fun deleteDtr(id_dokter: String) {
        viewModelScope.launch { // Lagi-lagi coroutine biar lancar
            try {
                dokterRepository.deleteDokter(id_dokter) // Hapus data di repo
            } catch (e: IOException) {
                dtrUIState = HomeDtrUiState.Error // Kalau koneksi bermasalah
            } catch (e: HttpException) {
                dtrUIState = HomeDtrUiState.Error // Kalau server error
            }
        }
    }
}