package com.evabeziner.mibookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.evabeziner.mibookshelf.ui.theme.MiBookshelfTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Primero los datos, despues la pantalla: si la interfaz se dibujara antes
        // de que exista la base, no tendria nada que mostrar.
        val database = AppDatabase.getDatabase(this)
        val dao = database.libroDao()
        val viewModel = LibroViewModel(dao)

        // Las preferencias las creo aca porque necesitan el contexto de la Activity.
        val preferencias = Preferencias(this)

        setContent {
            MiBookshelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaMisLibros(
                        viewModel = viewModel,
                        preferencias = preferencias,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Esta es la pantalla 1 del wireframe: mis libros. Por ahora el formulario de alta
// esta en la misma pantalla; cuando arme la navegacion va a pasar a la suya propia,
// que es como la tengo dibujada.
@Composable
fun PantallaMisLibros(
    viewModel: LibroViewModel,
    preferencias: Preferencias,
    modifier: Modifier = Modifier
) {
    // remember guarda estos textos mientras la pantalla existe, y mutableStateOf
    // hace que la interfaz se redibuje sola cada vez que escribo una letra.
    var tituloText by remember { mutableStateOf("") }
    var autorText by remember { mutableStateOf("") }
    var anioText by remember { mutableStateOf("") }
    var puntajeText by remember { mutableStateOf("") }

    // El orden arranca con el que quedo guardado la ultima vez que abri la app.
    // Esta linea es la que hace que la preferencia sirva de algo.
    var orden by remember { mutableStateOf(preferencias.obtenerOrden()) }

    // collectAsState escucha el Flow del ViewModel: cuando la tabla cambia, la lista
    // de la pantalla se actualiza sola, sin que yo tenga que recargar nada.
    val listaLibros by viewModel.obtenerLibros(orden).collectAsState(initial = emptyList())

    Column(modifier = modifier.padding(16.dp)) {

        Text(text = "Agregar un libro", style = MaterialTheme.typography.titleMedium)

        // El padding de abajo separa un campo del otro. Sin eso, la etiqueta flotante
        // del campo enfocado se monta sobre el borde del campo de arriba.
        val campoModifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)

        OutlinedTextField(
            value = tituloText,
            onValueChange = { tituloText = it },
            label = { Text("Titulo") },
            modifier = campoModifier
        )
        OutlinedTextField(
            value = autorText,
            onValueChange = { autorText = it },
            label = { Text("Autor") },
            modifier = campoModifier
        )
        OutlinedTextField(
            value = anioText,
            // Solo dejo escribir digitos. Si la tecla no es un numero, no la guardo,
            // asi el campo no acepta letras en primer lugar.
            onValueChange = { nuevoTexto ->
                if (nuevoTexto.all { it.isDigit() }) anioText = nuevoTexto
            },
            label = { Text("Año") },
            // Abre el teclado numerico en vez del de letras.
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = campoModifier
        )
        OutlinedTextField(
            value = puntajeText,
            onValueChange = { nuevoTexto ->
                if (nuevoTexto.all { it.isDigit() }) puntajeText = nuevoTexto
            },
            label = { Text("Puntaje (0 a 5)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = campoModifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                // No dejo guardar un libro sin titulo. Es el unico campo que marque
                // como obligatorio cuando diseñe la tabla.
                if (tituloText.isNotEmpty()) {
                    val nuevoLibro = Libro(
                        titulo = tituloText,
                        autor = autorText,
                        // toIntOrNull devuelve null si escribo cualquier cosa en vez de
                        // un numero. En el año eso esta bien, porque admite vacio.
                        anio = anioText.toIntOrNull(),
                        // En el puntaje no admito vacio, asi que si no es numero va 0.
                        // coerceIn lo encierra entre 0 y 5: si escribo 47, guarda 5.
                        // El campo son estrellas, y una escala que no tiene tope
                        // deja de servir para comparar un libro con otro.
                        puntaje = (puntajeText.toIntOrNull() ?: 0).coerceIn(0, 5),
                        // La fecha la pone la app, no yo. Es lo que despues permite
                        // ordenar por "fecha en que lo agregue".
                        fechaAgregado = System.currentTimeMillis()
                    )

                    viewModel.agregarLibro(nuevoLibro)

                    // Limpio el formulario para poder cargar el siguiente.
                    tituloText = ""; autorText = ""; anioText = ""; puntajeText = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar libro")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selector de orden. Cada boton cambia el orden en pantalla y ademas lo deja
        // guardado, asi la proxima vez la app se abre como la deje.
        Text(text = "Ordenar por:", style = MaterialTheme.typography.bodySmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BotonDeOrden("Autor", Preferencias.ORDEN_AUTOR, orden) {
                orden = it
                preferencias.guardarOrden(it)
            }
            BotonDeOrden("Titulo", Preferencias.ORDEN_TITULO, orden) {
                orden = it
                preferencias.guardarOrden(it)
            }
            BotonDeOrden("Agregado", Preferencias.ORDEN_FECHA, orden) {
                orden = it
                preferencias.guardarOrden(it)
            }
        }

        Text(
            text = "Mis libros (${listaLibros.size})",
            style = MaterialTheme.typography.titleMedium
        )

        // LazyColumn solo dibuja lo que se ve en pantalla, asi que no se pone lenta
        // cuando la biblioteca crezca.
        LazyColumn {
            items(listaLibros) { libro ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = libro.titulo, fontWeight = FontWeight.Bold)
                            Text(
                                text = libro.autor,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                // Si el año vino vacio muestro un guion y no "null".
                                text = "Año: ${libro.anio ?: "-"} | Puntaje: ${libro.puntaje}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        TextButton(onClick = { viewModel.eliminarLibro(libro) }) {
                            Text("Borrar", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

// Saque los tres botones de orden a su propia funcion para no repetir el mismo bloque
// tres veces. El que esta activo se muestra en negrita.
@Composable
fun BotonDeOrden(
    texto: String,
    valor: String,
    ordenActual: String,
    alTocar: (String) -> Unit
) {
    TextButton(onClick = { alTocar(valor) }) {
        Text(
            text = texto,
            fontWeight = if (ordenActual == valor) FontWeight.Bold else FontWeight.Normal
        )
    }
}
