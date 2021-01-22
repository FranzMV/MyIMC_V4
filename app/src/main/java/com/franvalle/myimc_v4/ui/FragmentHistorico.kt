package com.franvalle.myimc_v4.ui

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.franvalle.myimc_v4.adapters.HistoricoAdapter
import com.franvalle.myimc_v4.databinding.FragmentHistoricoBinding
import com.franvalle.myimc_v4.model.Imc
import com.franvalle.myimc_v4.utils.MyDbOpenHelper
import com.franvalle.myimc_v4.utils.MyDbOpenHelper.Companion.COLUMNA_ALTURA
import com.franvalle.myimc_v4.utils.MyDbOpenHelper.Companion.COLUMNA_ESTADO
import com.franvalle.myimc_v4.utils.MyDbOpenHelper.Companion.COLUMNA_FECHA
import com.franvalle.myimc_v4.utils.MyDbOpenHelper.Companion.COLUMNA_ID
import com.franvalle.myimc_v4.utils.MyDbOpenHelper.Companion.COLUMNA_IMC
import com.franvalle.myimc_v4.utils.MyDbOpenHelper.Companion.COLUMNA_PESO
import com.franvalle.myimc_v4.utils.MyDbOpenHelper.Companion.COLUMNA_SEXO


class FragmentHistorico() : Fragment() {

    //Variable para el adaptador del recyclerView
    private val adaptador : HistoricoAdapter = HistoricoAdapter()
    //Variable para el DBOpenHelper
    private lateinit var myImcDbHelper : MyDbOpenHelper
    //Variable para manipular la bd de SQLite
    private lateinit var db:SQLiteDatabase
    //Variable para el binding
    private lateinit var binding: FragmentHistoricoBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        //Instancia de myImcDBHelper
         myImcDbHelper = MyDbOpenHelper(context!!, null)
        // Inflate the layout for this fragment
        binding = FragmentHistoricoBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        cargarDatosHistorico()
    }

    override fun onResume() {
        super.onResume()
        cargarDatosHistorico()
    }


    /**
     * Función para cerrar la conexión con la BD
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", "Cerramos la conexión")
        db.close()
    }


    /**
     * Función para cargar una lista con objetos de tipo Imc
     */
    private fun cargarDatosHistorico() {

        db = myImcDbHelper.readableDatabase
        val cursor : Cursor = db.rawQuery(
                "SELECT * FROM ${MyDbOpenHelper.TABLA_IMC};",
                null
        )

        adaptador.HistoricoAdapter(myImcDbHelper.cargarDatosCursor(cursor), context!!)
        binding.recyclerViewHistorico.setHasFixedSize(true)
        binding.recyclerViewHistorico.layoutManager = LinearLayoutManager(context!!)
        binding.recyclerViewHistorico.adapter = adaptador
    }
}