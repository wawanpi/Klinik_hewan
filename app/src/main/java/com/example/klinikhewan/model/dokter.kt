package com.example.klinikhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dokter(
    @SerialName("id_dokter")
    val id_dokter : String,

    @SerialName("nama_dokter")
    val nama_dokter : String,

    @SerialName("spesialis")
    val spesialis : String,

    @SerialName("kontak")
    val kontak : String
)