package com.example.klinikhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dokter(
    @SerialName("id_dokter")
    val id_dokter : String,

    @SerialName("nama_dokter")
    val nama_dokter : String,

    @SerialName("spesialisasi")
    val spesialisasi : String,

    @SerialName("kontak")
    val kontak : String
)

@Serializable
data class AllDokterResponse(
    val status: Boolean,
    val message: String,
    val data : List<Dokter>
)

@Serializable
data class DokterDetailResponse(
    val status: Boolean,
    val message: String,
    val data : Dokter
)