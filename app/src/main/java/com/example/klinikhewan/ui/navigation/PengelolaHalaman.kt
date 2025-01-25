package com.example.klinikhewan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.klinikhewan.ui.view.dokter.DestinasiDtrDetailDtr
import com.example.klinikhewan.ui.view.dokter.DestinasiEntryDtr
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
import com.example.klinikhewan.ui.view.perawatan.DestinasiEntryPrn
import com.example.klinikhewan.ui.view.perawatan.DestinasiHomePrn
import com.example.klinikhewan.ui.view.perawatan.DestinasiPrnDetailPrn
import com.example.klinikhewan.ui.view.perawatan.DestinasiUpdatePrn
import com.example.klinikhewan.ui.view.perawatan.DetailPrnView
import com.example.klinikhewan.ui.view.perawatan.EntryPrnScreen
import com.example.klinikhewan.ui.view.perawatan.HomePrnScreen
import com.example.klinikhewan.ui.view.perawatan.UpdatePrnView

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController() // Objek controller buat navigasi
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomePsn.route, // Start screen (Home Pasien)
        modifier = modifier
    ) {
        // **Destinasi Home Pasien**: layar utama aplikasi untuk pasien
        composable(DestinasiHomePsn.route) {
            HomePsnScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPsn.route) },
                navigateToHomeDokter = { navController.navigate(DestinasiHomeDtr.route) },
                navigateToHomeJenisHewan = { navController.navigate(DestinasiHomeJh.route) },
                navigateToHomePerawatan = { navController.navigate(DestinasiHomePrn.route) },
                onDetailClick = { id_hewan ->
                    navController.navigate("${DestinasiPsnDetailPsn.route}/$id_hewan") {
                        popUpTo(DestinasiHomePsn.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // **Destinasi Entry Pasien**: form untuk menambah data pasien
        composable(DestinasiEntryPsn.route) {
            EntryPsnScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePsn.route) {
                        popUpTo(DestinasiHomePsn.route) { inclusive = true }
                    }
                }
            )
        }

        // **Destinasi Detail Pasien**: layar Detail Pasien berdasarkan id_hewan
        composable(DestinasiPsnDetailPsn.routesWithArg) { backStackEntry ->
            val id_hewan = backStackEntry.arguments?.getString(DestinasiPsnDetailPsn.ID_HEWAN)
            id_hewan?.let {
                DetailPsnView(
                    id_hewan = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomePsn.route) {
                            popUpTo(DestinasiHomePsn.route) { inclusive = true }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdatePsn.route}/$it")
                    }
                )
            }
        }

        // **Destinasi Update Pasien**: layar untuk update data pasien
        composable(DestinasiUpdatePsn.routesWithArg) { backStackEntry ->
            val id_hewan = backStackEntry.arguments?.getString(DestinasiUpdatePsn.ID_HEWAN)
            id_hewan?.let {
                UpdatePsnView(
                    id_hewan = it,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }

        // **Destinasi Home Dokter**: layar utama aplikasi untuk dokter
        composable(DestinasiHomeDtr.route) {
            HomeDtrScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryDtr.route) },
                onDetailClick = { id_dokter ->
                    navController.navigate("${DestinasiDtrDetailDtr.route}/$id_dokter") {
                        popUpTo(DestinasiHomeDtr.route) { inclusive = true }
                    }
                }
            )
        }

        // **Destinasi Entry Dokter**: form untuk menambah data dokter
        composable(DestinasiEntryDtr.route) {
            EntryDtrScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeDtr.route) {
                        popUpTo(DestinasiHomeDtr.route) { inclusive = true }
                    }
                }
            )
        }

        // **Destinasi Detail Dokter**: layar Detail Dokter berdasarkan id_dokter
        composable(DestinasiDtrDetailDtr.routesWithArg) { backStackEntry ->
            val id_dokter = backStackEntry.arguments?.getString(DestinasiDtrDetailDtr.ID_DOKTER)
            id_dokter?.let {
                DetailDtrView(
                    id_dokter = it,
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

        // **Destinasi Update Dokter**: layar untuk update data dokter
        composable(DestinasiUpdateDtr.routesWithArg) { backStackEntry ->
            val id_dokter = backStackEntry.arguments?.getString(DestinasiUpdateDtr.ID_DOKTER)
            id_dokter?.let {
                UpdateDtrView(
                    id_dokter = it,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }

        // **Destinasi Home Jenis Hewan**: layar utama aplikasi untuk jenis hewan
        composable(DestinasiHomeJh.route) {
            HomeJhScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryJh.route) },
                onDetailClick = { id_jenis_hewan ->
                    navController.navigate("${DestinasiJhDetailJh.route}/$id_jenis_hewan") {
                        popUpTo(DestinasiHomeJh.route) { inclusive = true }
                    }
                }
            )
        }

        // **Destinasi Entry Jenis Hewan**: form untuk menambah data jenis hewan
        composable(DestinasiEntryJh.route) {
            EntryJhScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeJh.route) {
                        popUpTo(DestinasiHomeJh.route) { inclusive = true }
                    }
                }
            )
        }

        // **Destinasi Detail Jenis Hewan**: layar Detail Jenis Hewan berdasarkan id_jenis_hewan
        composable(DestinasiJhDetailJh.routesWithArg) { backStackEntry ->
            val id_jenis_hewan = backStackEntry.arguments?.getString(DestinasiJhDetailJh.ID_JENSI_HEWAN)
            id_jenis_hewan?.let {
                DetailJhView(
                    id_jenis_hewan = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomeJh.route) {
                            popUpTo(DestinasiHomeJh.route) { inclusive = true }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateJh.route}/$it")
                    }
                )
            }
        }

        // **Destinasi Update Jenis Hewan**: layar untuk update data jenis hewan
        composable(DestinasiUpdateJh.routesWithArg) { backStackEntry ->
            val id_jenis_hewan = backStackEntry.arguments?.getString(DestinasiUpdateJh.ID_JENSI_HEWAN)
            id_jenis_hewan?.let {
                UpdateJhView(
                    id_jenis_hewan = it,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }

        // **Destinasi Home Perawatan**: layar utama aplikasi untuk perawatan
        composable(DestinasiHomePrn.route) {
            HomePrnScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPrn.route) },
                onDetailClick = { id_perawatan ->
                    navController.navigate("${DestinasiPrnDetailPrn.route}/$id_perawatan") {
                        popUpTo(DestinasiHomePrn.route) { inclusive = true }
                    }
                }
            )
        }

        // **Destinasi Entry Perawatan**: form untuk menambah data perawatan
        composable(DestinasiEntryPrn.route) {
            EntryPrnScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePrn.route) {
                        popUpTo(DestinasiHomePrn.route) { inclusive = true }
                    }
                }
            )
        }

        // **Destinasi Detail Perawatan**: layar Detail Perawatan berdasarkan id_perawatan
        composable(DestinasiPrnDetailPrn.routesWithArg) { backStackEntry ->
            val id_perawatan = backStackEntry.arguments?.getString(DestinasiPrnDetailPrn.ID_PERAWATAN)
            id_perawatan?.let {
                DetailPrnView(
                    id_perawatan = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomePrn.route) {
                            popUpTo(DestinasiHomePrn.route) { inclusive = true }
                        }
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdatePrn.route}/$it")
                    }
                )
            }
        }

        // **Destinasi Update Perawatan**: layar untuk update data perawatan
        composable(DestinasiUpdatePrn.routesWithArg) { backStackEntry ->
            val id_perawatan = backStackEntry.arguments?.getString(DestinasiUpdatePrn.ID_PERWATAN)
            id_perawatan?.let {
                UpdatePrnView(
                    id_perwatan = it,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
