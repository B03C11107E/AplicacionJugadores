package com.example.aplicacionjugadores.ui.rutas

sealed class rutas(val ruta: String) {
    object home: rutas("jugadores")
    object detalles: rutas("detallesJugador")
    object crear: rutas("crearJugador")
}