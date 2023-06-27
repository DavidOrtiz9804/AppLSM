package com.trabajoterminal.lsmapp.data

import com.trabajoterminal.lsmapp.R

object DataProviderCategorias {

    val categoriasList = listOf(
        Categoria(
            nameCat = "Alfabeto",
            colorCat = 0xFF989EAA,
            imageCat = R.drawable.abecedario
        ),
        Categoria(
            nameCat = "Números",
            colorCat = 0xFF996DCF,
            imageCat = R.drawable.numeros
        ),
        Categoria(
            nameCat = "Colores",
            colorCat = 0xFF353F4B,
            imageCat = R.drawable.colores
        ),
        Categoria(
            nameCat = "Palabras de Auxilio",
            colorCat = 0xFF01A6FF,
            imageCat = R.drawable.alerta
        ),
        Categoria(
            nameCat = "Nueva Palabra",
            colorCat = 0xFFFF51D7,
            imageCat = R.drawable.nuevapalabra
        )
    )

}