package com.example.practicaexamen

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.split
import android.transition.Slide
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider

class RestauranteActivity : AppCompatActivity() {

    //Tenemos que crear estos objetos para el Intent
    companion object {
        const val RES_PERSONAS = "EXTRA_PERSONAS"
        const val RES_NOMBRE = "EXTRA_NOMBRE"
        const val RES_HORA = "EXTRA_HORA"
    }

    private lateinit var viewPuerta2 : CardView
    private lateinit var viewPuerta4 : CardView
    private lateinit var viewPuerta8 : CardView
    private lateinit var viewNombre : EditText
    private lateinit var viewIntNombre : CardView
    private lateinit var viewBotonIz : FloatingActionButton
    private lateinit var viewBotonDe : FloatingActionButton
    private lateinit var viewHora : TextView
    private lateinit var viewBotonFinal : FloatingActionButton
    private lateinit var viewMinutos : TextView
    private lateinit var viewBarraMin : Slider

    private var puertaSelect : String = ""
    private var posicion : Int = 0
    private val horas = listOf("20:00","21:00","22:00")
    //Una lista con las horas que tenemos disponibles


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_restaurante)
        initComponents()
        initListeners()
        initUI()
    }

    private fun initUI() {
        setColor(viewPuerta2,false)
        setColor(viewPuerta4,false)
        setColor(viewPuerta8,false)
        setHoras(posicion)
    }

    //Para decirle a un view num que es un texto es necesario un set

    private fun initListeners() {
        viewPuerta2.setOnClickListener{
            setColor(viewPuerta2,true)
            setColor(viewPuerta4,false)
            setColor(viewPuerta8,false)
            puertaSelect = "Dos personas"
        }
        viewPuerta4.setOnClickListener{
            setColor(viewPuerta2,false)
            setColor(viewPuerta4,true)
            setColor(viewPuerta8,false)
            puertaSelect = "Cuatro personas"

        }
        viewPuerta8.setOnClickListener{
            setColor(viewPuerta2,false)
            setColor(viewPuerta4,false)
            setColor(viewPuerta8,true)
            puertaSelect = "Ocho personas"
        }

        viewBarraMin.addOnChangeListener{ _, value, _ ->

            val partes = viewHora.text.toString().split(":")
            var horas = partes[0].toInt()
            var minutos = partes[1].toInt()

            minutos += value.toInt()
            //%d representa numeros enteros y 02 que son dos digitos y que puede ser 0
            val nuevaHora = String.format("%02d:%02d", horas, minutos)

            // Actualizar el TextView con la nueva hora
            viewMinutos.text = nuevaHora
        }

        viewBotonDe.setOnClickListener{
            setHoras(++posicion)
        }

        viewBotonIz.setOnClickListener{
            setHoras(--posicion)
        }

        viewIntNombre.setOnClickListener{
            viewNombre.text.clear()
        }
            

        viewBotonFinal.setOnClickListener{
            //Para pasar los datos a la otra pantalla primero los pasaremos a texto
            val name = viewNombre.text.toString()//Hacer toast
            val personas = puertaSelect
            val horaDisp = viewMinutos.text.toString()
            //El intent para contectarlo con la otra pantalla
            val intentGA = Intent(this,ReservaActivity::class.java)

            if(name.isNotEmpty() && puertaSelect != "") {
                //PutExtra para añadir lo que queremos mandar junto al objeto creado antes
                intentGA.putExtra(RES_NOMBRE, name)
                intentGA.putExtra(RES_PERSONAS, personas)
                intentGA.putExtra(RES_HORA, horaDisp)

                //Para mandarlo
                startActivity(intentGA)
            }else if (name.isEmpty()){
                Toast.makeText(this, "Escribe tu nombre", Toast.LENGTH_SHORT).show()
            }else if(puertaSelect == ""){
                Toast.makeText(this, "Selecciona el numero de personas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //El when para cambiar la posicion para cambiar el numero
    private fun setHoras(nuevaPosicion: Int) {
        posicion = when {
            nuevaPosicion < 0 -> 2
            nuevaPosicion > 2 -> 0
            else -> nuevaPosicion
        }
        viewHora.text = horas[posicion]
    }

    private fun getBackGroundColor(bo :Boolean): Int {
        val colorReference = if(bo) {
            R.color.elementoSelec
        } else {
            R.color.cajaText
        }
        return ContextCompat.getColor(this,colorReference)
    }

    //Para hacer el setCardBackgroundColor,es necesario el getBackgroundColor para sacar el color
    private fun setColor(cardView: CardView, bo : Boolean){
        cardView.setCardBackgroundColor(getBackGroundColor(bo))
    }

    private fun initComponents() {
        viewPuerta2 = findViewById(R.id.puerta2per)
        viewPuerta4 = findViewById(R.id.puerta4per)
        viewPuerta8 = findViewById(R.id.puerta8per)
        viewNombre = findViewById(R.id.nombre)
        viewBotonDe = findViewById(R.id.botonDerecha)
        viewBotonIz = findViewById(R.id.botonIzquierda)
        viewHora = findViewById(R.id.horas)
        viewIntNombre = findViewById(R.id.intNombre)
        viewBotonFinal = findViewById(R.id.botonFinal)
        viewBarraMin = findViewById(R.id.barraMin)
        viewMinutos = findViewById(R.id.minutos)
    }


}

