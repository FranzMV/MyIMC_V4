package com.franvalle.myimc_v4.utils

import android.content.Context
import com.franvalle.myimc_v4.R
import java.util.*
import kotlin.math.pow

class MyFunctions {


    /**
     * Función que devuelve un String con la fecha en la que se realiza el cálculo del IMC
     */
    fun obtenerFecha() : String{

        val hoy = Calendar.getInstance()

        return "${hoy.get(Calendar.DAY_OF_MONTH)}"+
                "-${hoy.get(Calendar.MONTH)}"+
                "-${hoy.get(Calendar.YEAR)}"
    }


    /**
     * Función para calcular el IMC
     */
    fun calcularIMC(peso : Double, altura: Double):Double{

        return peso / (altura.pow(2)) * 10000
    }


    /**
     * Función para calcular el IMC dependiendo del cálculo de éste  y el género
     * seleccionado por el usuario en el RadioButton
     */
    fun obtenerResultadoIMC(context: Context, calculoIMC: Double, sexo: String) : String {

        var result: String

        if (sexo.equals("Hombre")) {
            result = when (calculoIMC) {
                in 0.0..18.5 -> { context.getString(R.string.PesoInferior) }
                in 18.5..24.9 -> { context.getString(R.string.PesoNormal) }
                in 25.0..29.9 -> { context.getString(R.string.Sobrepeso) }
                else -> { context.getString(R.string.Obesidad) }
            }
        } else {
            result = when(calculoIMC){
                in 0.0..18.5 -> { context.getString(R.string.PesoInferior) }
                in 18.5..23.9 -> { context.getString(R.string.PesoNormal) }
                in 24.0..28.9 -> { context.getString(R.string.Sobrepeso) }
                else ->{ context.getString(R.string.Obesidad) }
            }
        }
        return  result
    }
}