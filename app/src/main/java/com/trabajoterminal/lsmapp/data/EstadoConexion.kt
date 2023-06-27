package com.trabajoterminal.lsmapp.data

sealed interface EstadoConexion {
    object Conectado: EstadoConexion
    object Desconectado: EstadoConexion
    object NoInicializado: EstadoConexion
    object Inicializando: EstadoConexion
}