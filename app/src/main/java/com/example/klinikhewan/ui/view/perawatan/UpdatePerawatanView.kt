package com.example.klinikhewan.ui.view.perawatan

import Pasien
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.perawatan.InsertPrnUiEvent
import com.example.klinikhewan.ui.viewmodel.perawatan.InsertPrnUiState
import com.example.klinikhewan.ui.viewmodel.perawatan.UpdatePrnViewModel
import com.example.klinikhewan.ui.viewmodel.perawatan.toPrn
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiUpdatePrn : DestinasiNavigasi {
    override val route = "update"
    const val ID_PERWATAN = "id_perawatan"
    val routesWithArg = "$route/{$ID_PERWATAN}"
    override val titleRes = "Update Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePrnView(
    navigateBack: () -> Unit,
    id_perwatan: String,
    modifier: Modifier = Modifier,
    viewModel: UpdatePrnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val prnuiState = viewModel.prnUiState.value
    val psnList = viewModel.psnlist // Daftar pasien dari ViewModel
    val dtrList = viewModel.dtrlist // Daftar dokter dari ViewModel

    Scaffold(
        modifier = modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePrn.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            EntryBodyPrn(
                insertPrnUiState = prnuiState,
                psnList = psnList, // Kirim daftar pasien ke EntryBodyPrn
                dtrList = dtrList, // Kirim daftar dokter ke EntryBodyPrn
                onPasienValueChange = { updatedValue ->
                    viewModel.updatePrnState(updatedValue)
                },
                onSaveClick = {
                    coroutineScope.launch {
                        prnuiState.insertPrnUiEvent?.let {
                            viewModel.updatePrn(
                                id_perawatan = id_perwatan,
                                perawatan = it.toPrn()
                            )
                            navigateBack()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun EntryBodyPrn(
    insertPrnUiState: InsertPrnUiState,
    psnList: List<Pasien>,
    dtrList: List<com.example.klinikhewan.model.Dokter>,
    onPasienValueChange: (InsertPrnUiEvent) -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Dropdown untuk memilih pasien
        DropdownInput(
            label = "Pilih Pasien",
            items = psnList.map { it.id_hewan },
            selectedItem = insertPrnUiState.insertPrnUiEvent.id_hewan,
            onItemSelected = { onPasienValueChange(insertPrnUiState.insertPrnUiEvent.copy(id_hewan = it)) }
        )

        // Dropdown untuk memilih dokter
        DropdownInput(
            label = "Pilih Dokter",
            items = dtrList.map { it.id_dokter },
            selectedItem = insertPrnUiState.insertPrnUiEvent.id_dokter,
            onItemSelected = { onPasienValueChange(insertPrnUiState.insertPrnUiEvent.copy(id_dokter = it)) }
        )

        // Tombol simpan
        androidx.compose.material3.Button(
            onClick = onSaveClick,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            androidx.compose.material3.Text(text = "Simpan")
        }
    }
}

@Composable
fun DropdownInput(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    val expanded = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    Column {
        androidx.compose.material3.Text(text = label, modifier = Modifier.padding(bottom = 8.dp))
        androidx.compose.material3.Button(onClick = { expanded.value = true }) {
            androidx.compose.material3.Text(text = selectedItem.ifEmpty { "Pilih" })
        }
        androidx.compose.material3.DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            items.forEach { item ->
                androidx.compose.material3.DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded.value = false
                    },
                    text = { androidx.compose.material3.Text(text = item) }
                )
            }
        }
    }
}
