package com.example.klinikhewan.ui.view.pasien

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.InsertPsnUiEvent
import com.example.klinikhewan.ui.viewmodel.pasien.InsertPsnUiState
import com.example.klinikhewan.ui.viewmodel.pasien.InsertPsnViewModel
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.klinikhewan.model.JenisHewan

// Navigasi tujuan buat layar ini
object DestinasiEntryPsn : DestinasiNavigasi {
    override val route = "item_entry" // Rute navigasi ke layar ini
    override val titleRes = "Entry Pasien" // Judul di TopAppBar
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPsnScreen(
    navigateBack: () -> Unit, // Fungsi navigasi balik
    modifier: Modifier = Modifier,
    viewModel: InsertPsnViewModel = viewModel(factory = PenyediaViewModel.Factory) // ViewModel terintegrasi
) {
    val coroutineScope = rememberCoroutineScope() // Buat ngelola coroutine
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() // Scroll top bar efek smooth

    // Fetch jenis hewan saat layar dibuka
    remember {
        coroutineScope.launch {
            viewModel.getJenisHewan()
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPsn.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPsn(
            insertPsnUiState = viewModel.PsnuiState,
            onPasienValueChange = viewModel::updateInsertPsnState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPsn()
                    navigateBack()
                }
            },
            jenisHewanList = viewModel.jhlist, // Daftar jenis hewan dari ViewModel
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPsn(
    insertPsnUiState: InsertPsnUiState,
    onPasienValueChange: (InsertPsnUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    jenisHewanList: List<JenisHewan>, // Daftar jenis hewan untuk dropdown
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPsn(
            insertPsnUiEvent = insertPsnUiState.insertPsnUiEvent,
            onValueChange = onPasienValueChange,
            jenisHewanList = jenisHewanList,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInputPsn(
    insertPsnUiEvent: InsertPsnUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPsnUiEvent) -> Unit = {},
    jenisHewanList: List<JenisHewan>, // Jenis hewan untuk dropdown
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPsnUiEvent.nama_hewan,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(nama_hewan = it)) },
            label = { Text("Nama Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPsnUiEvent.id_hewan,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(id_hewan = it)) },
            label = { Text("Masukan Id Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Dropdown untuk Jenis Hewan
        val expanded = remember { mutableStateOf(false) } // Untuk kontrol dropdown
        val selectedIdJenisHewan = jenisHewanList.find { it.id_jenis_hewan == insertPsnUiEvent.id_jenis_hewan }?.id_jenis_hewan ?: "ID Jenis Hewan"

        Column {
            Text(

                text = "Pilih Id Jenis Hewan",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(
                onClick = { expanded.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedIdJenisHewan)
            }

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                jenisHewanList.forEach { jenisHewan ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(insertPsnUiEvent.copy(id_jenis_hewan = jenisHewan.id_jenis_hewan))
                            expanded.value = false
                        },
                        text = { Text(jenisHewan.id_jenis_hewan) }
                    )
                }
            }
        }

        OutlinedTextField(
            value = insertPsnUiEvent.pemilik,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(pemilik = it)) },
            label = { Text("Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPsnUiEvent.kontak_pemilik,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(kontak_pemilik = it)) },
            label = { Text("Kontak Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPsnUiEvent.tanggal_lahir,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(tanggal_lahir = it)) },
            label = { Text("Tanggal Lahir") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPsnUiEvent.catatan_kesehatan,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(catatan_kesehatan = it)) },
            label = { Text("Catatan Kesehatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
