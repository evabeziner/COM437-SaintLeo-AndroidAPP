package com.evabeziner.mibookshelf

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// El DAO es el contrato con la base: aca declaro que operaciones quiero poder hacer
// y Room escribe el codigo que las ejecuta.
@Dao
interface LibroDao {

    // Las tres consultas de lectura son la misma, solo cambia el ORDER BY.
    // Tener una por cada criterio de orden es lo que hace que el selector de la
    // pantalla principal funcione sin ordenar la lista a mano en Kotlin.
    // Flow significa que la lista se refresca sola cuando la tabla cambia.
    @Query("SELECT * FROM tabla_libros ORDER BY autor ASC")
    fun obtenerLibrosPorAutor(): Flow<List<Libro>>

    @Query("SELECT * FROM tabla_libros ORDER BY titulo ASC")
    fun obtenerLibrosPorTitulo(): Flow<List<Libro>>

    // Este va DESC para que lo ultimo que agregue aparezca primero.
    @Query("SELECT * FROM tabla_libros ORDER BY fechaAgregado DESC")
    fun obtenerLibrosPorFecha(): Flow<List<Libro>>

    // suspend: son funciones que se ejecutan en segundo plano para no frenar la
    // pantalla mientras la base escribe.
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertarLibro(libro: Libro)

    @Update
    suspend fun actualizarLibro(libro: Libro)

    @Delete
    suspend fun borrarLibro(libro: Libro)
}
