package com.example.klinikhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JenisHewan(
    @SerialName("id_jenis_hewan")
    val id_jenis_hewan : String,

    @SerialName("nama_jenis_hewan")
    val nama_jenis_hewan : String,

    @SerialName("deskripsi")
    val deskripsi : String,

)
@Serializable
data class AllJenisHewanResponse(
    val status: Boolean,
    val message: String,
    val data : List<JenisHewan>
)

@Serializable
data class JenisHewanDetailResponse(
    val status: Boolean,
    val message: String,
    val data : JenisHewan
)

