
import android.util.Log
import android.util.Xml
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.aplicacionjugadores.R
import com.example.aplicacionjugadores.partidaJugador
import com.example.aplicacionjugadores.personaje
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlSerializer
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun leerXMLjugadores(): ArrayList<partidaJugador> {
    val context = LocalContext.current
    val listaJugadores = ArrayList<partidaJugador>()

    try {
        // Copiar el archivo desde res/raw al almacenamiento interno
        val outputFile = File(context.filesDir, "partidasJugadores.xml")
        if (!outputFile.exists()) {
            val inputStream = context.resources.openRawResource(R.raw.partidas_jugadores)
            copyFile(inputStream, FileOutputStream(outputFile))
        }

        // Leer y parsear el archivo desde el almacenamiento interno
        val fileInputStream = FileInputStream(outputFile)
        listaJugadores.addAll(parseXMLjugadores(fileInputStream))
    } catch (e: Exception) {
        Log.e("Error", "Error al leer o parsear el archivo XML", e)
    }

    return listaJugadores
}
@Composable
fun guardarXMLjugadores(listaJugadores: List<partidaJugador>) {
    val context = LocalContext.current
    try {
        val outputFile = File(context.filesDir, "partidasJugadores.xml")
        val outputStream = FileOutputStream(outputFile)
        val serializer: XmlSerializer = android.util.Xml.newSerializer()
        serializer.setOutput(outputStream, "UTF-8")
        serializer.startDocument("UTF-8", true)
        serializer.startTag(null, "partidas_jugadores")

        for (jugador in listaJugadores) {
            serializer.startTag(null, "partida_jugador")
            serializer.attribute(null, "nombre", jugador.nombre)
            serializer.attribute(null, "equipo", jugador.equipo)
            serializer.attribute(null, "juego", jugador.juego)
            serializer.attribute(null, "personaje", jugador.personaje)
            serializer.attribute(null, "contra", jugador.contra)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fechaString = dateFormat.format(jugador.fecha)
            serializer.attribute(null, "fecha", fechaString)

            serializer.attribute(null, "score", jugador.score)
            serializer.endTag(null, "partida_jugador")
        }

        serializer.endTag(null, "partidas_jugadores")
        serializer.endDocument()
        outputStream.close()
    } catch (e: Exception) {
        Log.e("Error", "Error al escribir en el archivo XML", e)
    }
}

private fun parseXMLjugadores(inputStream: InputStream): ArrayList<partidaJugador> {
    val parser: XmlPullParser = android.util.Xml.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
    parser.setInput(inputStream, null)

    val partidas = ArrayList<partidaJugador>()

    try {
        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (parser.name == "partida_jugador") {
                        val nombre = parser.getAttributeValue(null, "nombre")
                        val equipo = parser.getAttributeValue(null, "equipo")
                        val juego = parser.getAttributeValue(null, "juego")
                        val personaje = parser.getAttributeValue(null, "personaje")
                        val contra = parser.getAttributeValue(null, "contra")
                        val fechaString = parser.getAttributeValue(null, "fecha")
                        val score = parser.getAttributeValue(null, "score")
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val fecha = dateFormat.parse(fechaString)
                        partidas.add(partidaJugador(nombre, equipo, juego, personaje, contra, fecha, score))
                    }
                }
            }
            eventType = parser.next()
        }
    } catch (e: Exception) {
        Log.e("Error", "Error al procesar un elemento", e)
    }

    return partidas
}

private fun copyFile(inputStream: InputStream, outputStream: OutputStream) {
    try {
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        inputStream.close()
        outputStream.close()
    } catch (e: IOException) {
        Log.e("Error", "Error al copiar el archivo", e)
    }
}
@Composable
fun leerDatosXMLJugadores(fileName : String): List<String> {
    val context = LocalContext.current
    val inputStream = context.assets.open(fileName)
    return try {
        parsearXMLJugadores(inputStream)
    } finally {
        inputStream.close()
    }
}

fun parsearXMLJugadores(inputStream: InputStream): List<String> {
    val parser: XmlPullParser = Xml.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
    parser.setInput(inputStream, null)

    val jugadores = mutableListOf<String>()

    var eventType = parser.eventType
    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                if (parser.name == "jugador") {
                    val nombre = parser.getAttributeValue(null, "nombre")
                    // Agregar lógica para leer otros atributos según sea necesario
                    jugadores.add(nombre)
                }
            }
        }
        eventType = parser.next()
    }

    return jugadores
}
@Composable
fun leerDatosXMLJuegos(fileName : String): List<String> {
    val context = LocalContext.current
    val inputStream = context.assets.open(fileName)
    return try {
        parsearXMLJuegos(inputStream)
    } finally {
        inputStream.close()
    }
}

fun parsearXMLJuegos(inputStream: InputStream): List<String> {
    val parser: XmlPullParser = Xml.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
    parser.setInput(inputStream, null)

    val jugadores = mutableListOf<String>()

    var eventType = parser.eventType
    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                if (parser.name == "juego") {
                    val nombre = parser.getAttributeValue(null, "nombre")
                    // Agregar lógica para leer otros atributos según sea necesario
                    jugadores.add(nombre)
                }
            }
        }
        eventType = parser.next()
    }

    return jugadores
}
@Composable
fun leerDatosXMLPersonajes(fileName : String): List<personaje> {
    val context = LocalContext.current
    val inputStream = context.assets.open(fileName)
    return try {
        parsearXMLPersonajes(inputStream)
    } finally {
        inputStream.close()
    }
}

fun parsearXMLPersonajes(inputStream: InputStream): List<personaje> {
    val parser: XmlPullParser = Xml.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
    parser.setInput(inputStream, null)

    val personajes = mutableListOf<personaje>()

    var eventType = parser.eventType
    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                if (parser.name == "personaje") {
                    val juego = parser.getAttributeValue(null, "juego")
                    val nombre = parser.getAttributeValue(null, "nombre")
                    // Agregar lógica para leer otros atributos según sea necesario
                    personajes.add(personaje(juego, nombre))
                }
            }
        }
        eventType = parser.next()
    }

    return personajes
}
@Composable
fun leerDatosXMLEquipos(fileName : String): List<String> {
    val context = LocalContext.current
    val inputStream = context.assets.open(fileName)
    return try {
        parsearXMLEquipos(inputStream)
    } finally {
        inputStream.close()
    }
}

fun parsearXMLEquipos(inputStream: InputStream): List<String> {
    val parser: XmlPullParser = Xml.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
    parser.setInput(inputStream, null)

    val jugadores = mutableListOf<String>()

    var eventType = parser.eventType
    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                if (parser.name == "equipo") {
                    val nombre = parser.getAttributeValue(null, "nombre")
                    // Agregar lógica para leer otros atributos según sea necesario
                    jugadores.add(nombre)
                }
            }
        }
        eventType = parser.next()
    }

    return jugadores
}