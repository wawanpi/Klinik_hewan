package com.example.klinikhewan.ui.view.dokter

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
import com.example.klinikhewan.ui.viewmodel.dokter.UpdateDtrViewModel
import com.example.klinikhewan.ui.viewmodel.dokter.toDtr
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiUpdateDtr : DestinasiNavigasi {
    override val route = "update dokter"
    const val ID_DOKTER = "id_dokter"
    val routesWithArg = "$route/{$ID_DOKTER}"
    override val titleRes = "Update Dokter"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UpdateDtrView(
        navigateBack: () -> Unit,
        id_dokter: String,
        modifier: Modifier = Modifier,
        viewModel: UpdateDtrViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ) {
        val coroutineScope = rememberCoroutineScope()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        val dtruiState = viewModel.DtruiState.value

        Scaffold(
            modifier = modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
            topBar = {
                CostumeTopAppBar(
                    title = DestinasiUpdateDtr.titleRes,
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
                EntryBodyDtr(
                    insertDtrUiState = dtruiState,
                    onPasienValueChange = { updatedValue ->
                        viewModel.updateDtrState(updatedValue)
                    },
                    onSaveClick = {
                        dtruiState.insertDtrUiEvent?.let { insertdtrUiEvent ->
                            coroutineScope.launch {
                                viewModel.updateDtr(
                                    id_dokter = viewModel.id_dokter,
                                    dokter = insertdtrUiEvent.toDtr()
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