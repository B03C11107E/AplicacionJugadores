package com.example.aplicacionjugadores.ui.pantallas

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aplicacionjugadores.partidaJugador
import com.example.aplicacionjugadores.personaje
import com.example.aplicacionjugadores.ui.rutas.rutas
import guardarXMLjugadores
import leerDatosXMLEquipos
import leerDatosXMLJuegos
import leerDatosXMLJugadores
import leerDatosXMLPersonajes
import leerXMLjugadores
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun PantallaCrear(navController: NavHostController?) {
    var listaJugadores: ArrayList<partidaJugador> = leerXMLjugadores()
    var listaNueva = remember { mutableStateListOf<partidaJugador>().apply { addAll(listaJugadores) } }
    var listaNombres = leerDatosXMLJugadores("jugadores.xml")
    var juegos = leerDatosXMLJuegos("juegos.xml")
    var equipos = leerDatosXMLEquipos("equipos.xml")
    var personajes = leerDatosXMLPersonajes("personajes.xml")
    var personajesMutable = remember { mutableStateListOf<personaje>().apply { addAll(personajes) } }
    var nombreSeleccionado by remember { mutableStateOf("") }
    var equipoSeleccionado by remember { mutableStateOf("") }
    var juegoSeleccionado by remember { mutableStateOf("") }
    var personajeSeleccionado by remember { mutableStateOf("") }
    var contraSeleccionado by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }
    var fechaSeleccionada by remember { mutableStateOf<Date?>(null) }
    var expandedJugador by remember { mutableStateOf(false) }
    var expandedEquipos by remember { mutableStateOf(false) }
    var expandedPersonajes by remember { mutableStateOf(false) }
    var expandedJuegos by remember { mutableStateOf(false) }
    var expandedContra by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Jugador:")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedJugador = true }
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Text(text = if (nombreSeleccionado.isNotEmpty()) nombreSeleccionado else "Seleccionar nombre")
            DropdownMenu(
                expanded = expandedJugador,
                onDismissRequest = { expandedJugador = false },
            ) {
                listaNombres.forEach { nombre ->
                    DropdownMenuItem(
                        onClick = {
                            nombreSeleccionado = nombre
                            expandedJugador = false
                        },
                        text = {
                            Text(text = nombre)
                        }
                    )
                }
            }
        }
        Text(text = "Equipo:")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedEquipos = true }
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Text(text = if (equipoSeleccionado.isNotEmpty()) equipoSeleccionado else "Seleccionar equipo")
            DropdownMenu(
                expanded = expandedEquipos,
                onDismissRequest = { expandedEquipos = false },
            ) {
                equipos.forEach { equipo ->
                    DropdownMenuItem(
                        onClick = {
                            equipoSeleccionado = equipo
                            expandedEquipos = false
                        },
                        text = {
                            Text(text = equipo)
                        }
                    )
                }
            }
        }
        Text(text = "Juego:")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedJuegos = true }
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Text(text = if (juegoSeleccionado.isNotEmpty()) juegoSeleccionado else "Seleccionar juego")
            DropdownMenu(
                expanded = expandedJuegos,
                onDismissRequest = { expandedJuegos = false },
            ) {
                juegos.forEach { juego ->
                    DropdownMenuItem(
                        onClick = {
                            juegoSeleccionado = juego
                            expandedJuegos = false
                        },
                        text = {
                            Text(text = juego)
                        }
                    )
                }
            }
        }

        Text(text = "Personaje:")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedPersonajes = true }
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            if(juegoSeleccionado != ""){
                Text(text = if (personajeSeleccionado.isNotEmpty()) personajeSeleccionado else "Seleccionar personaje")
                DropdownMenu(
                    expanded = expandedPersonajes,
                    onDismissRequest = { expandedPersonajes = false },
                ) {
                    personajesMutable.forEach { personaje ->
                        if(personaje.juego == juegoSeleccionado){
                            DropdownMenuItem(
                                onClick = {
                                    personajeSeleccionado = personaje.nombre
                                    expandedPersonajes = false
                                },
                                text = {
                                    Text(text = personaje.nombre)
                                }
                            )
                        }

                    }
                }
            }
            else{
                Text(text = "Seleccione primero un juego")
            }

        }
        Text(text = "Contra:")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedContra = true }
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Text(text = if (contraSeleccionado.isNotEmpty()) contraSeleccionado else "Seleccionar contrario")
            DropdownMenu(
                expanded = expandedContra,
                onDismissRequest = { expandedContra = false },
            ) {
                listaNombres.forEach { nombre ->
                    if(nombre != nombreSeleccionado) {
                        DropdownMenuItem(
                            onClick = {
                                contraSeleccionado = nombre
                                expandedContra = false
                            },
                            text = {
                                Text(text = nombre)
                            }
                        )
                    }
                }
            }
        }
        Text(text = "Score:")
        TextField(
            value = score,
            onValueChange = { score = it },
            label = { Text("Score") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Text(text = "Fecha:")
        FechaPicker(onDateSelected = {
            var fechaString = it

            var dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            fechaSeleccionada = dateFormat.parse(fechaString)
            })

        Button(
            onClick = {

                fechaSeleccionada?.let { fecha ->
                    val nuevaPartida = partidaJugador(
                        nombre = nombreSeleccionado,
                        equipo = equipoSeleccionado,
                        juego = juegoSeleccionado,
                        personaje = personajeSeleccionado,
                        contra = contraSeleccionado,
                        score = score,
                        fecha = fecha
                    )
                    listaNueva.add(nuevaPartida)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Crear Partida")
        }
        if(listaNueva.size > listaJugadores.size){
            guardarXMLjugadores(listaJugadores = listaNueva)
            navController?.navigate(rutas.home.ruta)
        }
    }
    }
@Composable
fun FechaPicker(onDateSelected: (String) -> Unit) {
    val mContext = LocalContext.current
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val mCalendar = remember { Calendar.getInstance() }
    val mDate = remember { mutableStateOf("") }

    // DatePickerDialog
    val mDatePickerDialog = remember {
        DatePickerDialog(
            mContext,
            { _, mYear, mMonth, mDayOfMonth ->
                mCalendar.set(Calendar.YEAR, mYear)
                mCalendar.set(Calendar.MONTH, mMonth)
                mCalendar.set(Calendar.DAY_OF_MONTH, mDayOfMonth)
                mDate.value = dateFormat.format(mCalendar.time)
                onDateSelected(mDate.value)
            },
            mCalendar.get(Calendar.YEAR),
            mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // Displaying the button
    Button(
        onClick = {
            mDatePickerDialog.show()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Seleccionar Fecha")
    }
    Text(text = "${mDate.value}", textAlign = TextAlign.Center)
}