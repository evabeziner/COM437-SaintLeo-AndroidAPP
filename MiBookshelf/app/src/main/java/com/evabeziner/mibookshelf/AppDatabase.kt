package com.evabeziner.mibookshelf

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// entities es la lista de tablas que va a tener la base. Por ahora solo Libro;
// cuando sume colecciones va a quedar [Libro::class, Coleccion::class, LibroColeccion::class].
// version = 1 es el control de cambios del esquema: si le agrego una columna a la
// tabla, hay que subir este numero y decirle a Room como migrar los datos viejos.
@Database(entities = [Libro::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Room implementa esta funcion sola y me devuelve el DAO listo para usar.
    abstract fun libroDao(): LibroDao

    companion object {
        // @Volatile hace que todos los hilos vean el mismo valor de INSTANCE.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Patron Singleton: abrir la base es caro, asi que la abro una sola vez
        // y despues devuelvo siempre la misma. Si dos partes de la app abrieran
        // conexiones distintas, podrian terminar mostrando datos diferentes.
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mibookshelf_database" // nombre del archivo fisico en el telefono
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
