package com.example.klinikhewan.repository

import AllPasienResponse
import Pasien

import com.example.klinikhewan.service.PasienService
import java.io.IOException

interface PasienRepository {
    // Mengambil daftar semua dokter
    suspend fun getAllPasien(): AllPasienResponse

    // Menambahkan data dokter
    suspend fun insertPasien(pasien: Pasien)

    // Memperbarui data dokter berdasarkan ID
    suspend fun updatePasien(id_hewan: String, pasien: Pasien)

    // Menghapus dokter berdasarkan ID
    suspend fun deletePasien(id_hewan: String)

    // Mengambil data dokter berdasarkan ID
    suspend fun getPasienById(id_hewan: String): Pasien
}

class NetworkPasienRepository(
    private val pasienApiService: PasienService // Menggunakan API Dokter sebagai sumber data
) : PasienRepository {
    override suspend fun getAllPasien(): AllPasienResponse =
        pasienApiService.getAllPasien()

    override suspend fun insertPasien(pasien: Pasien) {
        pasienApiService.insertPasien(pasien)
    }

    override suspend fun updatePasien(id_hewan: String, pasien: Pasien) {
        pasienApiService.updatePasien(id_hewan, pasien)
    }

    override suspend fun deletePasien(id_hewan: String) {
        try {
            val response = pasienApiService.deletePasien(id_hewan)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Pasien. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPasienById(id_hewan: String): Pasien {
        return pasienApiService.getPasienbyId(id_hewan).data
    }
}
