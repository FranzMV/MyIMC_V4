package com.franvalle.myimc_v4.model

class Imc {
    var peso: Double? = null
    set(value) {
        field = if(value!! > 0.00) value else 0.00
    }

    var altura: Double? = null
    set(value) {
        field = if(value!! > 0.00) value else 0.00
    }

    var calculoIMC: Double? = null
    set(value) {
        field = if(value!! > 0.00) value else 0.00
    }
    var fecha: String? = null
    var sexo: String? = null
    var resultadoIMC:String? = null


    //Función auxiliar para poder guardar los datos en el fichero con el formato correcto
    fun guardarEnFichero():String = "$peso;$altura;$fecha;$sexo;$calculoIMC;$resultadoIMC\n"
}