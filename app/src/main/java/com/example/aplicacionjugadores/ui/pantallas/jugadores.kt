package com.example.aplicacionjugadores.ui.pantallas

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aplicacionjugadores.R
import com.example.aplicacionjugadores.partidaJugador
import com.example.aplicacionjugadores.toJson
import com.example.aplicacionjugadores.ui.rutas.rutas
import guardarXMLjugadores
import leerXMLjugadores
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PantallaHome(navController: NavHostController?) {
    var listaJugadores: ArrayList<partidaJugador> = leerXMLjugadores()
    var deleteMode by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var listaMutable = remember { mutableStateListOf<partidaJugador>().apply { addAll(listaJugadores) } }
    guardarXMLjugadores(listaMutable)
    var selectedOptions by remember { mutableStateOf(listOf<partidaJugador>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Bienvenido", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            items(listaJugadores) { jugador ->
                if (searchText == "") {
                    Carta( jugador,navController, funcion = @Composable {
                        if (deleteMode) {
                            Checkbox(
                                checked = selectedOptions.contains(jugador),
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(start = 4.dp, top = 8.dp),
                                onCheckedChange = { selected ->
                                    val currentSelected = ArrayList(selectedOptions)
                                    if (selected) {
                                        currentSelected.add(jugador)
                                    } else {
                                        currentSelected.remove(jugador)
                                    }
                                    selectedOptions = currentSelected
                                }
                            )
                        }
                        if (!deleteMode) {
                            if (selectedOptions.isNotEmpty()) {
                                listaMutable.removeAll(selectedOptions.toSet())
                                listaJugadores.removeAll(selectedOptions.toSet())
                                guardarXMLjugadores(listaMutable)
                                selectedOptions = emptyList()
                            }
                        }
                    })
                } else {
                    if (jugador.equipo.toLowerCase().contains(searchText.toLowerCase())) {
                        Carta(
                            jugador, navController, funcion = @Composable {
                                if (deleteMode) {
                                    Checkbox(
                                        checked = selectedOptions.contains(jugador),
                                        modifier = Modifier
                                            .weight(1F)
                                            .padding(start = 4.dp, top = 8.dp),
                                        onCheckedChange = { selected ->
                                            val currentSelected = ArrayList(selectedOptions)
                                            if (selected) {
                                                currentSelected.add(jugador)
                                            } else {
                                                currentSelected.remove(jugador)
                                            }
                                            selectedOptions = currentSelected
                                        }
                                    )
                                }
                                if (!deleteMode) {
                                    if (selectedOptions.isNotEmpty()) {
                                        listaMutable.removeAll(selectedOptions.toSet())
                                        listaJugadores.removeAll(selectedOptions.toSet())
                                        guardarXMLjugadores(listaJugadores)
                                        selectedOptions = emptyList()
                                    }
                                }
                            })
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ExtendedFloatingActionButton(
                onClick = {navController?.navigate(rutas.crear.ruta) },
                icon = { Icon(Icons.Filled.AddCircle, "Extended floating action button.") },
                text = { Text(text = "Add") },
                modifier = Modifier.padding(16.dp)
            )
            ExtendedFloatingActionButton(
                onClick = { deleteMode = !deleteMode },
                icon = { Icon(Icons.Filled.Delete, "Extended floating action button.") },
                text = { Text(text = "Delete") },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
@Composable
fun Carta(jugador: partidaJugador,navController: NavHostController?, funcion: (@Composable () -> Unit)) {

    Card(modifier = Modifier.fillMaxWidth()
        .clickable {
            navController?.navigate("${rutas.detalles.ruta}/${jugador.nombre}")
        }
        , border = BorderStroke(2.dp, Color.Black)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fechaFormateada = formatoFecha.format(jugador.fecha)
            Column(modifier = Modifier.weight(5F)) {
                Text(
                    text = jugador.nombre + " | "+jugador.equipo+ " VS " + jugador.contra, textAlign = TextAlign.Center, modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = jugador.juego + " "+ jugador.personaje, textAlign = TextAlign.Center, modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = fechaFormateada  + " "+ jugador.score, textAlign = TextAlign.Center, modifier = Modifier
                        .fillMaxWidth()
                )
            }
            funcion()
        }
    }
}