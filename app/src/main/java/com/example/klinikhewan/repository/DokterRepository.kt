package com.example.klinikhewan.repository

import com.example.klinikhewan.model.AllDokterResponse
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.service.DokterService
import okio.IOException

interface DokterRepository {
    // Mengambil daftar semua dokter
    suspend fun getAllDokter(): AllDokterResponse

    // Menambahkan data dokter
    suspend fun insertDokter(dokter: Dokter)

    // Memperbarui data dokter berdasarkan ID
    suspend fun updateDokter(id_dokter: String, dokter: Dokter)

    // Menghapus dokter berdasarkan ID
    suspend fun deleteDokter(id_dokter: String)

    // Mengambil data dokter berdasarkan ID
    suspend fun getDokterById(id_dokter: String): Dokter
}

class NetworkDokterRepository(
    private val dokterApiService: DokterService // Menggunakan API Dokter sebagai sumber data
) : DokterRepository {
    override suspend fun getAllDokter(): AllDokterResponse =
        dokterApiService.getAllDokter()

    override suspend fun insertDokter(dokter: Dokter) {
        dokterApiService.insertDokter(dokter)
    }

    override suspend fun updateDokter(id_dokter: String, dokter: Dokter) {
        dokterApiService.updateDokter(id_dokter, dokter)
    }

    override suspend fun deleteDokter(id_dokter: String) {
        try {
            val response = dokterApiService.deleteDokter(id_dokter)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Dokter. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getDokterById(id_dokter: String): Dokter {
        return dokterApiService.getDokterbyId(id_dokter).data
    }
}
