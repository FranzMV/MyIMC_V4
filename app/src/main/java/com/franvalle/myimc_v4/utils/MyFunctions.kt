package com.franvalle.myimc_v4.utils

import java.util.*

class MyFunctions {

    fun obtenerFecha() : String{

        val hoy = Calendar.getInstance()

        return "${hoy.get(Calendar.DAY_OF_MONTH)}"+
                "-${hoy.get(Calendar.MONTH)}"+
                "-${hoy.get(Calendar.YEAR)}"
    }

}