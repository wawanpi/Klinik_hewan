package com.example.klinikhewan.ui.view.jenisHewan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinikhewan.R
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.jenisHewan.HomeJhUiState
import com.example.klinikhewan.ui.viewmodel.jenisHewan.HomeJhViewModel
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi

object DestinasiHomeJh: DestinasiNavigasi {
    override val route = "home jenis hewan"
    override val titleRes = "Home Daftat Jenis Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeJhScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit, // Callback untuk navigasi ke halaman tambah jenis hewan
    modifier: Modifier = Modifier, // Modifier untuk mengatur layout composable
    onDetailClick: (String) -> Unit = {}, // Callback untuk navigasi ke detail jenis hewan berdasarkan ID
    viewModel: HomeJhViewModel = viewModel(factory = PenyediaViewModel.Factory) // ViewModel untuk memuat data jenis hewan
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() // Mengatur perilaku scrolling pada TopAppBar

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), // Menambahkan nested scroll behavior
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeJh.titleRes, // Menggunakan judul dari objek `DestinasiHome`
                canNavigateBack = true, // Tidak memungkinkan kembali karena ini halaman utama
                navigateUp = navigateBack, // Aksi saat tombol back di klik
                scrollBehavior = scrollBehavior, // Menautkan scroll behavior ke TopAppBar
                onRefreshClick = {
                    viewModel.getJh() // Memuat ulang data jenis hewan ketika tombol refresh diklik
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry, // Navigasi ke halaman tambah jenis hewan
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add, // Ikon plus untuk tombol
                    contentDescription = "Add Jenis Hewan"
                )
            }
        },
    ) { innerPadding ->
        HomeJhStatus(
            homeJhUiState = viewModel.jhUIState, // State UI dari ViewModel
            retryAction = { viewModel.getJh() }, // Callback untuk mencoba lagi memuat data
            modifier = Modifier.padding(innerPadding), // Memberikan padding berdasarkan Scaffold
            onDetailClick = onDetailClick, // Aksi ketika item detail jenis hewan diklik
            onDeleteClick = {
                viewModel.deleteJh(it.id_jenis_hewan) // Memastikan properti yang digunakan sesuai model
                viewModel.getJh() // Memuat ulang data setelah penghapusan
            }
        )
    }
}

@Composable
fun HomeJhStatus(
    homeJhUiState: HomeJhUiState, // UI state
    retryAction: () -> Unit, // Fungsi untuk mencoba lagi setelah error
    modifier: Modifier = Modifier,
    onDeleteClick: (JenisHewan) -> Unit = {}, // Menyesuaikan tipe parameter dengan model
    onDetailClick: (String) -> Unit // Fungsi untuk handle klik detail
) {
    when (homeJhUiState) {
        is HomeJhUiState.Loading -> com.example.klinikhewan.ui.view.jenisHewan.OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is HomeJhUiState.Success -> {
            if (homeJhUiState.jenisHewan.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data jenis hewan")
                }
            } else {
                JhLayout(
                    jenisHewan = homeJhUiState.jenisHewan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_jenis_hewan) // Memastikan properti sesuai model `JenisHewan`
                    },
                    onDeleteClick = {
                        onDeleteClick(it) // Menyesuaikan tipe data dengan model
                    }
                )
            }
        }

        is HomeJhUiState.Error -> com.example.klinikhewan.ui.view.jenisHewan.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}


@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    // Menampilkan gambar ikon loading (misalnya gambar koneksi error yang ada tulisan "loading")
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading), // Icon loading error
        contentDescription = stringResource(R.string.loading) // Deskripsi gambar untuk aksesibilitas
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    // Kolom sebagai layout utama
    Column(
        modifier = modifier, // Modifier untuk menyesuaikan style (opsional)
        verticalArrangement = Arrangement.Center, // Untuk menempatkan elemen secara vertikal di tengah
        horizontalAlignment = Alignment.CenterHorizontally // Untuk menempatkan elemen secara horizontal di tengah
    ) {
        // Menampilkan gambar error (misalnya icon koneksi gagal)
        Image(
            painter = painterResource(id = R.drawable.wifieror),
            contentDescription = "" // Menambahkannya untuk aksesibilitas
        )

        // Menampilkan pesan error ("Loading failed" atau lainnya)
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))

        // Tombol untuk mencoba ulang
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry)) // Menampilkan text tombol "Retry"
        }
    }
}

@Composable
fun JhLayout(
    jenisHewan: List<JenisHewan>, // List data jenisHewan
    modifier: Modifier = Modifier,
    onDetailClick: (JenisHewan) -> Unit, // Fungsi untuk tampilkan detail jenisHewan
    onDeleteClick: (JenisHewan) -> Unit = {} // Fungsi untuk hapus data jenisHewan
) {
    // LazyColumn untuk menampilkan list jenisHewan
    LazyColumn(
        modifier = modifier, // Modifier untuk styling (opsional)
        contentPadding = PaddingValues(16.dp), // Padding untuk seluruh list
        verticalArrangement = Arrangement.spacedBy(16.dp) // Jarak antar item dalam list
    ) {
        items(jenisHewan) { jenisHewan -> // Untuk setiap jenisHewan di dalam list
            JhCard(
                jenisHewan = jenisHewan, // Kirim data jenisHewan ke PsnCard
                modifier = Modifier
                    .fillMaxWidth() // Membuat kartu memakan seluruh lebar layar
                    .clickable { onDetailClick(jenisHewan) }, // Fungsi detail ketika item diklik
                onDeleteClick = { // Fungsi delete ketika tombol delete di kartu diklik
                    onDeleteClick(jenisHewan)
                }
            )
        }
    }
}

@Composable
fun JhCard(
    jenisHewan: JenisHewan,
    modifier: Modifier = Modifier,
    onDeleteClick: (JenisHewan) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), // Sudut melengkung lebih besar
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // Use a solid color here
        )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp), // Padding keseluruhan lebih besar
            verticalArrangement = Arrangement.spacedBy(16.dp) // Jarak antar elemen lebih longgar
        ) {
            // Baris Nama dan Tombol Delete
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info, // Replace with a valid icon
                    contentDescription = "Pet Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = jenisHewan.nama_jenis_hewan,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = { onDeleteClick(jenisHewan) },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.error.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Informasi Pasien
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                com.example.klinikhewan.ui.view.jenisHewan.InfoRow(icon = Icons.Default.Info, label = "ID", value = jenisHewan.id_jenis_hewan)
                com.example.klinikhewan.ui.view.jenisHewan.InfoRow(icon = Icons.Default.Info, label = "Nama Jenis Hewan", value = jenisHewan.nama_jenis_hewan)
                com.example.klinikhewan.ui.view.jenisHewan.InfoRow(icon = Icons.Default.Person, label = "Deskripsi", value = jenisHewan.deskripsi)

            }

            // Divider sebagai dekorasi tambahan
            Divider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}