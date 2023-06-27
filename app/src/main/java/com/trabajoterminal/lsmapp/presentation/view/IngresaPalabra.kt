package com.trabajoterminal.lsmapp.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.trabajoterminal.lsmapp.data.validacion.Validacion
import com.trabajoterminal.lsmapp.presentation.view.navigation.AppScreens
import com.trabajoterminal.lsmapp.ui.theme.Montserrat

@Composable
fun IngresaPalabra(navController: NavController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = AppScreens.Categorias.route) },
                backgroundColor = MaterialTheme.colors.background
            ) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.White
                )
            }
        }
    ) {
        BodyContentIngresaPalabra(navController)
    }
}

@Composable
fun BodyContentIngresaPalabra(navController: NavController) {

    var palabra by remember {
        mutableStateOf("Ej. Ayuda")
    }

    var isClicked by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ingresa la palabra que deseas aprender:",
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Spacer(modifier = Modifier.padding(40.dp))
        Text(
            text = "Recuerda que no se pueden incluir las siguientes letras: J, K, Ñ, Q, R, V, X y Z.",
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Montserrat
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.padding(40.dp))
        OutlinedTextField(
            value = palabra,
            onValueChange = { palabra = it },
            label = { Text("Ingresa Palabra:") }
        )
        Spacer(modifier = Modifier.padding(60.dp))
        Button(
            onClick = { isClicked = true },
            contentPadding = PaddingValues(
                start = 50.dp,
                top = 20.dp,
                end = 50.dp,
                bottom = 20.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
        ) {
            Text(
                text = "IR A PRACTICAR",
                style = MaterialTheme.typography.button
            )
        }
        if (isClicked) {
            isClicked = comenzarValidacion(navController, palabra)
        }
    }
}

@Composable
fun comenzarValidacion(navController: NavController, pal: String): Boolean {

    val palabra = Validacion(pal)

    if(palabra.getResultadoValidacion()) {
        navController.navigate(route = AppScreens.Animacion.route + "/" + pal)
        return false
    } else {
        MensajeError(navController)
    }
    return true
}

@Composable
fun MensajeError(navController: NavController) {

    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = "Palabra No Válida",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Montserrat)
                )
            },
            text = {
                Text(
                    text = "La palabra intoducida no es válida, intente ingresar una nueva palabra",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Montserrat)
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {

                    TextButton(
                        onClick = {
                            openDialog.value = false
                            navController.navigate(route = AppScreens.IngresaPalabra.route)
                        }
                    ) {
                        Text(
                            text = "DE ACUERDO",
                            style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Montserrat)
                        )
                    }
                }
            },
            shape = RoundedCornerShape(
                topEndPercent = 50,
                bottomStartPercent = 50
            ),
            backgroundColor = Color(0xFF013D83),
            contentColor = Color.White
        )
    }

}