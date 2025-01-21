package com.example.klinikhewan.repository

import com.example.klinikhewan.model.AllJenisHewanResponse
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.service.JenisHewanService
import okio.IOException

interface JenisHewanRepository {
    // Mengambil daftar semua jenis hewan
    suspend fun getAllJenisHewan(): AllJenisHewanResponse

    // Menambahkan data jenis hewan
    suspend fun insertJenisHewan(jenisHewan: JenisHewan)

    // Memperbarui data jenis hewan berdasarkan ID
    suspend fun updateJenisHewan(id_jenis_hewan: String, jenisHewan: JenisHewan)

    // Menghapus jenis hewan berdasarkan ID
    suspend fun deleteJenisHewan(id_jenis_hewan: String)

    // Mengambil data jenis hewan berdasarkan ID
    suspend fun getJenisHewanById(id_jenis_hewan: String): JenisHewan
}

class NetworkJenisHewanRepository(
    private val jenisHewanApiService: JenisHewanService // Menggunakan API JenisHewan sebagai sumber data
) : JenisHewanRepository {

    override suspend fun getAllJenisHewan(): AllJenisHewanResponse =
        jenisHewanApiService.getAllJenisHewan()

    override suspend fun insertJenisHewan(jenisHewan: JenisHewan) {
        jenisHewanApiService.insertJenisHewan(jenisHewan)
    }

    override suspend fun updateJenisHewan(id_jenis_hewan: String, jenisHewan: JenisHewan) {
        jenisHewanApiService.updateJenisHewan(id_jenis_hewan, jenisHewan)
    }

    override suspend fun deleteJenisHewan(id_jenis_hewan: String) {
        try {
            val response = jenisHewanApiService.deleteJenisHewan(id_jenis_hewan)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete JenisHewan. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getJenisHewanById(id_jenis_hewan: String): JenisHewan {
        return jenisHewanApiService.getJenisHewanById(id_jenis_hewan).data
    }
}
