package com.example.klinikhewan.repository

import com.example.klinikhewan.model.AllPerawatanResponse
import com.example.klinikhewan.model.Perawatan
import com.example.klinikhewan.service.PerawatanService
import java.io.IOException

interface PerawatanRepository {
    // Mengambil daftar semua dokter
    suspend fun getAllPerawatan(): AllPerawatanResponse

    // Menambahkan data dokter
    suspend fun insertPerawatan(perawatan: Perawatan)

    // Memperbarui data dokter berdasarkan ID
    suspend fun updatePerawatan(id_hewan: String, perawatan: Perawatan)

    // Menghapus dokter berdasarkan ID
    suspend fun deletePerawatan(id_hewan: String)

    // Mengambil data dokter berdasarkan ID
    suspend fun getPerawatanById(id_hewan: String): Perawatan
}
class NetworkPerawatanRepository(
    private val perawatanApiService: PerawatanService // Menggunakan API Dokter sebagai sumber data
) : PerawatanRepository {
    override suspend fun getAllPerawatan(): AllPerawatanResponse =
        perawatanApiService.getAllPerawatan()

    override suspend fun insertPerawatan(perawatan: Perawatan) {
        perawatanApiService.insertPerawatan(perawatan)
    }

    override suspend fun updatePerawatan(id_perawatan: String, perawatan: Perawatan) {
        perawatanApiService.updatePerawatan(id_perawatan, perawatan)
    }

    override suspend fun deletePerawatan(id_perawatan: String) {
        try {
            val response = perawatanApiService.deletePerawatan(id_perawatan)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Perawatan. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPerawatanById(id_perawatan: String): Perawatan {
        return perawatanApiService.getPerawatanbyId(id_perawatan).data
    }
}