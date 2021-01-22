package com.franvalle.myimc_v4.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
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

        //Instancia de myImcDBHelper
        myImcDbHelper = MyDbOpenHelper(context, null)
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

            binding.textViewID.text = imc._id
            binding.txtVmostrarMes.text = context.resources.getStringArray(R.array.meses)[imc.fecha!!.split(delimitador)[1].toInt()]
            binding.txtVmostrarDia.text = imc.fecha!!.split(delimitador)[0]
            binding.txtVmostrarAnyo.text = imc.fecha!!.split(delimitador)[2]
            binding.tvPeso.text = imc.peso.toString()
            binding.tvAltura.text = imc.altura.toString()
            binding.txtVsexo.text = imc.sexo
            binding.txtViMC.text = String.format("%.2f", imc.calculoIMC)
            binding.txtVresultadoIMC.text = imc.resultadoIMC

            //Ocultamos el textView del ID correspondiente al identificador del IMC en la BD
            binding.textViewID.isVisible = false


            /**
             * Evento de pulsación larga para eliminar un elemento IMC del recyclerView.
             * Captamos la posición del elemento seleccionado para eliminarlo primero de la
             * lista y después llamamos a la función eliminarIMC para confirmar la eliminación
             * y eliminarlo también de la BD
             */
            itemView.setOnLongClickListener{
                val position = adapterPosition
                confirmacionEmliminarIMC(listaHistorico, context, position, binding.textViewID)
                return@setOnLongClickListener true
            }
        }
    }

    /**
     * Función encargada de crear un alertDialog y preguntar al
     * usuario si desea ELIMINAR el elemento IMC seleccionado mediante
     * pulsación larga
     */
    private fun confirmacionEmliminarIMC(listaHistorico: MutableList<Imc>, context: Context, position: Int, txtID: TextView) {

        val builder = AlertDialog.Builder(context)
        val imc = listaHistorico.get(position)//Para la posición en la lista del Item seleccionado
        val identificador = txtID.text.toString().toInt()//Para obtener el ID que identifica al Item en la BD

        //Se crea el alert dialog
        builder.apply {
            setTitle("Eliminar IMC")
            setMessage("¿Desea eliminar el IMC ${String.format("%.2f",imc.calculoIMC)} del ${imc.fecha}?")
            //Confirmar eliminación de un registr IMC
            setPositiveButton(R.string.aceptar){dialog, _ ->
                listaHistorico.removeAt(position)//Eliminamos el item de la lista
                myImcDbHelper.deleteIMC(identificador)//Lo eliminamos de la BD
                notifyDataSetChanged()//Actualizamos el recyclerView
            }
            setNegativeButton(R.string.cancelar){_,_ -> }
        }
        builder.show()
    }
}


