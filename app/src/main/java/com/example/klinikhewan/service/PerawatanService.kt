package com.example.klinikhewan.service

import com.example.klinikhewan.model.AllPerawatanResponse
import com.example.klinikhewan.model.Perawatan
import com.example.klinikhewan.model.PerawatanDetailResponse
import com.example.klinikhewan.repository.PerawatanRepository
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.io.IOException

interface PerawatanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    // Mengambil semua data perawatan
    @GET(".")
    suspend fun getAllPerawatan(): AllPerawatanResponse

    // Mengambil data mahasiswa berdasarkan ID
    @GET("{id_perawatan}")
    suspend fun getPerawatanbyId(@Path("id_perawatan") id_perawatan: String): PerawatanDetailResponse
    // Menambahkan mahasiswa baru ke database

    @POST("store")
    suspend fun insertPerawatan(@Body perawatan: Perawatan)

    @PUT("{id_perawatan}")
    suspend fun updatePerawatan(@Path("id_perawatan") id_perawatan: String, @Body perawatan: Perawatan)
    // Menghapus berdasarkan ID
    // @DELETE("deletemahasiswa.php/{nim}")
    @DELETE("{id_perawatan}")
    suspend fun deletePerawatan(@Path("id_perawatan") id_perawatan: String): Response<Void>
}

