package com.trabajoterminal.lsmapp.presentation.view

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.trabajoterminal.lsmapp.data.Categoria
import com.trabajoterminal.lsmapp.data.DataProviderSubcategorias
import com.trabajoterminal.lsmapp.presentation.view.navigation.AppScreens
import com.trabajoterminal.lsmapp.ui.theme.Montserrat

@Composable
fun Subcategorias(navController: NavController, categoriaSel: String?) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                backgroundColor = MaterialTheme.colors.background
            ) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.White
                )
            }
        }
    ) {
        BodyContentSubcategorias(navController, categoriaSel)
    }
}

@Composable
fun BodyContentSubcategorias(navController: NavController, categoriaSel: String?) {

    val subcategoria = seleccionaCategoria(categoriaSel, navController)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(20.dp))
        if (categoriaSel != null) {
            Text(
                text = categoriaSel,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Montserrat
                )
            )
        }
        Spacer(modifier = Modifier.padding(20.dp))
        LazyColumn() {
            items(subcategoria) {
                SubcategoriasItem(navController, categoriaSel, it)
            }
        }
    }
}

@Composable
fun seleccionaCategoria(categoriaSel: String?, navController: NavController): List<Categoria> {

    val categorias: List<Categoria>

    when(categoriaSel) {
        "Alfabeto" -> {
            categorias = remember { DataProviderSubcategorias.categoriaAlfabeto }
            return categorias
        }
        "Números" -> {
            categorias = remember { DataProviderSubcategorias.categoriaNumeros }
            return categorias
        }
        "Colores" -> {
            categorias = remember { DataProviderSubcategorias.categoriaColores }
            return categorias
        }
        "Palabras de Auxilio" -> {
            categorias = remember { DataProviderSubcategorias.categoriaPalabrasAuxilio }
            return categorias
        }
        else -> {
            Text(text = "Error, categoria mal seleccionada.")
            categorias = remember { DataProviderSubcategorias.categoriaVacia }
            return categorias
        }
    }
}

@Composable
fun SubcategoriasItem(navController: NavController, categoriaSel: String?, subcategoria: Categoria) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate(route = AppScreens.Animacion.route + "/" + subcategoria.nameCat)
            },
            contentPadding = PaddingValues(
                top = 15.dp,
                bottom = 15.dp
            ),
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(Color(subcategoria.colorCat))
        ) {
            Text(
                text = subcategoria.nameCat,
                style = MaterialTheme.typography.button,
                color = if (categoriaSel == "Colores") Color.Black else Color.White
            )
        }
    }
}