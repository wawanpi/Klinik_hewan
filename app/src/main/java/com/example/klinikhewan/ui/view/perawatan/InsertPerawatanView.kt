package com.example.klinikhewan.ui.view.perawatan

import Pasien
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.perawatan.InsertPrnUiEvent
import com.example.klinikhewan.ui.viewmodel.perawatan.InsertPrnUiState
import com.example.klinikhewan.ui.viewmodel.perawatan.InsertPrnViewModel
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiEntryPrn : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Perawatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPrnScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPrnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    remember {
        coroutineScope.launch {
            viewModel.getPsndanDtr()
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPrn.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPrn(
            insertPrnUiState = viewModel.PrnuiState,
            onPerawatanValueChange = viewModel::updateInsertPrnState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPrn()
                    navigateBack()
                }
            },
            pasienList = viewModel.psnlist,
            dokterList = viewModel.dtrlist,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPrn(
    insertPrnUiState: InsertPrnUiState,
    onPerawatanValueChange: (InsertPrnUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    pasienList: List<Pasien>,
    dokterList: List<Dokter>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPrn(
            insertPrnUiEvent = insertPrnUiState.insertPrnUiEvent,
            onValueChange = onPerawatanValueChange,
            pasienList = pasienList,
            dokterList = dokterList,
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
fun FormInputPrn(
    insertPrnUiEvent: InsertPrnUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPrnUiEvent) -> Unit = {},
    pasienList: List<Pasien>,
    dokterList: List<Dokter>,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Dropdown untuk ID Hewan (Pasien)
        val expandedHewan = remember { mutableStateOf(false) }
        val selectedIdHewan = pasienList.find { it.id_hewan == insertPrnUiEvent.id_hewan }?.id_hewan ?: "Pilih ID Hewan"

        Column {

            OutlinedTextField(
                value = insertPrnUiEvent.id_perawatan,
                onValueChange = { onValueChange(insertPrnUiEvent.copy(id_perawatan = it)) },
                label = { Text("Masukan Id Perawatan") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )


            Text(text = "Pilih ID Hewan", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 8.dp))
            Button(onClick = { expandedHewan.value = true }, modifier = Modifier.fillMaxWidth()) {
                Text(text = selectedIdHewan)
            }

            DropdownMenu(expanded = expandedHewan.value, onDismissRequest = { expandedHewan.value = false }, modifier = Modifier.fillMaxWidth()) {
                pasienList.forEach { pasien ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(insertPrnUiEvent.copy(id_hewan = pasien.id_hewan))
                            expandedHewan.value = false
                        },
                        text = { Text(pasien.id_hewan) }
                    )
                }
            }
        }

        // Dropdown untuk ID Dokter
        val expandedDokter = remember { mutableStateOf(false) }
        val selectedIdDokter = dokterList.find { it.id_dokter == insertPrnUiEvent.id_dokter }?.id_dokter ?: "Pilih ID Dokter"

        Column {
            Text(text = "Pilih ID Dokter", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 8.dp))
            Button(onClick = { expandedDokter.value = true }, modifier = Modifier.fillMaxWidth()) {
                Text(text = selectedIdDokter)
            }

            DropdownMenu(expanded = expandedDokter.value, onDismissRequest = { expandedDokter.value = false }, modifier = Modifier.fillMaxWidth()) {
                dokterList.forEach { dokter ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(insertPrnUiEvent.copy(id_dokter = dokter.id_dokter))
                            expandedDokter.value = false
                        },
                        text = { Text(dokter.id_dokter) }
                    )
                }
            }
        }

        OutlinedTextField(
            value = insertPrnUiEvent.detail_perawatan,
            onValueChange = { onValueChange(insertPrnUiEvent.copy(detail_perawatan = it)) },
            label = { Text("Detail Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPrnUiEvent.tanggal_perawatan,
            onValueChange = { onValueChange(insertPrnUiEvent.copy(tanggal_perawatan = it)) },
            label = { Text("Tanggal Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        Divider(thickness = 8.dp, modifier = Modifier.padding(12.dp))
    }
}
