package com.example.klinikhewan.ui.view.pasien

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
import com.example.klinikhewan.ui.viewmodel.pasien.UpdatePsnViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.toPsn
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiUpdatePsn : DestinasiNavigasi {
    override val route = "update pasien"
    const val ID_HEWAN = "id_hewan"
    val routesWithArg = "$route/{$ID_HEWAN}"
    override val titleRes = "Update Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePsnView(
    navigateBack: () -> Unit,
    id_hewan: String,
    modifier: Modifier = Modifier,
    viewModel: UpdatePsnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val psnuiState = viewModel.psnuiState.value
    val jenisHewanList = viewModel.jhlist // Ambil daftar jenis hewan dari ViewModel

    Scaffold(
        modifier = modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePsn.titleRes,
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
            EntryBodyPsn(
                insertPsnUiState = psnuiState,
                jenisHewanList = jenisHewanList, // Kirim daftar jenis hewan ke EntryBodyPsn
                onPasienValueChange = {   updatedValue ->
                    viewModel.updatePsnState(updatedValue)
                },
                onSaveClick = {
                    psnuiState.insertPsnUiEvent?.let { insertpsnUiEvent ->
                        coroutineScope.launch {
                            viewModel.updatePsn(
                                id_hewan = id_hewan,
                                pasien = insertpsnUiEvent.toPsn()
                            )
                            navigateBack()
                        }
                    }
                }
            )
        }
    }
}
