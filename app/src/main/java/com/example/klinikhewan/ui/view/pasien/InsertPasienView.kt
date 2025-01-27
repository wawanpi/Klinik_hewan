package com.example.klinikhewan.ui.view.pasien

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.InsertPsnUiEvent
import com.example.klinikhewan.ui.viewmodel.pasien.InsertPsnUiState
import com.example.klinikhewan.ui.viewmodel.pasien.InsertPsnViewModel
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Navigasi tujuan buat layar ini
object DestinasiEntryPsn : DestinasiNavigasi {
    override val route = "item_entry pasien" // Rute navigasi ke layar ini
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
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        FormInputPsn(
            insertPsnUiEvent = insertPsnUiState.insertPsnUiEvent,
            onValueChange = onPasienValueChange,
            jenisHewanList = jenisHewanList,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Simpan", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
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
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val openDatePicker = remember { mutableStateOf(false) }
    val dateState = remember { mutableStateOf(insertPsnUiEvent.tanggal_lahir) }

    if (openDatePicker.value) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar.set(year, monthOfYear, dayOfMonth)
                val selectedDate = dateFormatter.format(calendar.time)
                onValueChange(insertPsnUiEvent.copy(tanggal_lahir = selectedDate))
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
            label = { Text("ID Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        val expanded = remember { mutableStateOf(false) }
        val selectedIdJenisHewan = jenisHewanList.find { it.id_jenis_hewan == insertPsnUiEvent.id_jenis_hewan }?.id_jenis_hewan ?: "Pilih Jenis Hewan"

        Column {
            Text(text = "ID Jenis Hewan", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 4.dp))
            OutlinedButton(
                onClick = { expanded.value = true },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedIdJenisHewan, color = Color.Black)
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
            value = dateState.value,
            onValueChange = { },
            label = { Text("Tanggal Lahir") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                Text(
                    text = "📅",
                    modifier = Modifier.clickable { openDatePicker.value = true }
                )
            }
        )

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
            value = insertPsnUiEvent.catatan_kesehatan,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(catatan_kesehatan = it)) },
            label = { Text("Catatan Kesehatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
    }
}
