package com.example.klinikhewan.ui.viewmodel.perawatan

import Pasien
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.model.Perawatan
import com.example.klinikhewan.repository.DokterRepository
import com.example.klinikhewan.repository.PasienRepository
import com.example.klinikhewan.repository.PerawatanRepository

import kotlinx.coroutines.launch


class InsertPrnViewModel(private val prn: PerawatanRepository,
                         private val psn: PasienRepository,
                         private val dtr: DokterRepository
) : ViewModel() {
    // State untuk menyimpan inputan user
    var PrnuiState by mutableStateOf(InsertPrnUiState())
        private set // Agar tidak diubah langsung dari luar
    var psnlist by mutableStateOf<List<Pasien>>(listOf())
    var dtrlist by mutableStateOf<List<Dokter>>(listOf())
        private set

    // Update state input ketika ada event baru
    fun updateInsertPrnState(insertPrnUiEvent: InsertPrnUiEvent) {
        PrnuiState = InsertPrnUiState(insertPrnUiEvent = insertPrnUiEvent)
    }

    // Fungsi untuk insert data pasien
    fun insertPrn() {
        viewModelScope.launch { // Jalankan di background (async) untuk mencegah UI lag
            try {
                prn.insertPerawatan(PrnuiState.insertPrnUiEvent.toPrn()) // Panggil fungsi insert dari repository
            } catch (e: Exception) {
                e.printStackTrace() // Jika ada error, cetak untuk debugging
            }
        }
    }
    fun getPsndanDtr() {
        viewModelScope.launch {
            try {
                val psndata = psn.getAllPasien()
                psnlist = psndata.data
                val dtrdata = dtr.getAllDokter()
                dtrlist = dtrdata.data
            }catch (e: Exception) {

            }
        }
    }
}

data class InsertPrnUiState(
    val insertPrnUiEvent: InsertPrnUiEvent = InsertPrnUiEvent() // Default kosong
)

data class InsertPrnUiEvent(
    val id_perawatan : String = "",
    val id_hewan : String = "",
    val id_dokter: String = "",
    val tanggal_perawatan: String = "",
    val detail_perawatan: String = ""
)
// Konversi dari InsertUiEvent ke Perawatan (format backend)
fun InsertPrnUiEvent.toPrn(): Perawatan = Perawatan(
    id_perawatan = id_perawatan,
    id_hewan = id_hewan,
    id_dokter = id_dokter,
    tanggal_perawatan = tanggal_perawatan,
    detail_perawatan = detail_perawatan
)

// Konversi dari Perawatan ke InsertUiState (format UI)
fun Perawatan.toUiStatePrn(): InsertPrnUiState = InsertPrnUiState(
    insertPrnUiEvent = toInsertPrnUiEvent()
)

// Konversi dari Perawatan ke InsertUiEvent (event input UI)
fun Perawatan.toInsertPrnUiEvent(): InsertPrnUiEvent = InsertPrnUiEvent(
    id_perawatan = id_perawatan,
    id_hewan = id_hewan,
    id_dokter = id_dokter,
    tanggal_perawatan = tanggal_perawatan,
    detail_perawatan = detail_perawatan
)