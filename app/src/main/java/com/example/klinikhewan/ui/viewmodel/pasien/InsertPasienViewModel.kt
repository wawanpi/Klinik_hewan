package com.example.klinikhewan.ui.viewmodel.pasien

import Pasien
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.repository.JenisHewanRepository
import com.example.klinikhewan.repository.PasienRepository
import kotlinx.coroutines.launch

// ViewModel untuk proses Insert data Pasien
class InsertPsnViewModel(private val psn: PasienRepository,
    private val jh: JenisHewanRepository
) : ViewModel() {
    // State untuk menyimpan inputan user
    var PsnuiState by mutableStateOf(InsertPsnUiState())
        private set // Agar tidak diubah langsung dari luar
    var jhlist by mutableStateOf<List<JenisHewan>>(listOf())

    // Update state input ketika ada event baru
    fun updateInsertPsnState(insertPsnUiEvent: InsertPsnUiEvent) {
        PsnuiState = InsertPsnUiState(insertPsnUiEvent = insertPsnUiEvent)
    }

    // Fungsi untuk insert data pasien
    fun insertPsn() {
        viewModelScope.launch { // Jalankan di background (async) untuk mencegah UI lag
            try {
                psn.insertPasien(PsnuiState.insertPsnUiEvent.toPsn()) // Panggil fungsi insert dari repository
            } catch (e: Exception) {
                e.printStackTrace() // Jika ada error, cetak untuk debugging
            }
        }
    }
    fun getJenisHewan() {
        viewModelScope.launch {
            try {
                val jhdata = jh.getAllJenisHewan()
                jhlist = jhdata.data
            }catch (e: Exception) {

            }
        }
    }
}
// Data State untuk menyimpan UI-nya
data class InsertPsnUiState(
    val insertPsnUiEvent: InsertPsnUiEvent = InsertPsnUiEvent() // Default kosong
)

// Event user: input id_hewan, nama_hewan, jenis_hewan, dll.
data class InsertPsnUiEvent(
    val id_hewan: String = "",
    val nama_hewan: String = "",
    val id_jenis_hewan: String = "",
    val pemilik: String = "",
    val kontak_pemilik: String = "",
    val tanggal_lahir: String ="",
    val catatan_kesehatan: String = ""
)

// Konversi dari InsertUiEvent ke Pasien (format backend)
fun InsertPsnUiEvent.toPsn(): Pasien = Pasien(
    id_hewan = id_hewan,
    nama_hewan = nama_hewan,
    id_jenis_hewan = id_jenis_hewan,
    pemilik = pemilik,
    kontak_pemilik = kontak_pemilik,
    tanggal_lahir = tanggal_lahir,
    catatan_kesehatan = catatan_kesehatan
)

// Konversi dari Pasien ke InsertUiState (format UI)
fun Pasien.toUiStatePsn(): InsertPsnUiState = InsertPsnUiState(
    insertPsnUiEvent = toInsertPsnUiEvent()
)

// Konversi dari Pasien ke InsertUiEvent (event input UI)
fun Pasien.toInsertPsnUiEvent(): InsertPsnUiEvent = InsertPsnUiEvent(
    id_hewan = id_hewan,
    nama_hewan = nama_hewan,
    id_jenis_hewan = id_jenis_hewan,
    pemilik = pemilik,
    kontak_pemilik = kontak_pemilik,
    tanggal_lahir = tanggal_lahir,
    catatan_kesehatan = catatan_kesehatan
)
