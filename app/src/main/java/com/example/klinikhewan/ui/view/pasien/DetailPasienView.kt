package com.example.klinikhewan.ui.view.pasien

import Pasien
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinikhewan.R
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.DetailPsnViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.DetailPsnUiState
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi

object DestinasiPsnDetailPsn : DestinasiNavigasi {
    override val route = "detail Pasien"
    const val ID_HEWAN = "id_hewan"
    val routesWithArg = "$route/{$ID_HEWAN}"
    override val titleRes = "Detail Pasien"
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPsnView(
    id_hewan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPsnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiPsnDetailPsn.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefreshClick = { viewModel.getDetailPasien() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_hewan) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pasien"
                )
            }
        }
    ) { innerPadding ->
        val detailPsnUiState by viewModel.detailPsnUiState.collectAsState()

        BodyDetailPsn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            detailPsnUiState = detailPsnUiState,
            retryAction = { viewModel.getDetailPasien() }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BodyDetailPsn(
    modifier: Modifier = Modifier,
    detailPsnUiState: DetailPsnUiState,
    retryAction: () -> Unit = {}
) {
    Box(modifier = modifier) {
        when (detailPsnUiState) {
            is DetailPsnUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
            }
            is DetailPsnUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pet),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    )
                    Text(
                        text = "Detail Pasien",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.onBackground,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    ItemDetailPsn(pasien = detailPsnUiState.pasien)
                }
            }
            is DetailPsnUiState.Error -> {
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemDetailPsn(
    pasien: Pasien
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
            ComponentDetailPsn(judul = "ID Pasien", isinya = pasien.id_hewan)
            ComponentDetailPsn(judul = "Nama Hewan", isinya = pasien.nama_hewan)
            ComponentDetailPsn(judul = "Jenis Hewan", isinya = pasien.id_jenis_hewan)
            ComponentDetailPsn(judul = "Nama Pemilik", isinya = pasien.pemilik)
            ComponentDetailPsn(judul = "Kontak Pemilik", isinya = pasien.kontak_pemilik)

            // Format tanggal lahir menjadi lokal
            val formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy", java.util.Locale("id", "ID"))
            val formattedTanggalLahir = try {
                java.time.LocalDate.parse(pasien.tanggal_lahir).format(formatter)
            } catch (e: Exception) {
                pasien.tanggal_lahir // Jika parsing gagal, tampilkan string asli
            }
            ComponentDetailPsn(judul = "Tanggal Lahir", isinya = formattedTanggalLahir)

            ComponentDetailPsn(judul = "Catatan Kesehatan", isinya = pasien.catatan_kesehatan)
        }
    }
}

@Composable
fun ComponentDetailPsn(
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
