package com.trabajoterminal.lsmapp.data

import android.widget.ArrayAdapter
import com.trabajoterminal.lsmapp.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow

interface PosicionManoReceiveManager {
    val data: MutableSharedFlow<Resource<PosicionManoResult>>
    fun buscarDispositivos(): ArrayList<DispositivoBt>
    fun conectar(direccionDispositivo: String)
    fun desconectar()
    fun comienzaRecibir()
    fun cerrarConexion()
    fun estadoConexion(): Boolean
}