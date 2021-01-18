package com.franvalle.myimc_v4.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * Clase encargada de manejar los diferentes mensajes en la aplicación
 */
class MessageUtils() {


    /**
     * Función adicional para mostrar un Toast
     */
    fun mostrarToast(mensaje : String, context:Context) {
        Toast.makeText(
            context,
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }

    /**
     * Función adicional para mostrar un SnackBar
     */
    fun mostrarSnackBar(mensaje: String, view: View) {
        Snackbar.make(
            view,
            mensaje,
            Snackbar.LENGTH_LONG
        ).show()
    }
}