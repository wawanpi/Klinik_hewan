package com.example.klinikhewan.ui.view.dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.dokter.InsertDtrUiEvent
import com.example.klinikhewan.ui.viewmodel.dokter.InsertDtrUiState
import com.example.klinikhewan.ui.viewmodel.dokter.InsertDtrViewModel
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

// Navigasi tujuan buat layar ini
object DestinasiEntryDtr : DestinasiNavigasi {
    override val route = "item_entry dokter" // Rute navigasi ke layar ini
    override val titleRes = "Entry Dokter" // Judul di TopAppBar
}
// Komponen utama layar Entry Mahasiswa
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDtrScreen(
    navigateBack: () -> Unit, // Fungsi navigasi balik
    modifier: Modifier = Modifier,
    viewModel: InsertDtrViewModel = viewModel(factory = PenyediaViewModel.Factory) // ViewModel terintegrasi
) {
    val coroutineScope = rememberCoroutineScope() // Buat ngelola coroutine
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() // Scroll top bar efek smooth
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), // Scroll support buat UI
        topBar = { // AppBar custom
            CostumeTopAppBar(
                title = DestinasiEntryDtr.titleRes, // Judul dari object DestinasiEntry
                canNavigateBack = true, // Tampilkan tombol back
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack // Aksi saat tombol back di klik
            )
        }
    ) { innerPadding ->
        EntryBodyDtr(
            insertDtrUiState = viewModel.DtruiState, // Data dari ViewModel
            onPasienValueChange = viewModel::updateInsertDtrState, // Update state sesuai input
            onSaveClick = { // Simpan data dan navigasi balik
                coroutineScope.launch {
                    viewModel.insertDtr() // Call fungsi save data di ViewModel
                    navigateBack() // Balik ke layar sebelumnya
                }
            },
            modifier = Modifier
                .padding(innerPadding) // Biar nggak ketabrak AppBar
                .verticalScroll(rememberScrollState()) // Support scroll buat form panjang
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyDtr(
    insertDtrUiState: InsertDtrUiState, // Data state dari ViewModel
    onPasienValueChange: (InsertDtrUiEvent) -> Unit, // Fungsi buat update state
    onSaveClick: () -> Unit, // Aksi saat tombol Simpan diklik
    modifier: Modifier = Modifier // Styling tambahan opsional
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp), // Jarak antar elemen
        modifier = modifier.padding(12.dp) // Padding biar rapi
    ) {
        FormInputDtr( // Bagian form inputnya
            insertDtrUiEvent = insertDtrUiState.insertDtrUiEvent,
            onValueChange =  onPasienValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button( // Tombol Simpan
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small, // Style tombol
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan") // Teks tombol
        }
    }
}

@Composable
fun FormInputDtr(
    insertDtrUiEvent: InsertDtrUiEvent, // Data input user
    modifier: Modifier = Modifier,
    onValueChange: (InsertDtrUiEvent) -> Unit = {}, // Fungsi buat update input
    enabled: Boolean = true // Apakah input bisa diedit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar input field
    ) {
        // Input Nama
        OutlinedTextField(
            value = insertDtrUiEvent.nama_dokter,
            onValueChange = { onValueChange(insertDtrUiEvent.copy(nama_dokter = it)) },
            label = { Text("Nama Dokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Input NIM
        OutlinedTextField(
            value = insertDtrUiEvent.id_dokter,
            onValueChange = { onValueChange(insertDtrUiEvent.copy(id_dokter = it)) },
            label = { Text("Masukan Id Dokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Input Jenis Kelamin
        OutlinedTextField(
            value = insertDtrUiEvent.spesialisasi,
            onValueChange = { onValueChange(insertDtrUiEvent.copy(spesialisasi = it)) },
            label = { Text("Spesialis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Input Alamat
        OutlinedTextField(
            value = insertDtrUiEvent.kontak,
            onValueChange = { onValueChange(insertDtrUiEvent.copy(kontak = it)) },
            label = { Text("Kontak") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


        // Reminder buat isi semua data
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp) // Biar rapi
            )
        }

        // Divider buat pemisah
        Divider(
            thickness = 8.dp, // Tebal garis
            modifier = Modifier.padding(12.dp) // Jarak biar gak mepet
        )
    }
}