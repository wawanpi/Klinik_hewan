package com.example.klinikhewan.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.klinikhewan.KlinikApplications
import com.example.klinikhewan.ui.viewmodel.dokter.DetailDtrViewModel
import com.example.klinikhewan.ui.viewmodel.dokter.HomeDtrViewModel
import com.example.klinikhewan.ui.viewmodel.dokter.InsertDtrViewModel
import com.example.klinikhewan.ui.viewmodel.dokter.UpdateDtrViewModel
import com.example.klinikhewan.ui.viewmodel.jenisHewan.DetailJhViewModel
import com.example.klinikhewan.ui.viewmodel.jenisHewan.HomeJhViewModel
import com.example.klinikhewan.ui.viewmodel.jenisHewan.InsertJhViewModel
import com.example.klinikhewan.ui.viewmodel.jenisHewan.UpdateJhViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.DetailPsnViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.HomePsnViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.InsertPsnViewModel
import com.example.klinikhewan.ui.viewmodel.pasien.UpdatePsnViewModel

object PenyediaViewModel {
    // Factory untuk inisialisasi ViewModel yang diperlukan
    val Factory = viewModelFactory {
        // Inisialisasi ViewModel untuk halaman Home
        initializer {
            HomePsnViewModel(aplikasiKlinik().container.pasienRepository)
            // Pake repository dari aplikasi untuk HomeViewModel
        }

        initializer {
            InsertPsnViewModel(aplikasiKlinik().container.pasienRepository)
        }
        initializer { DetailPsnViewModel(createSavedStateHandle(),aplikasiKlinik().container.pasienRepository) }
        initializer { UpdatePsnViewModel(createSavedStateHandle(),aplikasiKlinik().container.pasienRepository) }

        //Dokter
        initializer {
            HomeDtrViewModel(aplikasiKlinik().container.dokterRepository)
            // Pake repository dari aplikasi untuk HomeViewModel
        }
        initializer {
            InsertDtrViewModel(aplikasiKlinik().container.dokterRepository)
        }
        initializer { DetailDtrViewModel(createSavedStateHandle(),aplikasiKlinik().container.dokterRepository) }
        initializer { UpdateDtrViewModel(createSavedStateHandle(),aplikasiKlinik().container.dokterRepository) }

        //Dokter
        initializer {
            HomeJhViewModel(aplikasiKlinik().container.jenisHewanRepository)
            // Pake repository dari aplikasi untuk HomeViewModel
        }
        initializer {
            InsertJhViewModel(aplikasiKlinik().container.jenisHewanRepository)
        }
        initializer { DetailJhViewModel(createSavedStateHandle(),aplikasiKlinik().container.jenisHewanRepository) }
        initializer { UpdateJhViewModel(createSavedStateHandle(),aplikasiKlinik().container.jenisHewanRepository) }

    }




    fun CreationExtras.aplikasiKlinik(): KlinikApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KlinikApplications)
    // Ambil aplikasi utama (cast dari Application) sebagai MahasiswaApplications

}