package com.trabajoterminal.lsmapp.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.trabajoterminal.lsmapp.data.DispositivoBt
import com.trabajoterminal.lsmapp.data.EstadoConexion
import com.trabajoterminal.lsmapp.data.PosicionManoReceiveManager
import com.trabajoterminal.lsmapp.data.PosicionManoResult
import com.trabajoterminal.lsmapp.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

@SuppressLint("MissingPermission")
class PosicionManoBtReceiveManager @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    private val context: Context
): PosicionManoReceiveManager {

    override val data: MutableSharedFlow<Resource<PosicionManoResult>> = MutableSharedFlow()

    var dispositivosBluetooth: ArrayList<DispositivoBt> = arrayListOf()
    private var bluetoothSocket: BluetoothSocket? = null
    var isConected: Boolean = false
    val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun buscarDispositivos(): ArrayList<DispositivoBt> {
        if(bluetoothAdapter.isEnabled){
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
            dispositivosBluetooth.clear()

            pairedDevices?.forEach { device ->
                val nombreDispositivo = device.name
                val direccionFisicaDispositivo = device.address //Direccion MAC de los dispositivos encontrados
                val dispositivoBt = DispositivoBt(nombreDispositivo, direccionFisicaDispositivo)
                dispositivosBluetooth.add(dispositivoBt)
            }
            return dispositivosBluetooth
        } else {
            val sinDispositivos = "Ningun dispositivo encontrado."
            val dispositivoBt = DispositivoBt(sinDispositivos, sinDispositivos)
            dispositivosBluetooth.add(dispositivoBt)
            return dispositivosBluetooth
        }
    }

    override fun conectar(direccionDispositivo: String) {
        try {
            if (bluetoothSocket == null || !isConected) {
                coroutineScope.launch {
                    bluetoothAdapter.cancelDiscovery()
                    val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(direccionDispositivo)
                    bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID)
                    bluetoothSocket!!.connect()
                    if (bluetoothSocket != null) {
                        readData()
                    }
                }
                Toast.makeText(context, "CONEXION EXITOSA", Toast.LENGTH_LONG).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context,"ERROR DE CONEXION",Toast.LENGTH_LONG).show()
        }
    }

    override fun desconectar() {
        if (bluetoothSocket != null) {
            bluetoothSocket!!.inputStream.close()
            bluetoothSocket!!.close()
            Toast.makeText(context, "BLUETOOTH DESCONECTADO", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "NO HAY CONEXION BLUETOOTH", Toast.LENGTH_LONG).show()
        }
    }

    override fun comienzaRecibir() {

    }

    override fun cerrarConexion() {
        TODO("Not yet implemented")
    }

    override fun estadoConexion(): Boolean {
        return (bluetoothSocket != null)
    }


    private suspend fun readData() {
        withContext(Dispatchers.IO) {
            val inputStream = bluetoothSocket!!.inputStream
            val buffer = ByteArray(2096)
            while (bluetoothSocket!!.isConnected) {
                val numRead = inputStream.read(buffer)
                if(numRead > 0) {
                    val receiveData = buffer.copyOf(numRead)
                    procesarDatos(receiveData)
                    delay(5000)
                }
            }
        }
    }

    private fun procesarDatos(dedo: ByteArray) {
        val receivedString = String(dedo)
        if (receivedString.length==21) {

            val pul = receivedString.subSequence(0, 4)
            val ind = receivedString.subSequence(4, 8)
            val med = receivedString.subSequence(8, 12)
            val anu = receivedString.subSequence(12, 16)
            val men = receivedString.subSequence(16, 20)

            val posicionManoResult = PosicionManoResult(
                pul as String,
                ind as String,
                med as String,
                anu as String,
                men as String,
                EstadoConexion.Conectado
            )
            coroutineScope.launch {
                data.emit(
                    Resource.Success(posicionManoResult)
                )
            }

            Log.i(TAG, "d: $receivedString")

        } else {
            Log.i(TAG, "Dato Incierto")
        }
    }

}