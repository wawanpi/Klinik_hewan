package com.example.klinikhewan.ui.viewmodel.jenisHewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.repository.JenisHewanRepository
import kotlinx.coroutines.launch

// ViewModel untuk proses Insert data JenisHewan
class InsertJhViewModel(private val jh: JenisHewanRepository) : ViewModel() {
    // State buat nampung inputan user
    var JhuiState by mutableStateOf(InsertJhUiState())
        private set // Biar enggak diubah langsung dari luar

    // Update state input kalo ada event baru
    fun updateInsertJhState(insertJhUiEvent: InsertJhUiEvent) {
        JhuiState = InsertJhUiState(insertJhUiEvent = insertJhUiEvent)
    }

    // Fungsi buat insert data mahasiswa
    suspend fun insertJh() {
        viewModelScope.launch { // Lari di background (asycn), biar UI gak ngelag
            try {
                jh.insertJenisHewan(JhuiState.insertJhUiEvent.toJh()) // Panggil fungsi insert dari repository
            } catch (e: Exception) {
                e.printStackTrace() // Error? Cetak aja, biar gampang debug
            }
        }
    }
}

// Data State buat nampung UI-nya
data class InsertJhUiState(
    val insertJhUiEvent: InsertJhUiEvent = InsertJhUiEvent() // Default kosong
)

// Event user: input NIM, Nama, Alamat, dll.
data class InsertJhUiEvent(
    val id_jenis_hewan: String = "",
    val nama_jenis_hewan : String = "",
    val deskripsi : String = ""
)

// Konversi dari InsertUiEvent ke JenisHewan (format backend)
fun InsertJhUiEvent.toJh(): JenisHewan = JenisHewan(
    id_jenis_hewan = id_jenis_hewan,
    nama_jenis_hewan = nama_jenis_hewan,
    deskripsi = deskripsi

)

// Konversi dari JenisHewan ke InsertUiState (format UI)
fun JenisHewan.toUiStateJh(): InsertJhUiState = InsertJhUiState(
    insertJhUiEvent = tolnsertUiEvent()
)

// Konversi dari JenisHewan ke InsertUiEvent (event input UI)
fun JenisHewan.tolnsertUiEvent(): InsertJhUiEvent = InsertJhUiEvent(

)