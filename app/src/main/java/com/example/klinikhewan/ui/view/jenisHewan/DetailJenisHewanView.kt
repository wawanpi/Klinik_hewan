package com.example.klinikhewan.ui.view.jenisHewan

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
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.jenisHewan.DetailJhViewModel
import com.example.klinikhewan.ui.viewmodel.jenisHewan.DetailJhUiState
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi

object DestinasiJhDetailJh : DestinasiNavigasi {
    override val route = "detail" // base route
    const val ID_JENSI_HEWAN = "id_jenis_hewan" // Nama parameter untuk nim
    val routesWithArg = "$route/{$ID_JENSI_HEWAN}" // Route yang menerima nim sebagai argumen
    override val titleRes = "Detail Dokter" // Title untuk halaman ini
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJhView(
    id_jenis_hewan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailJhViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack:()->Unit,
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiJhDetailJh.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefreshClick = { viewModel.getDetailJenisHewan() } // Trigger refresh action on refresh
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_jenis_hewan) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Jenis Hewan"
                )
            }
        }
    ) { innerPadding ->
        val detailpsnUiState by viewModel.detailJhUiState.collectAsState()

        BodyDetailJh(
            modifier = Modifier.padding(innerPadding),
            detailJhUiState = detailpsnUiState,
            retryAction = { viewModel.getDetailJenisHewan() }
        )
    }
}

@Composable
fun BodyDetailJh(
    modifier: Modifier = Modifier,
    detailJhUiState: DetailJhUiState,
    retryAction: () -> Unit = {}
) {
    when (detailJhUiState) {
        is DetailJhUiState.Loading -> {
            // Menampilkan gambar loading saat data sedang dimuat com.example.klinikhewan.ui.view.jenisHewan.
            com.example.klinikhewan.ui.view.jenisHewan.OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailJhUiState.Success -> {
            // Menampilkan detail mahasiswa jika berhasil
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailJh(jenisHewan = detailJhUiState
                    .jenisHewan)
            }
        }
        is DetailJhUiState.Error -> {
            // Menampilkan error jika data gagal dimuat com.example.klinikhewan.ui.view.jenisHewan.
            com.example.klinikhewan.ui.view.jenisHewan.OnError(
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
fun ItemDetailJh(
    jenisHewan: JenisHewan
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailJh(judul = "ID Jenis Hewan", isinya = jenisHewan.id_jenis_hewan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJh(judul = "Nama Jenis Hewan", isinya = jenisHewan.nama_jenis_hewan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJh(judul = "Deskripsi", isinya = jenisHewan.deskripsi)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}


@Composable
fun ComponentDetailJh(
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