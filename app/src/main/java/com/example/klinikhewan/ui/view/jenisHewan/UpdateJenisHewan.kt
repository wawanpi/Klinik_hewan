package com.example.klinikhewan.ui.view.jenisHewan

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
import com.example.klinikhewan.ui.viewmodel.jenisHewan.UpdateJhViewModel
import com.example.klinikhewan.ui.viewmodel.jenisHewan.toJh
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiUpdateJh : DestinasiNavigasi {
    override val route = "update"
    const val ID_JENSI_HEWAN = "id_jenis_hewan"
    val routesWithArg = "$route/{$ID_JENSI_HEWAN}"
    override val titleRes = "Update Dokter"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UpdateJhView(
        navigateBack: () -> Unit,
        id_jenis_hewan: String,
        modifier: Modifier = Modifier,
        viewModel: UpdateJhViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ) {
        val coroutineScope = rememberCoroutineScope()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        val dtruiState = viewModel.JhuiState.value

        Scaffold(
            modifier = modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
            topBar = {
                CostumeTopAppBar(
                    title = DestinasiUpdateJh.titleRes,
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
                EntryBodyJh(
                    insertJhUiState = dtruiState,
                    onPasienValueChange = { updatedValue ->
                        viewModel.updateJhState(updatedValue)
                    },
                    onSaveClick = {
                        dtruiState.insertJhUiEvent?.let { insertdtrUiEvent ->
                            coroutineScope.launch {
                                viewModel.updateJh(
                                    id_jenis_hewan = viewModel.id_jenis_hewan,
                                    jenisHewan = insertdtrUiEvent.toJh()
                                )
                                navigateBack()
                            }
                        }
                    }
                )
            }
        }
    }
}