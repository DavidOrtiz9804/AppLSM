package com.trabajoterminal.lsmapp.presentation.viewmodel

import android.widget.ArrayAdapter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trabajoterminal.lsmapp.data.DispositivoBt
import com.trabajoterminal.lsmapp.data.EstadoConexion
import com.trabajoterminal.lsmapp.data.PosicionManoReceiveManager
import com.trabajoterminal.lsmapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PosManoViewModel @Inject constructor(
    private val posicionManoReceiveManager: PosicionManoReceiveManager
): ViewModel() {

    var pulgar by mutableStateOf<String?>(null)
        private set

    var indice by mutableStateOf<String?>(null)
        private set

    var medio by mutableStateOf<String?>(null)
        private set

    var anular by mutableStateOf<String?>(null)
        private set

    var menique by mutableStateOf<String?>(null)
        private set

    var connectionState by mutableStateOf<EstadoConexion>(EstadoConexion.NoInicializado)

    fun buscarDispositivos(): ArrayList<DispositivoBt> {
        return posicionManoReceiveManager.buscarDispositivos()
    }

    fun conectar(direccionDispositivo: String) {
        posicionManoReceiveManager.conectar(direccionDispositivo)
    }

    fun comienzaRecibir() {
        mostrarCambios()
    }

    fun estadoConexionBluetooth(): Boolean {
        return posicionManoReceiveManager.estadoConexion()
    }

    fun desconectarBluetooth() {
        posicionManoReceiveManager.desconectar()
    }

    private fun mostrarCambios() {
        viewModelScope.launch {
            posicionManoReceiveManager.data.collect { result ->
                when(result) {
                    is Resource.Success -> {
                        connectionState = result.data.estadoConexion
                        pulgar = result.data.pulgar
                        indice = result.data.indice
                        medio = result.data.medio
                        anular = result.data.anular
                        menique = result.data.menique
                    }
                }
            }
        }
    }



}