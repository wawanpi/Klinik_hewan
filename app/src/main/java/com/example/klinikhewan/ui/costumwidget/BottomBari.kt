package com.example.klinikhewan.ui.costumwidget

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.klinikhewan.R

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onHomeDokterClick: () -> Unit,
    onHomeJenisHewanClick: () -> Unit, // Tambahkan parameter untuk tombol Reservasi
    onHomePerawatanClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Ikon untuk navigasi ke HomePelanggan
            Image(
                painter = painterResource(id = R.drawable.dokter), // Ganti dengan ikon Anda
                contentDescription = "Home Dokter",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onHomeDokterClick() }
            )

            // Ikon untuk navigasi ke HomeReservasi
            Image(
                painter = painterResource(id = R.drawable.jenishewan), // Ganti dengan ikon Anda
                contentDescription = "Home jenis hewan",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onHomeJenisHewanClick() }
            )

            Image(
                painter = painterResource(id = R.drawable.perawatan), // Ganti dengan ikon Anda
                contentDescription = "Home Perawatan ",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onHomePerawatanClick() }
            )
            }
        }
}