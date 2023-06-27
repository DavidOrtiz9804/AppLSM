package com.trabajoterminal.lsmapp.presentation.view

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trabajoterminal.lsmapp.R
import com.trabajoterminal.lsmapp.data.validapalabra.DataProviderValidacion
import com.trabajoterminal.lsmapp.presentation.view.navigation.AppScreens
import com.trabajoterminal.lsmapp.presentation.viewmodel.PosManoViewModel
import kotlinx.coroutines.*

@Composable
fun PracticarSena(
    navController: NavController,
    palabra: String?,
    viewModel: PosManoViewModel = hiltViewModel()
) {
    Scaffold() {
        BodyContentPracticarSena(navController,palabra,viewModel)
    }
}

@Composable
fun BodyContentPracticarSena(
    navController: NavController,
    palabra: String?,
    viewModel: PosManoViewModel = hiltViewModel()
) {
    viewModel.comienzaRecibir()

    var palabraMostrada by remember { mutableStateOf("") }

    val abecedario = DataProviderValidacion.letrasPosicion

    val coroutineScope = CoroutineScope(Dispatchers.Main)

    val context = LocalContext.current

    var validacion by remember { mutableStateOf(true) }
    var band by remember { mutableStateOf(true) }

    LaunchedEffect(palabra) {
        delay(5000)
        if (palabra != null) {
            for (i in palabra.indices) {
                palabraMostrada = palabra.substring(0, i + 1)
                coroutineScope.launch {
                    for(i2 in abecedario) {
                        if(i2.nameLetra == palabra[i].toString()) {
                            if (
                                viewModel.pulgar!!.toInt() in i2.pulgarMin..i2.pulgarMax &&
                                viewModel.indice!!.toInt() in i2.indiceMin..i2.indiceMax &&
                                viewModel.medio!!.toInt() in i2.medioMin..i2.medioMax &&
                                viewModel.anular!!.toInt() in i2.anularMin..i2.anularMax &&
                                viewModel.menique!!.toInt() in i2.meniqueMin..i2.meniqueMax
                            ) {
                                if(validacion) { validacion = true }
                                Log.d(TAG,"Letra: ${i2.nameLetra}")
                            } else {
                                validacion = false
                            }
                            //Log.d(TAG,"L: ${i2.nameLetra} p: ${viewModel.pulgar!!.toInt()}")
                        }
                    }
                    Toast.makeText(context, "Siguiente seña", Toast.LENGTH_LONG).show()
                }
                delay(5000)
            }
        }
        band = false
        Log.d(TAG,"Validacion: $validacion")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(band) {
            Text(
                text = palabraMostrada,
                fontSize = 50.sp,
            )
        } else {
            if(!validacion) {
                Image( // Logo
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(R.drawable.sena_incorrecto),
                    contentDescription = "Sena_Incorrecta")
            } else {
                Image( // Logo
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(R.drawable.sena_correcto),
                    contentDescription = "Sena_Correcta")
            }
            Spacer(modifier = Modifier.padding(30.dp))
            Button(
                onClick = { navController.popBackStack()  },
                contentPadding = PaddingValues(
                    start = 70.dp,
                    top = 20.dp,
                    end =70.dp,
                    bottom = 20.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
            ) {
                Text(
                    text = "REPETIR ANIMACION",
                    style = MaterialTheme.typography.button,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = { navController.navigate(AppScreens.MenuPrincipal.route) },
                contentPadding = PaddingValues(
                    start = 50.dp,
                    top = 20.dp,
                    end = 50.dp,
                    bottom = 20.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
            ) {
                Text(
                    text = "MENU PRINCIPAL",
                    style = MaterialTheme.typography.button,
                    color = Color.White
                )
            }
        }
    }
}
