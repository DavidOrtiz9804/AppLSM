package com.trabajoterminal.lsmapp.data.animaciones

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.delay
import java.util.*

class Animaciones (palabra: String) {

    val listaLetrasAnimacion = crearListaLetras(palabra)

    fun getAnimacion(): List<Letra> {
        return listaLetrasAnimacion
    }


}

fun crearListaLetras(palabra: String): List<Letra> {

    val abecedario = DataProviderAnimacion.letras
    val listaLetras: MutableList<Letra> = mutableListOf()
    var nLetra: Letra
    val palabraAnimar = palabra.uppercase(Locale.ROOT)

    for ( l in palabraAnimar ) {
        for (l2 in abecedario) {
            if (l2.nameLetra == l.toString()) {
                nLetra = Letra(l2.nameLetra, l2.imageLetra)
                listaLetras.add(nLetra)
            }
        }
    }

    return listaLetras

}