package com.example.klinikhewan.ui.viewmodel.perawatan



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.Perawatan
import com.example.klinikhewan.repository.PerawatanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Status UI buat nampilin kondisi data: sukses, error, atau loading
sealed class HomePrnUiState {
    data class Success(val perawatan: List<Perawatan>) : HomePrnUiState() // Kalau data berhasil diambil
    object Error : HomePrnUiState() // Kalau ada error
    object Loading : HomePrnUiState() // Kalau lagi loading
}

class HomePrnViewModel(private val perawatanRepository: PerawatanRepository) : ViewModel() {
    // State untuk nyimpen kondisi UI
    var prnUIState: HomePrnUiState by mutableStateOf(HomePrnUiState.Loading)
        private set

    // Langsung ambil data pas ViewModel dibuat
    init {
        getPrn()
    }

    // Ambil data dokter dari repository
    fun getPrn() {
        viewModelScope.launch { // Coroutine biar proses ambil data di background
            prnUIState = HomePrnUiState.Loading // Kasih tau UI lagi loading

            prnUIState = try {
                val response = perawatanRepository.getAllPerawatan()
                HomePrnUiState.Success(response.data) // Kalau berhasil, set ke sukses
            } catch (e: IOException) {
                HomePrnUiState.Error // Kalau ada error karena koneksi
            } catch (e: HttpException) {
                HomePrnUiState.Error // Kalau error dari server
            }
        }
    }

    // Hapus dokter berdasarkan ID
    fun deletePrn(id_perawatan: String) {
        viewModelScope.launch { // Lagi-lagi coroutine biar lancar
            try {
                perawatanRepository.deletePerawatan(id_perawatan) // Hapus data di repo
            } catch (e: IOException) {
                prnUIState = HomePrnUiState.Error // Kalau koneksi bermasalah
            } catch (e: HttpException) {
                prnUIState = HomePrnUiState.Error // Kalau server error
            }
        }
    }
}
