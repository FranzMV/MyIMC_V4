package com.franvalle.myimc_v4.utils

import android.app.Activity
import android.content.Context
import com.franvalle.myimc_v4.R
import com.franvalle.myimc_v4.model.Imc
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter


/**
 * Clase encargada de manejar la lectura y escritura de ficheros
 */
class FileUtils {

    /**
     * Función para escribir los datos obtenidos del IMC en un fichero de texto(MyImc.txt)
     */
    fun writeFile(context: Context, imc: Imc) {

        try {

            //Si el fichero no existe se crea, si existe se sobreescribe
            val salida = OutputStreamWriter(context.openFileOutput(
                        context.getString(R.string.nombreFichero),
                        Activity.MODE_APPEND))

            //Escribimos los datos en el fichero
            salida.write(imc.guardarEnFichero())

            //Se confirma la escritura
            salida.flush()
            salida.close()

        } catch (e: IOException) { MessageUtils().mostrarToast(e.toString(), context) }
    }


    /**
     * Función que lee el fichero myIMC.txt y devuelve una lista con los datos cargados
     */
    fun readFile(context: Context): MutableList<Imc> {

        val delimitador = ";"
        val lista: MutableList<Imc> = ArrayList()
        val myImc = Imc()

        //Comprobamos si existe el fichero, si existe, lo leemos, si no, mostramos un aviso
        if (context.fileList().contains(context.getString(R.string.nombreFichero))) {

            try {

                val entrada = InputStreamReader(context.openFileInput(context.getString(R.string.nombreFichero)))
                val br = BufferedReader(entrada)
                var linea = br.readLine()

                //Mientras el fichero no esté vacío lo leemos y añadimos los datos a una lista de tipo Imc
                while (!linea.isNullOrEmpty()) {
                    myImc.peso = linea.split(delimitador)[0].toDouble()
                    myImc.altura = linea.split(delimitador)[1].toDouble()
                    myImc.fecha =linea.split(delimitador)[2]
                    myImc.sexo =linea.split(delimitador)[3]
                    myImc.calculoIMC = linea.split(delimitador)[4].toDouble()
                    myImc.resultadoIMC =linea.split(delimitador)[5]

                    lista.add(myImc)
                    linea = br.readLine()
                }
                br.close()
                entrada.close()

            } catch (ex: IOException) { MessageUtils().mostrarToast(ex.toString(), context) }

        } else  MessageUtils().mostrarToast(context.getString(R.string.errorFicheroVacio),context)

        return lista
    }
}