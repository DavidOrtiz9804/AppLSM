package com.trabajoterminal.lsmapp.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.trabajoterminal.lsmapp.R
import com.trabajoterminal.lsmapp.presentation.view.navigation.AppScreens
import com.trabajoterminal.lsmapp.ui.theme.Lobster

@Composable
fun MenuPrincipal(navController: NavController) {
    Scaffold {
        BodyContentMenuPrincipal(navController)
    }
}

@Composable
fun BodyContentMenuPrincipal(navController: NavController) {
    MaterialTheme {
        //Columna que contiene el logo y los botones
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image( // Logo
                modifier = Modifier.size(220.dp),
                painter = painterResource(R.drawable.applsm_logo),
                contentDescription = "Logo AppLSM")
            Spacer(modifier = Modifier.padding(5.dp))
            Text( //Texto "Bienvenida"
                text = "Bienvenido",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Lobster
                )
            )
            Spacer(modifier = Modifier.padding(40.dp))
            Button( // Botón que redirecciona a pantalla CONECTAR GUANTE
                onClick = {
                    navController.navigate(route = AppScreens.EstadoConexion.route) // Navegación a pantalla CONECTAR GUANTE
                },
                contentPadding = PaddingValues(
                    start = 72.dp,
                    top = 20.dp,
                    end = 72.dp,
                    bottom = 20.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(colors.primary)
            ) {
                Text(
                    text = "Conectar Guante",
                    style = MaterialTheme.typography.button
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button( // Botón que redirecciona a pantalla COMENZAR A PRACTICAR
                onClick = {
                    navController.navigate(route = AppScreens.Categorias.route)
                }, //Navegación a pantalla COMENZAR A PRACTICAR
                contentPadding = PaddingValues(
                    start = 50.dp,
                    top = 20.dp,
                    end = 50.dp,
                    bottom = 20.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(colors.primaryVariant)
            ) {
                Text(
                    text = "Comenzar a Practicar",
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}
