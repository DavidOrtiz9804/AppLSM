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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.trabajoterminal.lsmapp.data.DispositivoBt
import com.trabajoterminal.lsmapp.presentation.permissions.PermissionUtils
import com.trabajoterminal.lsmapp.presentation.view.navigation.AppScreens
import com.trabajoterminal.lsmapp.presentation.viewmodel.PosManoViewModel
import com.trabajoterminal.lsmapp.ui.theme.Montserrat

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ConectarDispositivo(
    navController: NavController,
    viewModel: PosManoViewModel = hiltViewModel()
) {

    val permissionState = rememberMultiplePermissionsState(permissions = PermissionUtils.permissions)
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver{_,event ->
                if(event == Lifecycle.Event.ON_START){
                    permissionState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

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
        BodyContentConectarDispositivo(navController,viewModel)
    }
}

@Composable
fun BodyContentConectarDispositivo(
    navController: NavController,
    viewModel: PosManoViewModel = hiltViewModel()
) {

    val listaDispositivos = viewModel.buscarDispositivos()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selecciona el dispositivo:",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat
            )
        )
        Spacer(modifier = Modifier.padding(30.dp))
        LazyColumn() {
            items(listaDispositivos) {
                DispositivosItem(navController,it)
            }
        }
        Spacer(modifier = Modifier.padding(30.dp))
    }
}

@Composable
fun DispositivosItem(
    navController: NavController,
    dispositivo: DispositivoBt,
    viewModel: PosManoViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                viewModel.conectar(dispositivo.direccion)
                navController.navigate(route = AppScreens.EstadoConexion.route)
            },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dispositivo.nombre,
                    style = MaterialTheme.typography.button,
                    color = Color.White,
                )
                Text(
                    text = dispositivo.direccion,
                    style = MaterialTheme.typography.button,
                    color = Color.White
                )
            }
        }
    }
}

