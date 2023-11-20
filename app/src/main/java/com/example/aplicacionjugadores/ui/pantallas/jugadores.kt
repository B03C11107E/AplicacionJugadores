package com.example.aplicacionjugadores.ui.pantallas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aplicacionjugadores.R
import com.example.aplicacionjugadores.jugador
import com.example.aplicacionjugadores.ui.rutas.rutas

val listaJugadores = listOf(
    jugador("jugador 1", "equipo1"),
    jugador("jugador2", "equipo2")
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaHome(navController : NavHostController?) {

    var deleteMode by remember { mutableStateOf(false)}
    var searchText by remember { mutableStateOf("") }
    Column {
        Text("Bienvenido", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        TextField(value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth())
        if(searchText == ""){
            for (jugador in listaJugadores) {
                Carta(R.drawable.ic_launcher_background, jugador, deleteMode)
            }
        }
        else{
            for (jugador in listaJugadores) {
                if(jugador.equipo == searchText) {
                    Carta(R.drawable.ic_launcher_background, jugador, deleteMode)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            ExtendedFloatingActionButton(
                onClick = {  navController?.navigate(rutas.crear.ruta) },
                icon = { Icon(Icons.Filled.AddCircle, "Extended floating action button.") },
                text = { Text(text = "Add") },
                modifier = Modifier.padding(16.dp)
            )
            ExtendedFloatingActionButton(
                onClick = {  deleteMode = !deleteMode },
                icon = { Icon(Icons.Filled.Delete, "Extended floating action button.") },
                text = { Text(text = "Delete") },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
@Composable
fun Carta(imagen: Int, jugador: jugador, deleteMode: Boolean){
    var selectedOptions by remember { mutableStateOf(listOf<String>())  }
    Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(2.dp, Color.Black)){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Image(painter = painterResource(imagen), contentDescription = "",
                contentScale = ContentScale.Crop, modifier = Modifier
                    .size(64.dp)
                    .clip(
                        CircleShape
                    ))
            Column (modifier = Modifier.weight(5F)){
                Text(text=jugador.nombre, textAlign = TextAlign.Center, modifier = Modifier
                    .fillMaxWidth())
                Text(text=jugador.equipo, textAlign = TextAlign.Center, modifier = Modifier
                    .fillMaxWidth())
            }
            if(deleteMode){
                Checkbox(
                    checked = selectedOptions.contains(jugador.nombre),
                    modifier =  Modifier.weight(1F).padding(start = 4.dp, top = 8.dp),
                    onCheckedChange = { selected ->
                        val currentSelected = ArrayList(selectedOptions)
                        if (selected) {
                            currentSelected.add(jugador.nombre)
                        } else {
                            currentSelected.remove(jugador.nombre)
                        }
                        selectedOptions = currentSelected
                    }
                )
            }
            if(!deleteMode){

            }
        }
    }
}