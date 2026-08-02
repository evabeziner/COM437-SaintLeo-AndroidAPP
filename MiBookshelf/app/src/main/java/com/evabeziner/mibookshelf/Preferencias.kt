package com.evabeziner.mibookshelf

import android.content.Context

// Las opciones de la pantalla de configuracion no son registros, son valores sueltos
// que se leen al abrir la app y se escriben de a uno. Ese es el caso de SharedPreferences.
// Armarles una tabla en Room seria usar la herramienta equivocada.
//
// Nota para mi: la documentacion de Android ya recomienda DataStore en lugar de
// SharedPreferences. Uso SharedPreferences porque es lo que vemos en la materia,
// pero esta es la parte que habria que cambiar si la app siguiera despues del curso.
class Preferencias(context: Context) {

    // MODE_PRIVATE: solo mi app puede leer este archivo.
    private val prefs = context.getSharedPreferences("config_mibookshelf", Context.MODE_PRIVATE)

    // Si nunca guarde nada, devuelve autor, que es el orden que puse por defecto en el wireframe.
    fun obtenerOrden(): String {
        return prefs.getString(CLAVE_ORDEN, ORDEN_AUTOR) ?: ORDEN_AUTOR
    }

    // apply() escribe en segundo plano. commit() lo haria en el momento y frenaria la pantalla.
    fun guardarOrden(orden: String) {
        prefs.edit().putString(CLAVE_ORDEN, orden).apply()
    }

    // Constantes para no andar escribiendo los textos sueltos por todos lados
    // y que un error de tipeo me deje sin encontrar la preferencia.
    companion object {
        private const val CLAVE_ORDEN = "orden_por_defecto"

        const val ORDEN_AUTOR = "autor"
        const val ORDEN_TITULO = "titulo"
        const val ORDEN_FECHA = "fecha"
    }
}
