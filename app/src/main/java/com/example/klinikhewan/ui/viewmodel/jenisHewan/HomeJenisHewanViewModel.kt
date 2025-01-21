package com.example.klinikhewan.ui.viewmodel.jenisHewan


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.repository.JenisHewanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Status UI buat nampilin kondisi data: sukses, error, atau loading
sealed class HomeJhUiState {
    data class Success(val jenisHewan: List<JenisHewan>) : HomeJhUiState() // Kalau data berhasil diambil
    object Error : HomeJhUiState() // Kalau ada error
    object Loading : HomeJhUiState() // Kalau lagi loading
}

class HomeJhViewModel(private val jh: JenisHewanRepository) : ViewModel() {
    // State untuk nyimpen kondisi UI
    var jhUIState: HomeJhUiState by mutableStateOf(HomeJhUiState.Loading)
        private set

    // Langsung ambil data pas ViewModel dibuat
    init {
        getJh()
    }

    // Ambil data jenisHewan dari repository
    fun getJh() {
        viewModelScope.launch { // Coroutine biar proses ambil data di background
            jhUIState = HomeJhUiState.Loading // Kasih tau UI lagi loading

            jhUIState = try {
                HomeJhUiState.Success(jh.getAllJenisHewan().data) // Kalau berhasil, set ke sukses
            } catch (e: IOException) {
                HomeJhUiState.Error // Kalau ada error karena koneksi
            } catch (e: HttpException) {
                HomeJhUiState.Error // Kalau error dari server
            }
        }
    }

    // Hapus jenisHewan berdasarkan NIM
    fun deleteJh(id_jenis_hewan: String) {
        viewModelScope.launch { // Lagi-lagi coroutine biar lancar
            try {
                jh.deleteJenisHewan(id_jenis_hewan) // Hapus data di repo
            } catch (e: IOException) {
                HomeJhUiState.Error // Kalau koneksi bermasalah
            } catch (e: HttpException) {
                HomeJhUiState.Error // Kalau server error
            }
        }
    }
}
