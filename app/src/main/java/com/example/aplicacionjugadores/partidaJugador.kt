package com.example.aplicacionjugadores

import java.util.Date
import org.json.JSONArray
import org.json.JSONObject

data class partidaJugador(
    val nombre : String,
    val equipo : String,
    val juego : String,
    val personaje : String,
    val contra : String,
    val fecha : Date,
    val score : String
)
fun searchByName(jugadorABuscar : String, nombre : String): Boolean {
    return jugadorABuscar == nombre
}

fun List<partidaJugador>.toJson(): String {
    val jsonArray = JSONArray()
    for (jugador in this) {
        val jsonObject = JSONObject()
        jsonObject.put("nombre", jugador.nombre)
        jsonObject.put("equipo", jugador.equipo)
        jsonObject.put("juego", jugador.juego)
        jsonObject.put("personaje", jugador.personaje)
        jsonObject.put("contra", jugador.contra)
        jsonObject.put("fecha", jugador.fecha)
        jsonObject.put("score", jugador.score)
        jsonArray.put(jsonObject)
    }
    return jsonArray.toString()
}