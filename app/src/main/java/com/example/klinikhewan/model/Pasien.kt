import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Pasien(
    @SerialName("id_hewan")
    val id_hewan: String,

    @SerialName("nama_hewan")
    val nama_hewan: String,

    @SerialName("id_jenis_hewan")
    val id_jenis_hewan: String,

    @SerialName("pemilik")
    val pemilik: String,

    @SerialName("kontak_pemilik")
    val kontak_pemilik: String,

    @SerialName("tanggal_lahir")
    val tanggal_lahir: String,

    @SerialName("catatan_kesehatan")
    val catatan_kesehatan: String
)

@Serializable
data class AllPasienResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pasien>
)

@Serializable
data class PasienDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pasien
)
