package com.example.klinikhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Perawatan(
    @SerialName("id_perawatan")
    val id_perawatan: String,

    @SerialName("id_hewan")
    val id_hewan: String,

    @SerialName("id_dokter")
    val id_dokter: String,

    @SerialName("tanggal_perawatan")
    val tanggal_perawatan: String,

    @SerialName("detail_perawatan")
    val detail_perawatan: String
)


@Serializable
data class AllPerawatanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Perawatan>
)

@Serializable
data class PerawatanDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Perawatan
)