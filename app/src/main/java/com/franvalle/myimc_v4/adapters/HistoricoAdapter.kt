package com.franvalle.myimc_v4.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.franvalle.myimc_v4.R
import com.franvalle.myimc_v4.databinding.ItemHistoricoBinding
import com.franvalle.myimc_v4.model.Imc
import com.franvalle.myimc_v4.utils.MessageUtils
import androidx.recyclerview.widget.RecyclerView as RecyclerView1

class HistoricoAdapter : RecyclerView1.Adapter<HistoricoAdapter.ViewHolder>(){

    //Lista donde guardar el histórico
    private var listaHistorico : MutableList<Imc> = ArrayList()
    //Variable de tipo context para establecer el contexto
    private lateinit var context: Context

    /**
     * Constructor de la clase: recibe como parámetro una lista de tipo IMC y el contexto
     */
    fun HistoricoAdapter(listaHistorico: MutableList<Imc> , contexto: Context){
        this.listaHistorico = listaHistorico
        this.context = contexto
    }


    /**
     * Función que se encarga de devolver el viewHolder configurado
     */
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
                layoutInflater.inflate(
                        R.layout.item_historico,
                        parent,
                        false
                )
        )
    }

    /**
     * Se encarga de pasar los objetos uno a uno al viewHolder
     */
    override fun onBindViewHolder(
            holder:HistoricoAdapter.ViewHolder,
            position: Int)
    {
        val item = listaHistorico.get(position)
        holder.bind(item)

    }


    /**
     * Función que devuelve el número de items en la Lista que guarda el Histórico
     */
    override fun getItemCount(): Int {
        return if(listaHistorico.size > 0){
            listaHistorico.size
        }else 0
    }


    /**
     * Clase ViewHolder: se encarga de rellenar cada una de las vistas que se inflarán en el
     * RecyclerView
     */
    inner class ViewHolder(view: View) : RecyclerView1.ViewHolder(view){

        private val binding = ItemHistoricoBinding.bind(view)
        private val delimitador: String = "-"

        fun bind(imc: Imc){

            binding.txtVmostrarMes.text = context.resources.getStringArray(R.array.meses)[imc.fecha!!.split(delimitador)[1].toInt()]
            binding.txtVmostrarDia.text = imc.fecha!!.split(delimitador)[0]
            binding.txtVmostrarAnyo.text = imc.fecha!!.split(delimitador)[2]
            binding.tvPeso.text = imc.peso.toString()
            binding.tvAltura.text = imc.altura.toString()
            binding.txtVsexo.text = imc.sexo
            binding.txtViMC.text = String.format("%.2f", imc.calculoIMC)
            binding.txtVresultadoIMC.text = imc.resultadoIMC

            //Cada vez que se haga click en una tarjeta de IMC, mostramos el resultado en un SnackBar
            itemView.setOnClickListener{
                MessageUtils().mostrarSnackBar("IMC: ${binding.txtVresultadoIMC.text}",binding.root)
            }
        }
    }
}


