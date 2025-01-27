package com.example.klinikhewan.ui.view.jenisHewan

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
import com.example.klinikhewan.ui.viewmodel.jenisHewan.InsertJhUiEvent
import com.example.klinikhewan.ui.viewmodel.jenisHewan.InsertJhUiState
import com.example.klinikhewan.ui.viewmodel.jenisHewan.InsertJhViewModel
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiEntryJh : DestinasiNavigasi {
    override val route = "item_entry jenis hewan" // Rute navigasi ke layar ini
    override val titleRes = "Entry Jenis Hewan" // Judul di TopAppBar
}

// Komponen utama layar Entry Mahasiswa
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryJhScreen(
    navigateBack: () -> Unit, // Fungsi navigasi balik
    modifier: Modifier = Modifier,
    viewModel: InsertJhViewModel = viewModel(factory = PenyediaViewModel.Factory) // ViewModel terintegrasi
) {
    val coroutineScope = rememberCoroutineScope() // Buat ngelola coroutine
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() // Scroll top bar efek smooth
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), // Scroll support buat UI
        topBar = { // AppBar custom
            CostumeTopAppBar(
                title = DestinasiEntryJh.titleRes, // Judul dari object DestinasiEntry
                canNavigateBack = true, // Tampilkan tombol back
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack // Aksi saat tombol back di klik
            )
        }
    ) { innerPadding ->
        EntryBodyJh(
            insertJhUiState = viewModel.JhuiState, // Data dari ViewModel
            onPasienValueChange = viewModel::updateInsertJhState, // Update state sesuai input
            onSaveClick = { // Simpan data dan navigasi balik
                coroutineScope.launch {
                    viewModel.insertJh() // Call fungsi save data di ViewModel
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
fun EntryBodyJh(
    insertJhUiState: InsertJhUiState, // Data state dari ViewModel
    onPasienValueChange: (InsertJhUiEvent) -> Unit, // Fungsi buat update state
    onSaveClick: () -> Unit, // Aksi saat tombol Simpan diklik
    modifier: Modifier = Modifier // Styling tambahan opsional
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp), // Jarak antar elemen
        modifier = modifier.padding(12.dp) // Padding biar rapi
    ) {
        FormInputJh( // Bagian form inputnya
            insertJhUiEvent = insertJhUiState.insertJhUiEvent,
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
fun FormInputJh(
    insertJhUiEvent: InsertJhUiEvent, // Data input user
    modifier: Modifier = Modifier,
    onValueChange: (InsertJhUiEvent) -> Unit = {}, // Fungsi buat update input
    enabled: Boolean = true // Apakah input bisa diedit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar input field
    ) {
        // Input Nama
        OutlinedTextField(
            value = insertJhUiEvent.nama_jenis_hewan,
            onValueChange = { onValueChange(insertJhUiEvent.copy(nama_jenis_hewan = it)) },
            label = { Text("Nama Jenis Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Input NIM
        OutlinedTextField(
            value = insertJhUiEvent.id_jenis_hewan,
            onValueChange = { onValueChange(insertJhUiEvent.copy(id_jenis_hewan = it)) },
            label = { Text("Masukan Id Jenis Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Input Jenis Kelamin
        OutlinedTextField(
            value = insertJhUiEvent.deskripsi,
            onValueChange = { onValueChange(insertJhUiEvent.copy(deskripsi = it)) },
            label = { Text("deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        
        // Divider buat pemisah
        Divider(
            thickness = 8.dp, // Tebal garis
            modifier = Modifier.padding(12.dp) // Jarak biar gak mepet
        )
    }
}