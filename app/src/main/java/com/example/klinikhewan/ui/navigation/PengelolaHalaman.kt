package com.example.pertemuan12.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.klinikhewan.ui.view.dokter.DestinasiDtrDetailDtr
import com.example.klinikhewan.ui.view.dokter.DestinasiHomeDtr
import com.example.klinikhewan.ui.view.dokter.DestinasiUpdateDtr
import com.example.klinikhewan.ui.view.dokter.DestinasiUpdateDtr.UpdateDtrView
import com.example.klinikhewan.ui.view.dokter.DetailDtrView
import com.example.klinikhewan.ui.view.dokter.EntryDtrScreen
import com.example.klinikhewan.ui.view.dokter.HomeDtrScreen
import com.example.klinikhewan.ui.view.jenisHewan.DestinasiEntryJh
import com.example.klinikhewan.ui.view.jenisHewan.DestinasiHomeJh
import com.example.klinikhewan.ui.view.jenisHewan.DestinasiJhDetailJh
import com.example.klinikhewan.ui.view.jenisHewan.DestinasiUpdateJh
import com.example.klinikhewan.ui.view.jenisHewan.DestinasiUpdateJh.UpdateJhView
import com.example.klinikhewan.ui.view.jenisHewan.DetailJhView
import com.example.klinikhewan.ui.view.jenisHewan.EntryJhScreen
import com.example.klinikhewan.ui.view.jenisHewan.HomeJhScreen
import com.example.klinikhewan.ui.view.pasien.DestinasiEntryPsn
import com.example.klinikhewan.ui.view.pasien.DestinasiHomePsn
import com.example.klinikhewan.ui.view.pasien.DestinasiPsnDetailPsn
import com.example.klinikhewan.ui.view.pasien.DestinasiUpdatePsn
import com.example.klinikhewan.ui.view.pasien.DetailPsnView
import com.example.klinikhewan.ui.view.pasien.EntryPsnScreen
import com.example.klinikhewan.ui.view.pasien.HomePsnScreen
import com.example.klinikhewan.ui.view.pasien.UpdatePsnView
import com.example.klinikhewan.ui.viewmodel.dokter.DestinasiEntryDtr


//@Composable
//fun PengelolaHalaman(
//    modifier: Modifier = Modifier,
//    navController: NavHostController = rememberNavController() // Objek controller buat navigasi
//
//) {
//    NavHost(
//        navController = navController,
//        startDestination = DestinasiHomePsn.route, // Start screen (Home)
//        modifier = Modifier
//    ) {
//        // **Destinasi Home**: layar utama aplikasi
//        composable(DestinasiHomePsn.route) {
//            HomePsnScreen(
//                navigateToItemEntry = { navController.navigate(DestinasiEntryPsn.route) },
//                onDetailClick = { id_hewan ->
//                    // Navigasi ke destinasi Detail dengan menyertakan id_hewan
//                    navController.navigate("${DestinasiPsnDetailPsn.route}/$id_hewan") {
//                        // Menggunakan popUpTo untuk memastikan navigasi ke Detail dan menghapus stack sebelumnya jika perlu
//                        popUpTo(DestinasiHomePsn.route) {
//                            inclusive = true  // Termasuk juga destinasi yang akan dipopUp
//                        }
//                    }
//                    println("PengelolaHalaman: id_hewan = $id_hewan")
//                }
//            )
//        }
//
//        // **Destinasi Entry**: form mahasiswa baru
//        composable(DestinasiEntryPsn.route) {
//            EntryPsnScreen(
//                navigateBack = {
//                    navController.navigate(DestinasiHomePsn.route) { // Navigasi balik ke Home
//                        popUpTo(DestinasiHomePsn.route) {
//                            inclusive = true // Menghapus stack untuk menghindari double back
//                        }
//                    }
//                }
//            )
//        }
//
//        // **Destinasi Detail**: Layar Detail Pasien berdasarkan id_hewan
//        composable(DestinasiPsnDetailPsn.routesWithArg) { backStackEntry ->
//            // Mendapatkan id_hewan dari argument route
//            val id_hewan = backStackEntry.arguments?.getString(DestinasiPsnDetailPsn.ID_HEWAN)
//
//            id_hewan?.let {
//                // Pastikan ini adalah composable yang benar dan sesuai untuk DetailPsnViewModel
//                DetailPsnView(
//                    id_hewan = it, // Mengirimkan id_hewan ke DetailPsnViewModel
//                    navigateBack = {
//                        // Aksi ketika tombol "Kembali" ditekan
//                        navController.navigate(DestinasiHomePsn.route) {
//                            popUpTo(DestinasiHomePsn.route) {
//                                inclusive = true // Pop sampai ke DestinasiHome
//                            }
//                        }
//                    },
//                    onEditClick = {
//                        // Navigasi ke halaman update dengan id_hewan sebagai argumen
//                        navController.navigate("${DestinasiUpdatePsn.route}/$it")
//                    }
//                )
//            }
//        }
//
//        // **Destinasi Update**: Layar untuk update data Pasien
//        composable(DestinasiUpdatePsn.routesWithArg) { backStackEntry ->
//            // Mendapatkan id_hewan dari argument route
//            val id_hewan = backStackEntry.arguments?.getString(DestinasiUpdatePsn.ID_HEWAN)
//
//            id_hewan?.let {
//                // Pastikan untuk navigasi ke halaman update Pasien dengan id_hewan
//                UpdatePsnView(
//                    id_hewan = it, // Kirim id_hewan untuk diproses di layar update
//                    navigateBack = {
//                        navController.popBackStack() // Kembali ke layar sebelumnya
//                    }
//                )
//            }
//        }
//    }
//}
@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController() // Objek controller untuk navigasi
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeDtr.route, // Destinasi awal, sesuai dengan kode pertama
        modifier = modifier
    ) {
        // **Destinasi Home**: layar utama aplikasi
        composable(DestinasiHomeDtr.route) {
            HomeDtrScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryDtr.route) },
                onDetailClick = { id_dokter ->
                    // Navigasi ke Destinasi Detail dengan menyertakan id_dokter
                    navController.navigate("${DestinasiDtrDetailDtr.route}/$id_dokter") {
                        popUpTo(DestinasiHomeDtr.route) { inclusive = true }
                    }
                }
            )
        }

        // **Destinasi Entry**: layar untuk menambahkan data
        composable(DestinasiEntryDtr.route) {
            EntryDtrScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeDtr.route) {
                        popUpTo(DestinasiHomeDtr.route) { inclusive = true }
                    }
                }
            )
        }

        // **Destinasi Detail**: layar detail untuk dokter berdasarkan id_dokter
        composable(DestinasiDtrDetailDtr.routesWithArg) { backStackEntry ->
            val id_dokter = backStackEntry.arguments?.getString(DestinasiDtrDetailDtr.ID_DOKTER)

            id_dokter?.let {
                DetailDtrView(
                    id_dokter = it, // Mengirim id_dokter untuk detail
                    navigateBack = {
                        navController.navigate(DestinasiHomeDtr.route) {
                            popUpTo(DestinasiHomeDtr.route) { inclusive = true }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateDtr.route}/$it")
                    }
                )
            }
        }

        // **Destinasi Update**: layar untuk update data dokter
        composable(DestinasiUpdateDtr.routesWithArg) { backStackEntry ->
            val id_dokter = backStackEntry.arguments?.getString(DestinasiUpdateDtr.ID_DOKTER)

            id_dokter?.let {
                UpdateDtrView(
                    id_dokter = it,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

//@Composable
//fun PengelolaHalaman(
//    modifier: Modifier = Modifier,
//    navController: NavHostController = rememberNavController() // Objek controller buat navigasi
//
//) {
//    NavHost(
//        navController = navController,
//        startDestination = DestinasiHomeJh.route, // Start screen (Home)
//        modifier = Modifier
//    ) {
//        // **Destinasi Home**: layar utama aplikasi
//        composable(DestinasiHomeJh.route) {
//            HomeJhScreen(
//                navigateToItemEntry = { navController.navigate(DestinasiEntryJh.route) },
//                onDetailClick = { id_jenis_hewan ->
//                    // Navigasi ke destinasi Detail dengan menyertakan id_hewan
//                    navController.navigate("${DestinasiJhDetailJh.route}/$id_jenis_hewan") {
//                        // Menggunakan popUpTo untuk memastikan navigasi ke Detail dan menghapus stack sebelumnya jika perlu
//                        popUpTo(DestinasiHomeJh.route) {
//                            inclusive = true  // Termasuk juga destinasi yang akan dipopUp
//                        }
//                    }
//                    println("PengelolaHalaman: id_hewan = $id_jenis_hewan")
//                }
//            )
//        }
//
//        // **Destinasi Entry**: form mahasiswa baru
//        composable(DestinasiEntryJh.route) {
//            EntryJhScreen(
//                navigateBack = {
//                    navController.navigate(DestinasiHomeJh.route) { // Navigasi balik ke Home
//                        popUpTo(DestinasiHomeJh.route) {
//                            inclusive = true // Menghapus stack untuk menghindari double back
//                        }
//                    }
//                }
//            )
//        }
//
//        // **Destinasi Detail**: Layar Detail Pasien berdasarkan id_hewan
//        composable(DestinasiJhDetailJh.routesWithArg) { backStackEntry ->
//            // Mendapatkan id_hewan dari argument route
//            val id_hewan = backStackEntry.arguments?.getString(DestinasiJhDetailJh.ID_JENSI_HEWAN)
//
//            id_hewan?.let {
//                // Pastikan ini adalah composable yang benar dan sesuai untuk DetailPsnViewModel
//                DetailJhView (
//                    id_jenis_hewan = it, // Mengirimkan id_hewan ke DetailPsnViewModel
//                    navigateBack = {
//                        // Aksi ketika tombol "Kembali" ditekan
//                        navController.navigate(DestinasiHomeJh.route) {
//                            popUpTo(DestinasiHomeJh.route) {
//                                inclusive = true // Pop sampai ke DestinasiHome
//                            }
//                        }
//                    },
//                    onEditClick = {
//                        // Navigasi ke halaman update dengan id_hewan sebagai argumen
//                        navController.navigate("${DestinasiUpdateJh.route}/$it")
//                    }
//                )
//            }
//        }
//
//        // **Destinasi Update**: Layar untuk update data Pasien
//        composable(DestinasiUpdateJh.routesWithArg) { backStackEntry ->
//            // Mendapatkan id_hewan dari argument route
//            val id_hewan = backStackEntry.arguments?.getString(DestinasiUpdateJh.ID_JENSI_HEWAN)
//
//            id_hewan?.let {
//                // Pastikan untuk navigasi ke halaman update Pasien dengan id_hewan
//                UpdateJhView(
//                    id_jenis_hewan = it, // Kirim id_hewan untuk diproses di layar update
//                    navigateBack = {
//                        navController.popBackStack() // Kembali ke layar sebelumnya
//                    }
//                )
//            }
//        }
//    }
//}

