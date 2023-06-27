package com.trabajoterminal.lsmapp.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.trabajoterminal.lsmapp.R
import com.trabajoterminal.lsmapp.presentation.view.navigation.AppScreens
import com.trabajoterminal.lsmapp.presentation.viewmodel.PosManoViewModel
import com.trabajoterminal.lsmapp.ui.theme.Montserrat

@Composable
fun EstadoConexion(
    navController: NavController,
    viewModel: PosManoViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AppScreens.MenuPrincipal.route) },
                backgroundColor = colors.background
            ) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.White
                )
            }
        }
    ) {
        BodyContentEstadoConexion(navController, viewModel)
    }
}

@Composable
fun BodyContentEstadoConexion(
    navController: NavController,
    viewModel: PosManoViewModel = hiltViewModel()
) {

    var estadoDeConexion by remember { mutableStateOf(viewModel.estadoConexionBluetooth()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Estado:",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat
            )
        )
        Spacer(modifier = Modifier.padding(20.dp))
        if(!estadoDeConexion) {
            Image( // Logo
                modifier = Modifier.size(300.dp),
                painter = painterResource(R.drawable.bt_desconectado),
                contentDescription = "BT_Desconectado")
        } else {
            Image( // Logo
                modifier = Modifier.size(300.dp),
                painter = painterResource(R.drawable.bt_conectado),
                contentDescription = "BT_Conectado")
        }
        Spacer(modifier = Modifier.padding(30.dp))
        Button(
            onClick = { navController.navigate(route = AppScreens.ConectarDispositivo.route) },
            contentPadding = PaddingValues(
                start = 70.dp,
                top = 20.dp,
                end =70.dp,
                bottom = 20.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(colors.primary)
        ) {
            Text(
                text = "CONECTAR",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            onClick = {
                viewModel.desconectarBluetooth()
                estadoDeConexion = false
            },
            contentPadding = PaddingValues(
                start = 50.dp,
                top = 20.dp,
                end = 50.dp,
                bottom = 20.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(colors.secondary)
        ) {
            Text(
                text = "DESCONECTAR",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }
}