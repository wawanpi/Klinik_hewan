package com.example.pertemuan12.ui.costumwidget

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack // Icon panah balik
import androidx.compose.material.icons.filled.Refresh // Icon refresh
import androidx.compose.material3.CenterAlignedTopAppBar // Buat top bar yang center aligned
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton // Button buat icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class) // Buat fitur eksperimental
@Composable
fun CostumeTopAppBar(
    title: String, // Text judul di app bar
    canNavigateBack: Boolean, // Bolehkah pakai tombol balik?
    modifier: Modifier = Modifier, // Modifier buat styling
    scrollBehavior: TopAppBarScrollBehavior? = null, // Scroll behavior buat efek
    navigateUp: () -> Unit = {}, // Fungsi tombol balik
    onRefreshClick: () -> Unit = {}, // Fungsi tombol refresh
) {
    // Top app bar dengan desain center aligned
    CenterAlignedTopAppBar(
        title = { Text(title) }, // Tampilkan judul di tengah
        actions = {
            // Icon refresh, clickable buat trigger refresh
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "", // Tidak ada deskripsi
                modifier = Modifier.clickable {
                    onRefreshClick() // Trigger pas icon refresh diklik
                }
            )
        },
        modifier = modifier, // Apply modifier styling
        scrollBehavior = scrollBehavior, // Scroll efek kalau di-scroll
        navigationIcon = {
            if (canNavigateBack) { // Kalau bisa navigasi balik, munculin icon back
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, // Icon panah balik
                        contentDescription = null // Opsional: tambahin aksesibilitas deskripsi
                    )
                }
            }
        }
    )
}
