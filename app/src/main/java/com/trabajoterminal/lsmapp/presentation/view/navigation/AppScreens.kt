package com.trabajoterminal.lsmapp.presentation.view.navigation

sealed class AppScreens(val route: String) {
    object MenuPrincipal: AppScreens("menu_principal")
    object EstadoConexion: AppScreens("estado_conexion")
    object ConectarDispositivo: AppScreens("conectar_dispositivo")
    object Categorias: AppScreens("categorias")
    object Subcategorias: AppScreens("subcategorias")
    object IngresaPalabra: AppScreens("ingresa_palabra")
    object Animacion: AppScreens("animacion")
    object PracticarSena: AppScreens("practicar_sena")
}
