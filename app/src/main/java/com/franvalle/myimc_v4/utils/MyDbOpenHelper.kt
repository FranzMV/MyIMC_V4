package com.franvalle.myimc_v4.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.franvalle.myimc_v4.model.Imc


/**
 * Clase para gestionar la creación y manejo de la BD
 */
class MyDbOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){

    private val TAG ="SQLite"

    companion object{
        val DATABASE_VERSION = 1
        const val DATABASE_NAME = "myImc.db"
        const val TABLA_IMC = "imc"
        const val COLUMNA_ID = "_id"
        const val COLUMNA_FECHA = "fecha"
        const val COLUMNA_SEXO = "sexo"
        const val COLUMNA_IMC = "imc"
        const val COLUMNA_PESO = "peso"
        const val COLUMNA_ALTURA = "altura"
        const val COLUMNA_ESTADO = "estado"
    }


    /**
     * Este método es llamado cuando se crea la base por primer vez. Debe producirse la creación
     * de todas las tablas que forman la base de datos
     */
    override fun onCreate(db: SQLiteDatabase?) {

        try {

            val createTableIMC = "CREATE TABLE $TABLA_IMC "+
                    "($COLUMNA_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "$COLUMNA_FECHA TEXT, "+
                    "$COLUMNA_SEXO TEXT, "+
                    "$COLUMNA_IMC REAL, "+
                    "$COLUMNA_PESO REAL, "+
                    "$COLUMNA_ALTURA REAL, "+
                    "$COLUMNA_ESTADO TEXT)"

            db!!.execSQL(createTableIMC)

        }catch (e:SQLiteException){ Log.e("$TAG (onCreate)", e.message.toString())}
    }

    /**
     * Este método se invocará cuando la base de datos necesite ser actualizada.
     * Se utiliza para hacer DROPs, ñadir tablas o cualque acción que actualice el esquema de la BD
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        try {

            val dropTableIMC = "DROP TABLE IF EXISTS $TABLA_IMC"
            db!!.execSQL(dropTableIMC)
            onCreate(db)

        }catch (e: SQLiteException){Log.e("$TAG (onCreate)", e.message.toString())}

    }


    /**
     * Método opcional. Se llamará a este método después de abrir la base de
     * datos, antes de ello, comprobará si está en modo lectura. Se llama justo
     * después de establecer la conexión y crear el esquema.
     */
    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        Log.d("$TAG (onOpen)", "¡¡Base de datos abierta!!")
    }


    /**
     * Función para añadir un dato de tipo IMC a la tabla imc
     */
    fun addIMC(imc: Imc){

        //Se crea un arrayMap<>() haciendo uso de ContentValue()
        val myIMCdata = ContentValues()
        myIMCdata.put(COLUMNA_ID, imc._id)
        myIMCdata.put(COLUMNA_FECHA,imc.fecha)
        myIMCdata.put(COLUMNA_SEXO, imc.sexo)
        myIMCdata.put(COLUMNA_IMC, imc.calculoIMC)
        myIMCdata.put(COLUMNA_PESO, imc.peso)
        myIMCdata.put(COLUMNA_ALTURA, imc.altura)
        myIMCdata.put(COLUMNA_ESTADO, imc.resultadoIMC)

        //Se abre la BD en modo escritura
        val db = this.writableDatabase
        db.insert(TABLA_IMC, null, myIMCdata)
        db.close()

    }


    /**
     * Función para eliminar un dato de tipo IMC en la tabla imc
     */
    fun deleteIMC(identifier: Int):Int{

        val args = arrayOf(identifier.toString())
        //Se abre la base de datos en modo escritura
        val db = this.writableDatabase
        val result = db.delete(TABLA_IMC, "$COLUMNA_ID = ?", args)

        db.close()
        return result
    }

    /**
     * Función para cargar los datos del cursor en una Lista de tipo Imc
     */
    fun cargarDatosCursor(cursor: Cursor): MutableList<Imc>{

        val datos: MutableList<Imc> = ArrayList()
        if(cursor.moveToFirst()) {
            do {
                val imc = Imc()
                imc._id = cursor.getString(cursor.getColumnIndex(COLUMNA_ID))
                imc.fecha = cursor.getString(cursor.getColumnIndex(COLUMNA_FECHA))
                imc.peso = cursor.getDouble(cursor.getColumnIndex(COLUMNA_PESO))
                imc.sexo = cursor.getString(cursor.getColumnIndex(COLUMNA_SEXO))
                imc.altura = cursor.getDouble(cursor.getColumnIndex(COLUMNA_ALTURA))
                imc.calculoIMC = cursor.getDouble(cursor.getColumnIndex(COLUMNA_IMC))
                imc.resultadoIMC = cursor.getString(cursor.getColumnIndex(COLUMNA_ESTADO))
                datos.add(imc)
            } while (cursor.moveToNext())

            cursor.close()

        }else Log.d("Sin datos","No hay datos en el cursor")

        return datos
    }
}