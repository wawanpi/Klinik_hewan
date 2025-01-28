package com.example.klinikhewan.ui.view.dokter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinikhewan.R
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.dokter.DetailDtrUiState
import com.example.klinikhewan.ui.viewmodel.dokter.DetailDtrViewModel
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi

object DestinasiDtrDetailDtr : DestinasiNavigasi {
    override val route = "detail dokter"
    const val ID_DOKTER = "id_dokter"
    val routesWithArg = "$route/{$ID_DOKTER}"
    override val titleRes = "Detail Dokter"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDtrView(
    id_dokter: String,
    modifier: Modifier = Modifier,
    viewModel: DetailDtrViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDtrDetailDtr.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefreshClick = { viewModel.getDetailDokter() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_dokter) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Dokter"
                )
            }
        }
    ) { innerPadding ->
        val detailDtrUiState by viewModel.detailDtrUiState.collectAsState()

        BodyDetailDtr(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            detailDtrUiState = detailDtrUiState,
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
    Box(modifier = modifier) {
        when (detailDtrUiState) {
            is DetailDtrUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
            }
            is DetailDtrUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dokter),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    )
                    Text(
                        text = "Detail Dokter",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.onBackground,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    ItemDetailDtr(dokter = detailDtrUiState.dokter)
                }
            }
            is DetailDtrUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Gagal memuat data.", color = MaterialTheme.colorScheme.error, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = retryAction) {
                        Text("Coba Lagi")
                    }
                }
            }
            else -> {
                Text(
                    text = "Keadaan tidak terduga",
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Medium
                )
            }
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
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ComponentDetailDtr(judul = "ID Dokter", isinya = dokter.id_dokter)
            ComponentDetailDtr(judul = "Nama Dokter", isinya = dokter.nama_dokter)
            ComponentDetailDtr(judul = "Spesialisasi", isinya = dokter.spesialisasi)
            ComponentDetailDtr(judul = "Kontak", isinya = dokter.kontak)
        }
    }
}

@Composable
fun ComponentDetailDtr(
    judul: String,
    isinya: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = judul,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = isinya,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}