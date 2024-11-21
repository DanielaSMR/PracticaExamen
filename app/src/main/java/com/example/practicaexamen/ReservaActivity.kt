package com.example.practicaexamen

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import org.w3c.dom.Text

class ReservaActivity : AppCompatActivity() {

    private lateinit var viewResultado : TextView
    private lateinit var viewBoton : AppCompatButton
    private lateinit var viewPremio : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reserva)
        initComponents()
        initListeners()

        //Para agarrar el valor de los datos del restaurante utilizamos el intent y se agrega el objeto al final
        val personas = intent.getStringExtra(RestauranteActivity.RES_PERSONAS)
        val nombre = intent.getStringExtra(RestauranteActivity.RES_NOMBRE)
        val hora = intent.getStringExtra(RestauranteActivity.RES_HORA)

        //Creamos el view y ahora indicamos que dira este
        viewResultado.text = "Mesa reservada para $personas a nombre de $nombre, a las $hora"

    }

    private fun initListeners() {
        viewBoton.setOnClickListener{
            rollPremiado()
            viewPremio.visibility = View.VISIBLE
        }
    }

    private fun rollPremiado(){
        viewPremio.text = Premiado.roll()

    }

    private fun initComponents() {
        viewResultado = findViewById(R.id.resultado)
        viewBoton = findViewById(R.id.botonPremio)
        viewPremio = findViewById(R.id.mostrarPremio)
    }

}

//Para hacer un dado necesitamos una clase aparte
class Premiado(){
    companion object {
        val premios = listOf("Has ganado una bebida gratis","No has ganado nada :(")
        var cont : Int = 0
        var num : Int = 0
        fun roll(): String {
            if(cont != 1) {
                num = (0..1).random()
                cont = 1
            }
                return premios[num]
        }
    }
}