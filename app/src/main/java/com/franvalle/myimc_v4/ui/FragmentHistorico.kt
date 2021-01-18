package com.franvalle.myimc_v4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.franvalle.myimc_v4.R
import com.franvalle.myimc_v4.adapters.HistoricoAdapter
import com.franvalle.myimc_v4.databinding.FragmentHistoricoBinding
import com.franvalle.myimc_v4.model.Imc
import com.franvalle.myimc_v4.utils.FileUtils


class FragmentHistorico() : Fragment() {

    //Variable para el adaptador del recyclerView
    private val adaptador : HistoricoAdapter = HistoricoAdapter()
    //Variable para el binding
    private lateinit var binding: FragmentHistoricoBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentHistoricoBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        updateRecycler()
    }

    //Función para actualizar el contenido del Recycler cuando se añada un dato
    private fun updateRecycler(){

        if(context!!.fileList().contains(getString(R.string.nombreFichero))) {
            binding.recyclerViewHistorico.setHasFixedSize(true)
            binding.recyclerViewHistorico.adapter = adaptador
            binding.recyclerViewHistorico.layoutManager = LinearLayoutManager(activity)
            adaptador.HistoricoAdapter(crearListaHistorico(), context!!)
        }
    }

    /**
     * Función para cargar una lista con objetos de tipo Imc
     */
    private fun crearListaHistorico(): MutableList<Imc> {

        return FileUtils().readFile(context!!)
    }
}