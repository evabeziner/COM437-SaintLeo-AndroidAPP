package com.evabeziner.mibookshelf

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity le avisa a Room que esta clase no es una clase cualquiera, es una tabla.
// Le pongo nombre propio a la tabla para que no dependa de como llame a la clase.
@Entity(tableName = "tabla_libros")

// data class: le dice a Kotlin que esta clase existe para guardar datos.
data class Libro(

    // Clave primaria. Room la va incrementando sola cada vez que agrego un libro,
    // asi que arranca en 0 y nunca la escribo a mano.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Lo unico que le pido obligatorio a un libro es el titulo.
    val titulo: String,

    val autor: String,

    // El signo de pregunta significa que puede venir vacio. Tengo libros viejos
    // sin año de edicion claro y no quiero que eso me impida cargarlos.
    val anio: Int?,

    // De 0 a 5. Las estrellas del wireframe salen de aca.
    val puntaje: Int = 0,

    // Mi comentario personal sobre el libro.
    val nota: String = "",

    // La fecha en que lo agregue, guardada como milisegundos. La pone la app sola.
    val fechaAgregado: Long,

    // Si es nulo, el libro esta en casa. Si tiene texto, es el nombre de a quien se lo preste.
    val prestadoA: String? = null,

    // Ruta al archivo de la foto de portada. Guardo la ruta, no la imagen:
    // meter fotos adentro de la base la haria crecer y volveria lenta cualquier consulta.
    val portada: String? = null
)
