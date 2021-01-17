package com.franvalle.myimc_v4.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.franvalle.myimc_v4.R
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
    fun HistoricoAdapter(lista: MutableList<Imc>, contexto: Context){
        this.listaHistorico = lista
        this.context = contexto
    }


    /**
     * Función que se encarga de devolver el viewHolder configurado
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.item_historico, parent, false
            )
        )
    }

    /**
     * Se encarga de pasar los objetos uno a uno al viewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listaHistorico[position]
        holder.bind(item, context)
    }


    /**
     * Función que devuelve el número de items en la Lista que guarda el Histórico
     */
    override fun getItemCount(): Int {
        return listaHistorico.size
    }



    /**
     * Clase ViewHolder: se encarga de rellenar cada una de las vistas que se inflarán en el
     * RecyclerView
     */
    inner class ViewHolder(view: View) : RecyclerView1.ViewHolder(view){

        private val delimitador: String = "-"
        private val view = view.findViewById(R.id.itemHistoricoLayout) as View

        //Variables para partir la fecha en dia, mes y año
        private val mes = view.findViewById(R.id.txtVmostrarMes) as TextView
        private val dia = view.findViewById(R.id.txtVmostrarDia) as TextView
        private val anyo = view.findViewById(R.id.txtVmostrarAnyo) as TextView

        //resto de variables para completar un objeto de tipo IMC
        private val peso = view.findViewById(R.id.tvPeso)as TextView
        private val altura = view.findViewById(R.id.tvAltura)as TextView
        private val sexo = view.findViewById(R.id.txtVsexo) as TextView
        private val calculoIMC = view.findViewById(R.id.txtViMC) as TextView
        private val resultadoIMC = view.findViewById(R.id.txtVresultadoIMC) as TextView


        fun bind(imc: Imc, context: Context){

            /**
             *  Asignamos los campos de la fecha con respecto a un array de strings (creado en strings.xml)
             *  que contiene los nombres de los meses del año.
             */
            mes.text = context.resources.getStringArray(R.array.meses)[imc.fecha!!.split(delimitador)[1].toInt()]
            dia.text = imc.fecha!!.split(delimitador)[0]
            anyo.text = imc.fecha!!.split(delimitador)[2]

            //El resto de campos de un objeto de tipo IMC
            peso.text = imc.peso.toString()
            altura.text = imc.altura.toString()
            sexo.text = imc.sexo
            calculoIMC.text = String.format("%.2f", imc.calculoIMC)
            resultadoIMC.text = imc.resultadoIMC

            //Cada vez que se haga click en una tarjeta de IMC, mostramos el resultado en un Toast
            itemView.setOnClickListener{
                MessageUtils().mostrarSnackBar("IMC: ${calculoIMC.text}",view)
            }
        }
    }
}

