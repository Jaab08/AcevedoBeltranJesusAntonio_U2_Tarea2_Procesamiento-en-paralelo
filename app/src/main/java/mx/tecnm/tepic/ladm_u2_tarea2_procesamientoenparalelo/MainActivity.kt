package mx.tecnm.tepic.ladm_u2_tarea2_procesamientoenparalelo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var contadorHilo = 0
    var hilo = Hilo(p = this)

    fun mensajeNoSeEjecuta() {
            AlertDialog.Builder(this)
                .setMessage("EL HILO AUN NO SE EJECUTA")
                .setTitle("ATENCIÓN")
                .setPositiveButton("Ok") { d, i ->
                    d.dismiss()
                }
                .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            try {
                hilo.start()
            } catch (e:Exception) {
                AlertDialog.Builder(this)
                    .setMessage("EL HILO YA SE ESTA EJECUTANDO")
                    .setTitle("ATENCIÓN")
                    .setPositiveButton("Ok") { d, i ->
                        d.dismiss()
                    }
                    .show()
            }
        } // BOTON INICIAR

        button2.setOnClickListener {
            if (hilo.estaIniciado()) {
                if (!hilo.estaPausado()) {
                    hilo.pausarHilo()
                } else {
                    AlertDialog.Builder(this)
                        .setMessage("EL HILO YA SE ENCUENTRA PAUSADO")
                        .setTitle("ATENCIÓN")
                        .setPositiveButton("Ok") { d, i ->
                            d.dismiss()
                        }
                        .show()
                }
            } else{
                mensajeNoSeEjecuta()
            }
        } // BOTON PAUSAR

        button3.setOnClickListener {
            if (hilo.estaIniciado()) {
                if (hilo.estaPausado()) {
                    hilo.desPausarHilo()
                } else {
                    AlertDialog.Builder(this)
                        .setMessage("EL HILO YA SE ENCUENTRA EN EJECUCIÓN")
                        .setTitle("ATENCIÓN")
                        .setPositiveButton("Ok") { d, i ->
                            d.dismiss()
                        }
                        .show()
                }
            } else{
                mensajeNoSeEjecuta()
            }
        } // BOTON DESPAUSAR

        button4.setOnClickListener {
            if (hilo.estaIniciado()) {
                contadorHilo = 0
                textView.text = "0"
            } else{
                mensajeNoSeEjecuta()
            }
        } // BOTON REINICIAR

        button5.setOnClickListener {
            if (hilo.estaIniciado()) {
                hilo.terminarHilo()
            } else{
                mensajeNoSeEjecuta()
            }
        } // BOTON TERMINAR

    }
}

class Hilo(p:MainActivity) : Thread(){
    var puntero = p  //p solo existe en la primera linea
    var inicio = false
    var pausar = false

    fun terminarHilo(){
        inicio = false
        puntero.textView.text = "HILO TERMINADO"
    }

    fun estaIniciado() : Boolean {
        if (inicio == true) {
            return true
        }
        return false
    }

    fun pausarHilo() {
            pausar = true
    }

    fun desPausarHilo() {
            pausar = false
    }

    fun estaPausado() : Boolean {
        if (pausar == true) {
            return true
        }
        return false
    }

    override fun run() {
        super.run()
        inicio = true
        while (inicio) {
            if (pausar == false) {
                puntero.runOnUiThread {
                    puntero.textView.text = puntero.contadorHilo.toString()
                }
                puntero.contadorHilo++
            }

            sleep(100)
        }
    }

}