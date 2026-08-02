package com.evabeziner.mibookshelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// El ViewModel es el intermediario entre la base y la pantalla. Heredar de ViewModel()
// hace que sobreviva a cosas como rotar el telefono, que destruyen y rearman la Activity.
class LibroViewModel(private val dao: LibroDao) : ViewModel() {

    // Devuelve la lista ya ordenada. El orden no lo resuelvo en Kotlin sino en el DAO,
    // porque SQLite ordena mucho mejor de lo que lo haria yo recorriendo una lista.
    fun obtenerLibros(orden: String): Flow<List<Libro>> {
        return when (orden) {
            Preferencias.ORDEN_TITULO -> dao.obtenerLibrosPorTitulo()
            Preferencias.ORDEN_FECHA -> dao.obtenerLibrosPorFecha()
            else -> dao.obtenerLibrosPorAutor()
        }
    }

    // viewModelScope.launch abre un hilo secundario para que la funcion suspend
    // pueda escribir en la base sin congelar la pantalla mientras tanto.
    fun agregarLibro(libro: Libro) {
        viewModelScope.launch {
            dao.insertarLibro(libro)
        }
    }

    fun eliminarLibro(libro: Libro) {
        viewModelScope.launch {
            dao.borrarLibro(libro)
        }
    }
}
