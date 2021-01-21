package com.franvalle.myimc_v4.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.franvalle.myimc_v4.R
import com.franvalle.myimc_v4.databinding.ItemHistoricoBinding
import com.franvalle.myimc_v4.model.Imc
import androidx.recyclerview.widget.RecyclerView
import com.franvalle.myimc_v4.utils.MyDbOpenHelper

class HistoricoAdapter : RecyclerView.Adapter<HistoricoAdapter.ViewHolder>(){

    //Lista donde guardar el histórico
    private var listaHistorico : MutableList<Imc> = ArrayList()
    //Variable para el DBOpenHelper
    private lateinit var myImcDbHelper : MyDbOpenHelper
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
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val binding = ItemHistoricoBinding.bind(view)
        private val delimitador: String = "-"

        fun bind(imc:Imc){
            binding.txtVmostrarMes.text = context.resources.getStringArray(R.array.meses)[imc.fecha!!.split(delimitador)[1].toInt()]
            binding.txtVmostrarDia.text = imc.fecha!!.split(delimitador)[0]
            binding.txtVmostrarAnyo.text = imc.fecha!!.split(delimitador)[2]
            binding.tvPeso.text = imc.peso.toString()
            binding.tvAltura.text = imc.altura.toString()
            binding.txtVsexo.text = imc.sexo
            binding.txtViMC.text = String.format("%.2f", imc.calculoIMC)
            binding.txtVresultadoIMC.text = imc.resultadoIMC

            /**
             *Evento de pulsación larga para eliminar un elemento IMC del recyclerView
             */
            itemView.setOnLongClickListener{

                val position = adapterPosition
                Toast.makeText(binding.itemHistoricoLayout.context, "Long click detected $position", Toast.LENGTH_SHORT).show()
                mostrarAlertDialog(listaHistorico, context, position)
                return@setOnLongClickListener true
            }
        }
    }

    /**
     * Función encargada de crear un alertDialog y preguntar al
     * usuario si desea ELIMINAR el elemento IMC seleccionado mediante
     * pulsación larga
     */
    private fun mostrarAlertDialog(listaHistorico: MutableList<Imc>, context: Context, position: Int) {

        val builder = AlertDialog.Builder(context)
        val imc = listaHistorico.get(position)

        //Se crea el alert dialog
        builder.apply {
            setTitle("Eliminar IMC")
            //Se assigna el cuerpo del mensaje
            setMessage("¿Desea eliminar el IMC ${String.format("%.2f",imc.calculoIMC)} del ${imc.fecha}?")
            setPositiveButton(R.string.aceptar){_, _ ->
                listaHistorico.removeAt(position)
                notifyDataSetChanged()
            }
            setNegativeButton(R.string.cancelar){_,_ ->

            }
        }
        builder.show()
    }
}


