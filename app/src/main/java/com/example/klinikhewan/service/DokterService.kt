package com.example.klinikhewan.service

import com.example.klinikhewan.model.AllDokterResponse
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.model.DokterDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DokterService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    // Mengambil semua data dokter
    @GET(".")
    suspend fun getAllDokter(): AllDokterResponse

    // Mengambil data mahasiswa berdasarkan ID
    @GET("{id_dokter}")
    suspend fun getDokterbyId(@Path("id_dokter") id_dokter: String): DokterDetailResponse
    // Menambahkan mahasiswa baru ke database

    @POST("store")
    suspend fun insertDokter(@Body dokter: Dokter)

    @PUT("{id_dokter}")
    suspend fun updateDokter(@Path("id_dokter") id_dokter: String, @Body dokter: Dokter)
    // Menghapus berdasarkan ID
    // @DELETE("deletemahasiswa.php/{nim}")
    @DELETE("{id_dokter}")
    suspend fun deleteDokter(@Path("id_dokter") id_dokter: String): Response<Void>
}
