package com.trabajoterminal.lsmapp.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.trabajoterminal.lsmapp.data.Categoria
import com.trabajoterminal.lsmapp.data.DataProviderCategorias
import com.trabajoterminal.lsmapp.presentation.view.navigation.AppScreens
import com.trabajoterminal.lsmapp.ui.theme.Montserrat

@Composable
fun Categorias(navController: NavController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AppScreens.MenuPrincipal.route) },
                backgroundColor = MaterialTheme.colors.background
            ) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.White
                )
            }
        }
    ) {
        BodyContentCategorias(navController)
    }
}

@Composable
fun BodyContentCategorias(navController: NavController) {

    val categorias = remember { DataProviderCategorias.categoriasList }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿Que deseas aprender?",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat
            )
        )
        Spacer(modifier = Modifier.padding(20.dp))
        LazyColumn() {
            items(categorias) {
                CategoriasItem(navController, it)
            }
        }
    }
}

@Composable
fun CategoriasItem(navController: NavController, categoria: Categoria) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if(categoria.nameCat == "Nueva Palabra") {
                    navController.navigate(route = AppScreens.IngresaPalabra.route)
                } else {
                    navController.navigate(route = AppScreens.Subcategorias.route + "/" + categoria.nameCat)
                }
            },
            contentPadding = PaddingValues(
                top = 10.dp,
                bottom = 10.dp),
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 5.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(Color(categoria.colorCat))
        ) {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(categoria.imageCat),
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = categoria.nameCat,
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }
}