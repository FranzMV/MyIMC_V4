package com.franvalle.myimc_v4.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.franvalle.myimc_v4.R
import com.franvalle.myimc_v4.model.Imc

/**
 * Clase encargada de mostrar un DialogFragment
 */
class MyDialogFragment(imc: Imc) : DialogFragment() {

    private var myImc :Imc = imc

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(getString(R.string.mensajeAlerDialog))
                    .setPositiveButton(android.R.string.ok){_, _ ->
                        FileUtils().writeFile(context!!, myImc)

                        MessageUtils().mostrarSnackBar(getString(R.string.mensaje_aceptar),
                            it.findViewById(android.R.id.content))
                    }
                    .setNegativeButton(android.R.string.cancel){_, _ ->
                        MessageUtils().mostrarSnackBar(getString(R.string.mensaje_cancelar),
                                it.findViewById(android.R.id.content))
                    }
            builder.create()

        }?: throw IllegalStateException("Error en MyDialogFragment")
    }
}