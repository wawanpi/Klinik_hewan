package com.example.klinikhewan.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.repository.DokterRepository
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiEntryDtr : DestinasiNavigasi {
    override val route = "item_entry" // Rute navigasi ke layar ini
    override val titleRes = "Entry Dokter" // Judul di TopAppBar
}

// ViewModel untuk proses Insert data Mahasiswa
class InsertDtrViewModel(private val dtr: DokterRepository) : ViewModel() {
    // State buat nampung inputan user
    var DtruiState by mutableStateOf(InsertDtrUiState())
        private set // Biar enggak diubah langsung dari luar

    // Update state input kalo ada event baru
    fun updateInsertDtrState(insertDtrUiEvent: InsertDtrUiEvent) {
        DtruiState = InsertDtrUiState(insertDtrUiEvent = insertDtrUiEvent)
    }

    // Fungsi buat insert data mahasiswa
    suspend fun insertDtr() {
        viewModelScope.launch { // Lari di background (asycn), biar UI gak ngelag
            try {
                dtr.insertDokter(DtruiState.insertDtrUiEvent.toDtr()) // Panggil fungsi insert dari repository
            } catch (e: Exception) {
                e.printStackTrace() // Error? Cetak aja, biar gampang debug
            }
        }
    }
}

// Data State buat nampung UI-nya
data class InsertDtrUiState(
    val insertDtrUiEvent: InsertDtrUiEvent = InsertDtrUiEvent() // Default kosong
)

// Event user: input NIM, Nama, Alamat, dll.
data class InsertDtrUiEvent(
    val id_dokter: String = "",
    val nama_dokter: String = "",
    val spesialisasi: String = "",
    val kontak: String = ""
)

// Konversi dari InsertUiEvent ke Mahasiswa (format backend)
fun InsertDtrUiEvent.toDtr(): Dokter = Dokter(
    id_dokter = id_dokter,
    nama_dokter = nama_dokter,
    spesialisasi = spesialisasi,
    kontak = kontak
)

// Konversi dari Mahasiswa ke InsertUiState (format UI)
fun Dokter.toUiStateDtr(): InsertDtrUiState = InsertDtrUiState(
    insertDtrUiEvent = tolnsertDtrUiEvent()
)

// Konversi dari Mahasiswa ke InsertUiEvent (event input UI)
fun Dokter.tolnsertDtrUiEvent(): InsertDtrUiEvent = InsertDtrUiEvent(
    id_dokter = id_dokter,
    nama_dokter = nama_dokter,
    spesialisasi = spesialisasi,
    kontak = kontak
)