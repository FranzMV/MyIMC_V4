package com.franvalle.myimc_v4.model

class Imc (
    val peso: Double,
    val altrura: Double,
    val fecha: String,
    val sexo: String,
    val calculoIMC: Double,
    val resultadoIMC:String){

    //Función auxiliar para poder guardar los datos en el fichero con el formato correcto
    fun guardarEnFichero():String = "$peso;$altrura;$fecha;$sexo;$calculoIMC;$resultadoIMC\n"
}