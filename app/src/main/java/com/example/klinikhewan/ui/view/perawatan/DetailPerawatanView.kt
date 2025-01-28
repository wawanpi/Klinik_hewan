package com.example.klinikhewan.ui.view.perawatan

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
import com.example.klinikhewan.model.Perawatan
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.perawatan.DetailPrnUiState
import com.example.klinikhewan.ui.viewmodel.perawatan.DetailPrnViewModel
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DestinasiPrnDetailPrn : DestinasiNavigasi {
    override val route = "detail perawatan"
    const val ID_PERAWATAN = "id_perawatan"
    val routesWithArg = "$route/{$ID_PERAWATAN}"
    override val titleRes = "Detail Perawatan"
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPrnView(
    id_perawatan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPrnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiPrnDetailPrn.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefreshClick = { viewModel.getDetailPerawatan() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_perawatan) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Perawatan"
                )
            }
        }
    ) { innerPadding ->
        val detailPrnUiState by viewModel.detailPrnUiState.collectAsState()

        BodyDetailPrn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            detailPrnUiState = detailPrnUiState,
            retryAction = { viewModel.getDetailPerawatan() }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BodyDetailPrn(
    modifier: Modifier = Modifier,
    detailPrnUiState: DetailPrnUiState,
    retryAction: () -> Unit = {}
) {
    Box(modifier = modifier) {
        when (detailPrnUiState) {
            is DetailPrnUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            is DetailPrnUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.perawatan),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    )
                    Text(
                        text = "Detail Perawatan",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.onBackground,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    ItemDetailPrn(perawatan = detailPrnUiState.perawatan)
                }
            }
            is DetailPrnUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Gagal memuat data.",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
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
fun ItemDetailPrn(
    perawatan: Perawatan
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
            ComponentDetailPrn(judul = "ID Perawatan", isinya = perawatan.id_perawatan)
            ComponentDetailPrn(judul = "ID Hewan", isinya = perawatan.id_hewan)
            ComponentDetailPrn(judul = "ID Dokter", isinya = perawatan.id_dokter)

            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
            val formattedDate = try {
                LocalDate.parse(perawatan.tanggal_perawatan).format(formatter)
            } catch (e: Exception) {
                perawatan.tanggal_perawatan
            }
            ComponentDetailPrn(judul = "Tanggal Perawatan", isinya = formattedDate)

            ComponentDetailPrn(judul = "Detail Perawatan", isinya = perawatan.detail_perawatan)
        }
    }
}

@Composable
fun ComponentDetailPrn(
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
