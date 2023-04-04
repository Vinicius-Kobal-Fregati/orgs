package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import kotlin.math.log

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {

    // Com o AppCompatActivity, podemos deixar de usar o setContentView passando
    // para seu construtor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_formulario_produto)

        val botaoSalvar = findViewById<Button>(R.id.botao_salvar)
        botaoSalvar.setOnClickListener {
            val campoNome = findViewById<EditText>(R.id.nome)
            val nome = campoNome.text.toString()
            Log.i("FormularioProduto", nome)
        }
    }
}