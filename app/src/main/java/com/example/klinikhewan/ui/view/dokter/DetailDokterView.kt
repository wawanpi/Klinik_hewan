package com.example.klinikhewan.ui.view.dokter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
    override val route = "detail" // base route
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
    navigateBack:()->Unit,
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
            // Menampilkan gambar loading saat data sedang dimuat com.example.klinikhewan.ui.view.dokter.
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailDtrUiState.Success -> {
            // Menampilkan detail mahasiswa jika berhasil
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailDtr(dokter = detailDtrUiState
                    .dokter)
            }
        }
        is DetailDtrUiState.Error -> {
            // Menampilkan error jika data gagal dimuat com.example.klinikhewan.ui.view.dokter.
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
        else -> {
            // Menangani kasus yang tidak terduga (optional, jika Anda ingin menangani hal ini)
            // Anda bisa menambahkan logika untuk menangani kesalahan yang tidak diketahui
            Text("Unexpected state encountered")
        }
    }

}

@Composable
fun ItemDetailDtr(
    dokter: Dokter
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailDtr(judul = "ID", isinya = dokter.id_dokter)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailDtr(judul = "Nama Hewan", isinya = dokter.nama_dokter)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailDtr(judul = "Jenis Hewan", isinya = dokter.spesialisasi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailDtr(judul = "Nama Pemilik", isinya = dokter.kontak)
            Spacer(modifier = Modifier.padding(4.dp))//Pasien
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
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}