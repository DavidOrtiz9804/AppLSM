package com.trabajoterminal.lsmapp.presentation.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.trabajoterminal.lsmapp.presentation.view.Animacion
import com.trabajoterminal.lsmapp.presentation.view.Categorias
import com.trabajoterminal.lsmapp.presentation.view.ConectarDispositivo
import com.trabajoterminal.lsmapp.presentation.view.EstadoConexion
import com.trabajoterminal.lsmapp.presentation.view.IngresaPalabra
import com.trabajoterminal.lsmapp.presentation.view.MenuPrincipal
import com.trabajoterminal.lsmapp.presentation.view.PracticarSena
import com.trabajoterminal.lsmapp.presentation.view.Subcategorias

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MenuPrincipal.route) {
        composable(route = AppScreens.MenuPrincipal.route) {
            MenuPrincipal(navController)
        }
        composable(route = AppScreens.EstadoConexion.route) {
            EstadoConexion(navController)
        }
        composable(route = AppScreens.ConectarDispositivo.route) {
            ConectarDispositivo(navController)
        }
        composable(route = AppScreens.Categorias.route) {
            Categorias(navController)
        }
        composable(
            route = AppScreens.Subcategorias.route + "/{text}",
            arguments = listOf(navArgument(name = "text") {
                type = NavType.StringType
            })
        ) {
            Subcategorias(navController, it.arguments?.getString("text"))
        }
        composable(route = AppScreens.IngresaPalabra.route) {
            IngresaPalabra(navController)
        }
        composable(
            route = AppScreens.Animacion.route + "/{text}",
            arguments = listOf(navArgument(name = "text") {
                type = NavType.StringType
            })
        ) {
            Animacion(navController, it.arguments?.getString("text"))
        }
        composable(
            route = AppScreens.PracticarSena.route + "/{text}",
            arguments = listOf(navArgument(name = "text") {
                type = NavType.StringType
            })
        ) {
            PracticarSena(navController, it.arguments?.getString("text"))
        }
    }
}