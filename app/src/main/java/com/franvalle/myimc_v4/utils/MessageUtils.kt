package com.franvalle.myimc_v4.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Clase encargada de manejar los diferentes mensajes en la aplicación
 */
class MessageUtils() {

    /**
     * Función adicional para mostrar un SnackBar
     */
    fun mostrarSnackBar(mensaje: String, view: View) =
            Snackbar.make(
                    view,
                    mensaje,
                    Snackbar.LENGTH_LONG
            ).show()
}