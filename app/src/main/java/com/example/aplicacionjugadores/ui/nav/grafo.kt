package com.example.aplicacionjugadores.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionjugadores.partidaJugador
import com.example.aplicacionjugadores.ui.rutas.rutas
import com.example.aplicacionjugadores.ui.pantallas.PantallaHome
import com.example.aplicacionjugadores.ui.pantallas.PantallaCrear
import com.example.aplicacionjugadores.ui.pantallas.PantallaDetalles

@Composable
fun GrafoNavegacion(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = rutas.home.ruta) {

        composable(rutas.home.ruta){
            PantallaHome(navController = navController)
        }
        composable(rutas.crear.ruta){backStackEntry ->
            PantallaCrear(navController = navController)

        }
        composable(
            route = "${rutas.detalles.ruta}/{nombre}",
        ) { navBackStackEntry ->
            val nombreJugador = navBackStackEntry.arguments?.getString("nombre")
            PantallaDetalles(nombreJugador)
        }
    }
}