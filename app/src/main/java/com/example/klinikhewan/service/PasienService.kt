package com.example.klinikhewan.service

import AllPasienResponse
import Pasien
import PasienDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PasienService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    // Mengambil semua data pasien
    @GET(".")
    suspend fun getAllPasien(): AllPasienResponse

    // Mengambil data mahasiswa berdasarkan ID
    @GET("{id_hewan}")
    suspend fun getPasienbyId(@Path("id_hewan") id_hewan: String): PasienDetailResponse
    // Menambahkan mahasiswa baru ke database

    @POST("store")
    suspend fun insertPasien(@Body pasien: Pasien)

    @PUT("{id_hewan}")
    suspend fun updatePasien(@Path("id_hewan") id_hewan: String, @Body pasien: Pasien)
    // Menghapus berdasarkan ID
    // @DELETE("deletemahasiswa.php/{nim}")
    @DELETE("{id_hewan}")
    suspend fun deletePasien(@Path("id_hewan") id_hewan: String): Response<Void>
}