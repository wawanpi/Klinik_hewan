package com.example.klinikhewan.ui.view.dokter

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
import androidx.compose.material.icons.filled.Call
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
import com.example.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.example.klinikhewan.ui.viewmodel.dokter.HomeDtrUiState
import com.example.klinikhewan.ui.viewmodel.dokter.HomeDtrViewModel
import com.example.pertemuan12.ui.costumwidget.CostumeTopAppBar
import com.example.pertemuan12.ui.navigation.DestinasiNavigasi

object DestinasiHomeDtr: DestinasiNavigasi {
    override val route = "home dokter"
    override val titleRes = "Home Daftat Dokter"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDtrScreen(
    navigateToItemEntry: () -> Unit, // Callback untuk navigasi ke halaman tambah mahasiswa
    modifier: Modifier = Modifier, // Modifier untuk mengatur layout composable
    onDetailClick: (String) -> Unit = {}, // Callback untuk navigasi ke detail mahasiswa berdasarkan NIM
    viewModel: HomeDtrViewModel = viewModel(factory = PenyediaViewModel.Factory) // ViewModel untuk memuat data mahasiswa
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() // Mengatur perilaku scrolling pada TopAppBar

    // Scaffold: Struktur dasar layar dengan komponen seperti TopBar, FAB, dan konten
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), // Menambahkan nested scroll behavior
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeDtr.titleRes, // Menggunakan judul dari objek `DestinasiHome`
                canNavigateBack = false, // Tidak memungkinkan kembali karena ini halaman utama
                scrollBehavior = scrollBehavior, // Menautkan scroll behavior ke TopAppBar
                onRefreshClick = {
                    viewModel.getDtr() // Memuat ulang data mahasiswa ketika tombol refresh diklik
                }
            )
        },
        floatingActionButton = {
            // Tombol floating untuk menambahkan data mahasiswa
            FloatingActionButton(
                onClick = navigateToItemEntry, // Navigasi ke halaman tambah mahasiswa
                shape = MaterialTheme.shapes.medium, // Mengatur bentuk tombol floating
                modifier = Modifier.padding(18.dp) // Memberikan padding
            ) {
                Icon(
                    imageVector = Icons.Default.Add, // Ikon plus untuk tombol
                    contentDescription = "Add Pasien" // Deskripsi konten ikon
                )
            }
        },
    ) { innerPadding ->
        // Konten utama layar (bagian `Body`) yang menampilkan daftar mahasiswa atau status lain
        HomeDtrStatus(
            homeDtrUiState = viewModel.dtrUIState, // State UI dari ViewModel: Loading, Success, atau Error
            retryAction = { viewModel.getDtr() }, // Callback untuk mencoba lagi memuat data
            modifier = Modifier.padding(innerPadding), // Memberikan padding berdasarkan `Scaffold`
            onDetailClick = onDetailClick, // Aksi ketika item detail mahasiswa diklik
            onDeleteClick = {
                viewModel.deleteDtr(it.id_dokter) // Hapus mahasiswa berdasarkan NIM
                viewModel.getDtr()         // Memuat ulang data setelah penghapusan
            }
        )
    }
}

@Composable
fun HomeDtrStatus(
    homeDtrUiState: HomeDtrUiState, // UI state yang bisa berupa Loading, Success atau Error
    retryAction: () -> Unit, // Fungsi untuk mencoba lagi setelah error
    modifier: Modifier = Modifier, // Modifier untuk styling tampilan
    onDeleteClick: (Dokter) -> Unit = {}, // Fungsi untuk handle aksi delete mahasiswa
    onDetailClick: (String) -> Unit // Fungsi untuk handle klik detail mahasiswa
) {
    when (homeDtrUiState) {
        // Jika dalam kondisi loading
        is HomeDtrUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        // Jika data berhasil diambil
        is HomeDtrUiState.Success -> {
            if (homeDtrUiState.dokter.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data kontak")
                }
            } else {
                // Jika data ada, tampilkan layout mahasiswa
                DtrLayout(
                    dokter = homeDtrUiState.dokter,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_dokter) // OnDetailClick memberi akses detail mahasiswa
                    },
                    onDeleteClick = {
                        onDeleteClick(it) // OnDeleteClick memberi aksi delete
                    }
                )
            }
        }

        // Jika terjadi error
        is HomeDtrUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun DtrLayout(
    dokter: List<Dokter>, // List data dokter
    modifier: Modifier = Modifier,
    onDetailClick: (Dokter) -> Unit, // Fungsi untuk tampilkan detail dokter
    onDeleteClick: (Dokter) -> Unit = {} // Fungsi untuk hapus data dokter
) {
    // LazyColumn untuk menampilkan list dokter
    LazyColumn(
        modifier = modifier, // Modifier untuk styling (opsional)
        contentPadding = PaddingValues(16.dp), // Padding untuk seluruh list
        verticalArrangement = Arrangement.spacedBy(16.dp) // Jarak antar item dalam list
    ) {
        items(dokter) { dokter -> // Untuk setiap dokter di dalam list
            DtrCard(
                dokter = dokter, // Kirim data dokter ke PsnCard
                modifier = Modifier
                    .fillMaxWidth() // Membuat kartu memakan seluruh lebar layar
                    .clickable { onDetailClick(dokter) }, // Fungsi detail ketika item diklik
                onDeleteClick = { // Fungsi delete ketika tombol delete di kartu diklik
                    onDeleteClick(dokter)
                }
            )
        }
    }
}


@Composable
fun DtrCard(
    dokter: Dokter,
    modifier: Modifier = Modifier,
    onDeleteClick: (Dokter) -> Unit = {}
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
                    text = dokter.nama_dokter,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = { onDeleteClick(dokter) },
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
                InfoRow(icon = Icons.Default.Info, label = "ID", value = dokter.id_dokter)
                InfoRow(icon = Icons.Default.Info, label = "Nama Dokter", value = dokter.nama_dokter)
                InfoRow(icon = Icons.Default.Person, label = "Spesialis", value = dokter.spesialisasi)
                InfoRow(icon = Icons.Default.Call, label = "Kontak", value = dokter.kontak)
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