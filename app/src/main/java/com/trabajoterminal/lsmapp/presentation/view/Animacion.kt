package com.trabajoterminal.lsmapp.presentation.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.trabajoterminal.lsmapp.data.animaciones.Animaciones
import com.trabajoterminal.lsmapp.presentation.view.navigation.AppScreens
import com.trabajoterminal.lsmapp.presentation.viewmodel.PosManoViewModel
import kotlinx.coroutines.delay

@Composable
fun Animacion(
    navController: NavController,
    palabra: String?,
    viewModel: PosManoViewModel = hiltViewModel()
) {
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
        BodyContentAnimacion(navController, palabra, viewModel)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BodyContentAnimacion(
    navController: NavController,
    palabra: String?,
    viewModel: PosManoViewModel = hiltViewModel()
) {

    val animacion = palabra?.let { Animaciones(it).getAnimacion() }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            if (animacion != null) {
                Animaciones(
                    itemsCount = animacion.size,
                    itemContent = { index ->
                        Image(painterResource(animacion[index].imageLetra), contentDescription = null)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Button(
            onClick = {
                if(viewModel.estadoConexionBluetooth()) {
                    navController.navigate(route = AppScreens.PracticarSena.route + "/" + palabra)
                } else {
                    Toast.makeText(context, "GuanteAppLSM no está conectado", Toast.LENGTH_LONG).show()
                }
            },
            contentPadding = PaddingValues(
                start = 50.dp,
                top = 20.dp,
                end = 50.dp,
                bottom = 20.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
        ) {
            Text(
                text = "PRACTICAR SEÑA",
                style = MaterialTheme.typography.button
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Animaciones(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 2000,
    pagerState: PagerState = remember { PagerState() },
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    LaunchedEffect(pagerState.currentPage) {
        delay(autoSlideDuration)
        pagerState.animateScrollToPage((pagerState.currentPage + 1) % itemsCount)
    }

    Box(
        modifier = modifier.fillMaxWidth().padding(50.dp),
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }
        // you can remove the surface in case you don't want
        // the transparant bacground
        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            //color = Color.BLACK.copy(alpha = 0.5f)
        ) {

        }
    }
}