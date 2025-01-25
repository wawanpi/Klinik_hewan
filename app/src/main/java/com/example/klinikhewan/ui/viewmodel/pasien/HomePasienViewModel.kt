package com.example.klinikhewan.ui.viewmodel.pasien


import Pasien
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.repository.PasienRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Status UI buat nampilin kondisi data: sukses, error, atau loading
sealed class HomePsnUiState {
    data class Success(val pasien: List<Pasien>) : HomePsnUiState() // Kalau data berhasil diambil
    object Error : HomePsnUiState() // Kalau ada error
    object Loading : HomePsnUiState() // Kalau lagi loading
}

class HomePsnViewModel(private val pasienRepository: PasienRepository) : ViewModel() {
    // State untuk nyimpen kondisi UI
    var psnUIState: HomePsnUiState by mutableStateOf(HomePsnUiState.Loading)
        private set

    // Langsung ambil data pas ViewModel dibuat
    init {
        getPsn()
    }

    // Ambil data dokter dari repository
    fun getPsn() {
        viewModelScope.launch { // Coroutine biar proses ambil data di background
            psnUIState = HomePsnUiState.Loading // Kasih tau UI lagi loading

            psnUIState = try {
                val response = pasienRepository.getAllPasien()
                HomePsnUiState.Success(response.data) // Kalau berhasil, set ke sukses
            } catch (e: IOException) {
                HomePsnUiState.Error // Kalau ada error karena koneksi
            } catch (e: HttpException) {
                HomePsnUiState.Error // Kalau error dari server
            }
        }
    }

    // Hapus dokter berdasarkan ID
    fun deletePsn(id_hewan: String) {
        viewModelScope.launch { // Lagi-lagi coroutine biar lancar
            try {
                pasienRepository.deletePasien(id_hewan) // Hapus data di repo
            } catch (e: IOException) {
                psnUIState = HomePsnUiState.Error // Kalau koneksi bermasalah
            } catch (e: HttpException) {
                psnUIState = HomePsnUiState.Error // Kalau server error
            }
        }
    }
}
