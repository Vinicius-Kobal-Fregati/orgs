package br.com.alura.orgs.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //O R pode acessar tudo que está dentro de resources
        setContentView(R.layout.activity_main)

        // Processo de bind, vinculação entre o código fonte e o arquivo de layout
//        val nome = findViewById<TextView>(R.id.nome)
//        val descricao = findViewById<TextView>(R.id.descricao)
//        val valor = findViewById<TextView>(R.id.valor)
//
//        nome.text = "Cesta"
//        descricao.text = "Laranja, manga e maçã"
//        valor.text = "19.99"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = ListaProdutosAdapter()
    }
}