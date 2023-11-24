package com.example.aplicacionjugadores

data class jugador(
    val nombre : String,
    val equipo : String
)
fun searchByName(jugadorABuscar : String, nombre : String): Boolean {
    return jugadorABuscar == nombre
}

