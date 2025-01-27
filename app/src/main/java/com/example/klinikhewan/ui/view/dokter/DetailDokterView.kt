package com.example.klinikhewan.ui.view.dokter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.dokter.DetailDtrUiState
import com.example.klinikhewan.ui.viewmodel.dokter.DetailDtrViewModel
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi

object DestinasiDtrDetailDtr : DestinasiNavigasi {
    override val route = "detail dokter" // base route
    const val ID_DOKTER = "id_dokter" // Nama parameter untuk nim
    val routesWithArg = "$route/{$ID_DOKTER}" // Route yang menerima nim sebagai argumen
    override val titleRes = "Detail Dokter" // Title untuk halaman ini
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDtrView(
    id_dokter: String,
    modifier: Modifier = Modifier,
    viewModel: DetailDtrViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDtrDetailDtr.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefreshClick = { viewModel.getDetailDokter() } // Trigger refresh action on refresh
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_dokter) },
                shape = RoundedCornerShape(50),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Dokter"
                )
            }
        }
    ) { innerPadding ->
        val detailpsnUiState by viewModel.detailDtrUiState.collectAsState()

        BodyDetailDtr(
            modifier = Modifier.padding(innerPadding),
            detailDtrUiState = detailpsnUiState,
            retryAction = { viewModel.getDetailDokter() }
        )
    }
}

@Composable
fun BodyDetailDtr(
    modifier: Modifier = Modifier,
    detailDtrUiState: DetailDtrUiState,
    retryAction: () -> Unit = {}
) {
    when (detailDtrUiState) {
        is DetailDtrUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailDtrUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailDtr(dokter = detailDtrUiState.dokter)
            }
        }
        is DetailDtrUiState.Error -> {
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
        else -> {
            Text("Unexpected state encountered")
        }
    }
}

@Composable
fun ItemDetailDtr(
    dokter: Dokter
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailDtr(judul = "ID Dokter", isinya = dokter.id_dokter)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailDtr(judul = "Nama Dokter", isinya = dokter.nama_dokter)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailDtr(judul = "Spesialisasi", isinya = dokter.spesialisasi)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailDtr(judul = "Kontak", isinya = dokter.kontak)
        }
    }
}

@Composable
fun ComponentDetailDtr(
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = isinya,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
