package com.example.klinikhewan.dependeciesinjection

import com.example.klinikhewan.repository.DokterRepository
import com.example.klinikhewan.repository.JenisHewanRepository
import com.example.klinikhewan.repository.NetworkDokterRepository
import com.example.klinikhewan.repository.NetworkJenisHewanRepository
import com.example.klinikhewan.repository.NetworkPasienRepository
import com.example.klinikhewan.repository.PasienRepository
import com.example.klinikhewan.service.DokterService
import com.example.klinikhewan.service.JenisHewanService
import com.example.klinikhewan.service.PasienService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// Interface untuk AppContainer yang menangani semua dependency
interface AppContainer {
    val pasienRepository: PasienRepository
    val dokterRepository: DokterRepository
    val jenisHewanRepository: JenisHewanRepository
}

// Implementasi AppContainer untuk menyimpan semua dependency
class AppContainerImpl : AppContainer {

    // URL dasar untuk mengakses API backend
    private val baseUrlPasien = "http://10.0.2.2:2000/api/pasien/"
    private val baseUrlDokter = "http://10.0.2.2:2000/api/dokter/"
    private val baseUrlJenisHewan = "http://10.0.2.2:2000/api/jenis_hewan/"

    // Note: 10.0.2.2 adalah localhost untuk emulator. Ganti dengan IP perangkat jika diakses dari perangkat lain.

    // Konfigurasi Json untuk menangani data yang tidak dikenal (ignoreUnknownKeys)
    private val json = Json { ignoreUnknownKeys = true }

    // Membuat instance Retrofit untuk pasien
    private val retrofitPasien: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) // Converter JSON ke objek Kotlin
        .baseUrl(baseUrlPasien)
        .build()

    // Membuat instance Retrofit untuk dokter
    private val retrofitDokter: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) // Converter JSON ke objek Kotlin
        .baseUrl(baseUrlDokter)
        .build()

    // Membuat instance Retrofit untuk jenis hewan
    private val retrofitJenisHewan: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) // Converter JSON ke objek Kotlin
        .baseUrl(baseUrlJenisHewan)
        .build()

    // Lazy initialization untuk PasienService, DokterService, dan JenisHewanService
    private val pasienService: PasienService by lazy {
        retrofitPasien.create(PasienService::class.java)
    }

    private val dokterService: DokterService by lazy {
        retrofitDokter.create(DokterService::class.java)
    }

    private val jenisHewanService: JenisHewanService by lazy {
        retrofitJenisHewan.create(JenisHewanService::class.java)
    }

    // Lazy initialization untuk repository Pasien, Dokter, dan Jenis Hewan
    override val pasienRepository: PasienRepository by lazy {
        NetworkPasienRepository(pasienService)
    }

    override val dokterRepository: DokterRepository by lazy {
        NetworkDokterRepository(dokterService)
    }

    override val jenisHewanRepository: JenisHewanRepository by lazy {
        NetworkJenisHewanRepository(jenisHewanService)
    }
}
