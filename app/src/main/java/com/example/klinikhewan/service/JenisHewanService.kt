package com.example.klinikhewan.service

import com.example.klinikhewan.model.AllDokterResponse
import com.example.klinikhewan.model.AllJenisHewanResponse
import com.example.klinikhewan.model.Dokter
import com.example.klinikhewan.model.DokterDetailResponse
import com.example.klinikhewan.model.JenisHewan
import com.example.klinikhewan.model.JenisHewanDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JenisHewanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET(".")
    suspend fun getAllJenisHewan(): AllJenisHewanResponse


    @GET("{id_jenis_hewan}")
    suspend fun getJenisHewanById(@Path("id_jenis_hewan") id_jenis_hewan: String): JenisHewanDetailResponse


    @POST("store")
    suspend fun insertJenisHewan(@Body jenisHewan: JenisHewan)

    @PUT("{id_jenis_hewan}")
    suspend fun updateJenisHewan(@Path("id_jenis_hewan") id_jenis_hewan: String, @Body jenisHewan: JenisHewan)


    @DELETE("{id_jenis_hewan}")
    suspend fun deleteJenisHewan(@Path("id_jenis_hewan") id_jenis_hewan: String): Response<Void>
}