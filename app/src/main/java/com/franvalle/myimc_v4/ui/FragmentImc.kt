package com.franvalle.myimc_v4.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.franvalle.myimc_v4.R
import com.franvalle.myimc_v4.databinding.FragmentImcBinding
import com.franvalle.myimc_v4.model.Imc
import com.franvalle.myimc_v4.utils.FileUtils
import com.franvalle.myimc_v4.utils.MessageUtils
import com.franvalle.myimc_v4.utils.MyFunctions
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentImc.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentImc : Fragment() {

    //Objeto de tipo imc
    private lateinit var myImc: Imc
    //Variable para el Binding
    private lateinit var binding: FragmentImcBinding


    /**
     *  Función donde cargamos tanto el view como el binding para inflarlos
     */
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentImcBinding.inflate(inflater,container, false)
        return binding.root
    }

    /**
     * Función donde se desarrolla la lógica del fragment
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Instancia de un objeto de tipo IMC
        myImc = Imc()

        /**
         * Evento de botón para calcular el IMC
         */
        binding.btnCalcular.setOnClickListener {

            /**
                Si ninguno de los dos EditText están vacíos, calculamos el IMC
                si no, mostramos el Toast con el aviso
             */
            if (binding.editTextPeso.text.toString().isNotEmpty() && binding.editTextAltura.text.toString().isNotEmpty()) {

                if((binding.txtViewPeso.text.toString().toDouble() > 0.00) && binding.txtViewAltura.text.toString().toDouble() >0.00){

                    //Calculamos el IMC con los datos obtenidos en los EditTex
                    myImc.fecha = MyFunctions().obtenerFecha()
                    myImc.peso = binding.editTextPeso.text.toString().toDouble()
                    myImc.altura = binding.editTextAltura.text.toString().toDouble()
                    myImc.calculoIMC = MyFunctions().calcularIMC(myImc.peso!!, myImc.altura!!)

                    //Controlamos qué tipo de RadioButton (Hombre o Mujer) ha seleccionado el usuario
                    when {
                        //Si se ha elegido Hombre
                        binding.radioBtnHombre.isChecked -> {
                            myImc.sexo = binding.radioBtnHombre.text.toString()
                            myImc.resultadoIMC = MyFunctions().obtenerResultadoIMC(
                                    it.context,
                                    myImc.calculoIMC!!,
                                    binding.radioBtnHombre.text.toString()
                            )
                        }
                        //Si se ha elegido Mujer
                        binding.radioBtnMujer.isChecked -> {
                            myImc.sexo = binding.radioBtnMujer.text.toString()
                            myImc.resultadoIMC = MyFunctions().obtenerResultadoIMC(
                                    it.context,
                                    myImc.calculoIMC!!,
                                    binding.radioBtnMujer.text.toString()
                            )

                        }else -> MessageUtils().mostrarSnackBar(getString(R.string.mensajeErrorRadioButtons), view)
                    }

                    binding.txtViewIMC.text = String.format("%.2f", myImc.calculoIMC)
                    binding.txtViewResultado.text = myImc.resultadoIMC

                }else MessageUtils().mostrarSnackBar("Los campos de peso y altura deben ser mayores que cero", view)

            } else MessageUtils().mostrarSnackBar(getString(R.string.mensajeErrorEditText), view)
        }
    }


    /**
     * Función encargada de crear un alertDialog y preguntar al
     * usuario si desea guardar la información del IMC en el fichero
     * Dependiendo de la selección del usuario, la información se guarda
     * en el fichero o no, mostrando el SanackBar correspondiente
     */
    private fun createAlertDialog(message : String, imc: Imc, context: Context) {

        val builder = AlertDialog.Builder(context)
        val fileUtils = FileUtils(context, binding.mainLayout)

        //Se crea el alert dialog
        builder.apply {
            //Se assigna el cuerpo del mensaje
            setMessage(message)
            //Boton para confirmar guardar la información en el fichero
            setPositiveButton(R.string.aceptar){_, _ ->
                //Guardamos los datos en el fichero
                fileUtils.writeFile(imc)
                //Avisamos
                MessageUtils().mostrarSnackBar(getString(R.string.mensaje_aceptar), binding.mainLayout)
            }
            setNegativeButton(R.string.cancelar){_,_ ->
                //No guardamos datos en el fichero pero avisamos
                MessageUtils().mostrarSnackBar(getString(R.string.mensaje_cancelar), binding.mainLayout)
            }
        }
        builder.show()
    }
}