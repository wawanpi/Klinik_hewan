package com.example.klinikhewan.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.klinikhewan.KlinikApplications
import com.example.klinikhewan.ui.viewmodel.dokter.*
import com.example.klinikhewan.ui.viewmodel.jenisHewan.*
import com.example.klinikhewan.ui.viewmodel.pasien.*
import com.example.klinikhewan.ui.viewmodel.perawatan.*

object PenyediaViewModel {

    // Factory untuk inisialisasi ViewModel
    val Factory = viewModelFactory {
        // Pasien
        initializer {
            HomePsnViewModel(aplikasiKlinik().container.pasienRepository)
        }
        initializer {
            InsertPsnViewModel(
                aplikasiKlinik().container.pasienRepository,
                aplikasiKlinik().container.jenisHewanRepository
            )
        }
        initializer {
            DetailPsnViewModel(
                createSavedStateHandle(),
                aplikasiKlinik().container.pasienRepository
            )
        }
        initializer {
            UpdatePsnViewModel(
                createSavedStateHandle(),
                aplikasiKlinik().container.pasienRepository,
                aplikasiKlinik().container.jenisHewanRepository
            )
        }

        // Dokter
        initializer {
            HomeDtrViewModel(aplikasiKlinik().container.dokterRepository)
        }
        initializer {
            InsertDtrViewModel(aplikasiKlinik().container.dokterRepository)
        }
        initializer {
            DetailDtrViewModel(
                createSavedStateHandle(),
                aplikasiKlinik().container.dokterRepository
            )
        }
        initializer {
            UpdateDtrViewModel(
                createSavedStateHandle(),
                aplikasiKlinik().container.dokterRepository
            )
        }

        // Jenis Hewan
        initializer {
            HomeJhViewModel(aplikasiKlinik().container.jenisHewanRepository)
        }
        initializer {
            InsertJhViewModel(aplikasiKlinik().container.jenisHewanRepository)
        }
        initializer {
            DetailJhViewModel(
                createSavedStateHandle(),
                aplikasiKlinik().container.jenisHewanRepository
            )
        }
        initializer {
            UpdateJhViewModel(
                createSavedStateHandle(),
                aplikasiKlinik().container.jenisHewanRepository
            )
        }

        // Perawatan
        initializer {
            HomePrnViewModel(aplikasiKlinik().container.perawatanRepository)
        }
        initializer {
            InsertPrnViewModel(
                aplikasiKlinik().container.perawatanRepository,
                aplikasiKlinik().container.pasienRepository,
                aplikasiKlinik().container.dokterRepository
            )
        }
        initializer {
            DetailPrnViewModel(
                createSavedStateHandle(),
                aplikasiKlinik().container.perawatanRepository
            )
        }
        initializer {
            UpdatePrnViewModel(
                createSavedStateHandle(),
                aplikasiKlinik().container.perawatanRepository,
                aplikasiKlinik().container.pasienRepository,
                aplikasiKlinik().container.dokterRepository
            )
        }
    }

    // Helper function untuk mendapatkan aplikasi utama
    fun CreationExtras.aplikasiKlinik(): KlinikApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KlinikApplications)
}
