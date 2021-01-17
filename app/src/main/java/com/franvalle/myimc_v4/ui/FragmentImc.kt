package com.franvalle.myimc_v4.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import com.franvalle.myimc_v4.R
import com.franvalle.myimc_v4.databinding.FragmentImcBinding
import com.franvalle.myimc_v4.model.Imc
import com.franvalle.myimc_v4.utils.FileUtils
import com.franvalle.myimc_v4.utils.MessageUtils
import java.util.*
import kotlin.math.pow


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentImc.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentImc : Fragment() {

    //Objeto de tipo imc
    private lateinit var imc: Imc
    //Variable para el Binding
    private lateinit var binding: FragmentImcBinding

    //Variables necesarias para calcular el IMC
    private var peso : Double = 0.0
    private var altura : Double = 0.0
    private var resultado : Double = 0.0

    //Para obtener la fecha en forma de String
    private lateinit var fecha : String
    //Para obtener el resultado del IMC (no numérico) en forma de String
    private lateinit var resultadoIMC : String


    /**
     *  Función donde cargamos tanto el view como el binding para inflarlos
     */
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImcBinding.inflate(inflater,container, false)
        return binding.root
    }

    /**
     * Función donde se desarrolla la lógica del fragment
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Evento de botón para calcular el IMC
         */
        binding.btnCalcular.setOnClickListener {

            /**
                Si ninguno de los dos EditText están vacíos, calculamos el IMC
                si no, mostramos el Toast con el aviso
             */
            if (binding.editTextPeso.text.toString().isNotEmpty() &&
                    binding.editTextAltura.text.toString().isNotEmpty()) {

                //Calculamos el IMC con los datos obtenidos en los EditTex
                peso = binding.editTextPeso.text.toString().toDouble()
                altura = binding.editTextAltura.text.toString().toDouble()
                resultado = peso / (altura.pow(2)) * 10000

                /**
                    *Controlamos qué tipo de RadioButton (Hombre o Mujer) ha seleccionado el usuario
                    *y calculamos el IMC corresponciente llamando a la función calcularResultadoIMC.
                    *Si no se selecciona ninguno, se muestra el SnackBar para avisar.
                    *Si el input de datos del usuario es correcto, mostramos un AletDialog para confirmar
                    *si quiere guardar la información o no en el histórico
                 */
                when {
                    //Si se ha elegido Hombre
                    binding.radioBtnHombre.isChecked -> {

                        binding.txtViewIMC.text = String.format("%.2f", resultado)
                        resultadoIMC = calcularResultadoIMC(resultado, binding.radioBtnHombre)
                        fecha = obtenerFecha()
                        imc = Imc(peso, altura, fecha, binding.radioBtnHombre.text.toString(), resultado, resultadoIMC)
                        createAlertDialog(getString(R.string.mensajeAlerDialog), imc, binding.mainLayout.context )
                    }
                    //Si se ha elegido Mujer
                    binding.radioBtnMujer.isChecked -> {

                        binding.txtViewIMC.text = String.format("%.2f", resultado)
                        resultadoIMC = calcularResultadoIMC(resultado, binding.radioBtnMujer)
                        fecha = obtenerFecha()
                        imc = Imc(peso, altura, fecha, binding.radioBtnMujer.text.toString(), resultado, resultadoIMC)
                        createAlertDialog(getString(R.string.mensajeAlerDialog), imc, binding.mainLayout.context)
                    }
                    else -> MessageUtils().mostrarSnackBar(getString(R.string.mensajeErrorRadioButtons), binding.mainLayout)
                }

            } else MessageUtils().mostrarSnackBar(getString(R.string.mensajeErrorEditText), binding.mainLayout)
        }
    }


    /**
     * Función para calcular el IMC dependiendo del resultado y el género
     * seleccionado por el usuario en el RadioButton
     */
    private fun calcularResultadoIMC(resultado : Double, sexo: RadioButton) : String {

        var resultadoIMC = ""
        //ICM para Hombre
        when (sexo.text) {
            binding.radioBtnHombre.text -> {
                when (resultado) {
                    in 0.0..18.5 -> {
                        binding.txtViewResultado.text = getString(R.string.PesoInferior)
                        resultadoIMC = getString(R.string.PesoInferior)
                    }
                    in 18.5..24.9 -> {
                        binding.txtViewResultado.text = getString(R.string.PesoNormal)
                        resultadoIMC = getString(R.string.PesoNormal)
                    }
                    in 25.0..29.9 -> {
                        binding.txtViewResultado.text = getString(R.string.Sobrepeso)
                        resultadoIMC = getString(R.string.Sobrepeso)
                    }
                    else -> {
                        binding.txtViewResultado.text = getString(R.string.Obesidad)
                        resultadoIMC = getString(R.string.Obesidad)
                    }
                }
            }
            //IMC Mujer
            binding.radioBtnMujer.text -> {
                when (resultado) {
                    in 0.0..18.5 -> {
                        binding.txtViewResultado.text = getString(R.string.PesoInferior)
                        resultadoIMC = getString(R.string.PesoInferior)
                    }
                    in 18.5..23.9 -> {
                        binding.txtViewResultado.text = getString(R.string.PesoNormal)
                        resultadoIMC = getString(R.string.PesoNormal)
                    }
                    in 24.0..28.9 -> {
                        binding.txtViewResultado.text = getString(R.string.Sobrepeso)
                        resultadoIMC = getString(R.string.Sobrepeso)
                    }
                    else ->{
                        binding.txtViewResultado.text = getString(R.string.Obesidad)
                        resultadoIMC = getString(R.string.Obesidad)
                    }
                }
            }
        }
        return resultadoIMC
    }

    /**
     * Función que devuelve un String con la fecha en la que se realiza el cálculo del IMC
     */
    private fun obtenerFecha() : String{

        val hoy = Calendar.getInstance()

        return "${hoy.get(Calendar.DAY_OF_MONTH)}"+
                "-${hoy.get(Calendar.MONTH)}"+
                "-${hoy.get(Calendar.YEAR)}"
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