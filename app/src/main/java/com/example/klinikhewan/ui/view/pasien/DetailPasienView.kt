package com.example.klinikhewan.ui.view.pasien


import Pasien
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
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.DetailPsnViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.DetailPsnUiState
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi

object DestinasiPsnDetailPsn : DestinasiNavigasi {
    override val route = "detail" // base route
    const val ID_HEWAN = "id_hewan" // Nama parameter untuk nim
    val routesWithArg = "$route/{$ID_HEWAN}" // Route yang menerima nim sebagai argumen
    override val titleRes = "Detail Pasien" // Title untuk halaman ini
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPsnView(
    id_hewan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPsnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (String) -> Unit = {},
    navigateBack:()->Unit,
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiPsnDetailPsn.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefreshClick = { viewModel.getDetailPasien() } // Trigger refresh action on refresh
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
        val detailpsnUiState by viewModel.detailPsnUiState.collectAsState()

        BodyDetailPsn(
            modifier = Modifier.padding(innerPadding),
            detailPsnUiState = detailpsnUiState,
            retryAction = { viewModel.getDetailPasien() }
        )
    }
}

@Composable
fun BodyDetailPsn(
    modifier: Modifier = Modifier,
    detailPsnUiState: DetailPsnUiState,
    retryAction: () -> Unit = {}
) {
    when (detailPsnUiState) {
        is DetailPsnUiState.Loading -> {
            // Menampilkan gambar loading saat data sedang dimuat
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailPsnUiState.Success -> {
            // Menampilkan detail mahasiswa jika berhasil
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailPsn(pasien = detailPsnUiState
                    .pasien)
            }
        }
        is DetailPsnUiState.Error -> {
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
fun ItemDetailPsn(
    pasien: Pasien
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailPsn(judul = "ID Pasien", isinya = pasien.id_hewan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPsn(judul = "Nama Hewan", isinya = pasien.nama_hewan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPsn(judul = "Jenis Hewan", isinya = pasien.id_jenis_hewan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPsn(judul = "Nama Pemilik", isinya = pasien.pemilik)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPsn(judul = "Kontak Pemilik", isinya = pasien.kontak_pemilik)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPsn(judul = "Tanggal Lahir ", isinya = pasien.tanggal_lahir)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPsn(judul = "Catatan Keshatan", isinya = pasien.catatan_kesehatan)
        }
    }
}

@Composable
fun ComponentDetailPsn(
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