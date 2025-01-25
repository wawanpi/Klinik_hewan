package com.example.klinikhewan.ui.view.perawatan


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
import com.example.klinikhewan.model.Perawatan
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.perawatan.DetailPrnViewModel
import com.example.klinikhewan.ui.viewmodel.perawatan.DetailPrnUiState
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi

object DestinasiPrnDetailPrn : DestinasiNavigasi {
    override val route = "detail" // base route
    const val ID_PERAWATAN = "id_perawatan" // Nama parameter untuk nim
    val routesWithArg = "$route/{$ID_PERAWATAN}" // Route yang menerima nim sebagai argumen
    override val titleRes = "Detail Perawatan" // Title untuk halaman ini
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPrnView(
    id_perawatan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPrnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack:()->Unit,
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiPrnDetailPrn.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefreshClick = { viewModel.getDetailPerawatan() } // Trigger refresh action on refresh
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
        val detailprnUiState by viewModel.detailPrnUiState.collectAsState()

        BodyDetailPrn(
            modifier = Modifier.padding(innerPadding),
            detailPrnUiState = detailprnUiState,
            retryAction = { viewModel.getDetailPerawatan() }
        )
    }
}

@Composable
fun BodyDetailPrn(
    modifier: Modifier = Modifier,
    detailPrnUiState: DetailPrnUiState,
    retryAction: () -> Unit = {}
) {
    when (detailPrnUiState) {
        is DetailPrnUiState.Loading -> {
            // Menampilkan gambar loading saat data sedang dimuat
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailPrnUiState.Success -> {
            // Menampilkan detail mahasiswa jika berhasil
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailPrn(perawatan = detailPrnUiState
                    .perawatan)
            }
        }
        is DetailPrnUiState.Error -> {
            // Menampilkan error jika data gagal dimuat
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
fun ItemDetailPrn(
    perawatan: Perawatan
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailPrn(judul = "ID Perawatan", isinya = perawatan.id_perawatan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPrn(judul = "Id Hewan", isinya = perawatan.id_hewan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPrn(judul = "Id Dokter", isinya = perawatan.id_dokter)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPrn(judul = "Tanggal Perawatan", isinya = perawatan.tanggal_perawatan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPrn(judul = "Kontak Pemilik", isinya = perawatan.detail_perawatan)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailPrn(
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