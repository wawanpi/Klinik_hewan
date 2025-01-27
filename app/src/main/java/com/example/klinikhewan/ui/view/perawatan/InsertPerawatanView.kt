package com.example.klinikhewan.ui.view.perawatan

import Pasien
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DestinasiEntryPrn : DestinasiNavigasi {
    override val route = "item_entry perawatan"
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

    LaunchedEffect(Unit) {
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
        // Variable to control DatePicker
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val openDatePicker = remember { mutableStateOf(false) }
        val dateState = remember { mutableStateOf(insertPrnUiEvent.tanggal_perawatan) }

        // Launch DatePickerDialog when openDatePicker is true
        if (openDatePicker.value) {
            val datePickerDialog = android.app.DatePickerDialog(
                context,
                { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    calendar.set(year, monthOfYear, dayOfMonth)
                    val selectedDate = dateFormatter.format(calendar.time)
                    onValueChange(insertPrnUiEvent.copy(tanggal_perawatan = selectedDate))
                    dateState.value = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            LaunchedEffect(openDatePicker.value) {
                datePickerDialog.show()
                openDatePicker.value = false
            }
        }


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
            Button(onClick = { expandedHewan.value = true },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,) {
                Text(text = selectedIdHewan)
            }

            DropdownMenu(expanded = expandedHewan.value, onDismissRequest = { expandedHewan.value = false }) {
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
            Button(onClick = { expandedDokter.value = true }, modifier = Modifier.fillMaxWidth(),shape = MaterialTheme.shapes.medium,) {
                Text(text = selectedIdDokter)
            }

            DropdownMenu(expanded = expandedDokter.value, onDismissRequest = { expandedDokter.value = false }) {
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

        // Tanggal Lahir sebagai DatePicker
        OutlinedTextField(
            value = dateState.value,
            onValueChange = { /* Tidak diubah karena kita menggunakan DatePicker */ },
            label = { Text("Tanggal Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                Text(
                    text = "📅", // Ikon kalender
                    modifier = Modifier.clickable { openDatePicker.value = true }
                )
            }
        )

        Divider(thickness = 8.dp, modifier = Modifier.padding(12.dp))
    }
}
