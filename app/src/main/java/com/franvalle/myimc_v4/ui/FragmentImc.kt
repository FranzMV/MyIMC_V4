package com.franvalle.myimc_v4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.franvalle.myimc_v4.R
import com.franvalle.myimc_v4.databinding.FragmentImcBinding
import com.franvalle.myimc_v4.model.Imc
import com.franvalle.myimc_v4.utils.MessageUtils
import com.franvalle.myimc_v4.utils.MyDialogFragment
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
    //Objeto de tipo MyDialogFragment
    private lateinit var myDialogFragment: DialogFragment
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

        // Evento de botón para calcular el IMC
        binding.btnCalcular.setOnClickListener {

            //Si ninguno de los dos EditText están vacíos, calculamos el IMC
            if (binding.editTextPeso.text.toString().isNotEmpty() &&
                    binding.editTextAltura.text.toString().isNotEmpty()) {

                //Calculamos el IMC con los datos obtenidos en los EditTex
                myImc.fecha = MyFunctions().obtenerFecha()
                myImc.peso = binding.editTextPeso.text.toString().toDouble()
                myImc.altura = binding.editTextAltura.text.toString().toDouble()
                myImc.calculoIMC = MyFunctions().calcularIMC(myImc.peso!!, myImc.altura!!)

                //Controlamos qué tipo de RadioButton (Hombre o Mujer) ha seleccionado el usuario
                when {
                    //Si se ha seleccionado Hombre
                    binding.radioBtnHombre.isChecked -> {

                        myImc.sexo = binding.radioBtnHombre.text.toString()
                        myImc.resultadoIMC = MyFunctions().obtenerResultadoIMC(
                                it.context,
                                myImc.calculoIMC!!,
                                binding.radioBtnHombre.text.toString()
                        )
                        //Obtenemos el resto de datos del IMC
                        binding.txtViewIMC.text = String.format("%.2f", myImc.calculoIMC)
                        binding.txtViewResultado.text = myImc.resultadoIMC
                        //Pasamos el objeto IMC creado a myDialogFragment para que éste pregunte
                        //al usuario si desea guardar los datos en la BD
                        myDialogFragment = MyDialogFragment(myImc)
                        myDialogFragment.show(fragmentManager!!, "SaveFile")

                    }
                    //Si se ha seleccionado Mujer
                    binding.radioBtnMujer.isChecked -> {

                        myImc.sexo = binding.radioBtnMujer.text.toString()
                        myImc.resultadoIMC = MyFunctions().obtenerResultadoIMC(
                                it.context,
                                myImc.calculoIMC!!,
                                binding.radioBtnMujer.text.toString()
                        )
                        //Obtenemos el resto de datos del IMC
                        binding.txtViewIMC.text = String.format("%.2f", myImc.calculoIMC)
                        binding.txtViewResultado.text = myImc.resultadoIMC
                        //Pasamos el objeto IMC creado a myDialogFragment para que éste pregunte
                        //al usuario si desea guardar los datos en la BD
                        myDialogFragment = MyDialogFragment(myImc)
                        myDialogFragment.show(fragmentManager!!, "SaveFile")

                    }
                    else -> MessageUtils().mostrarSnackBar(getString(R.string.mensajeErrorRadioButtons), view)
                }

            }else MessageUtils().mostrarSnackBar(getString(R.string.mensajeErrorEditText),view)
        }
    }
}