package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

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
        recyclerView.adapter = ListaProdutosAdapter(
            context = this, produtos = listOf(
                Produto(
                    nome = "teste",
                    descricao = "teste desc",
                    valor = BigDecimal("19.99")
                ), Produto(
                    nome = "teste 2",
                    descricao = "teste desc 2",
                    valor = BigDecimal("29.99")
                ), Produto(
                    nome = "teste 3",
                    descricao = "teste desc 3",
                    valor = BigDecimal("39.99")
                )
            )
        )

        // O RecyclerView exige um layoutManager para exibir a lista
        // Pode ser por código ou pelo layout
        //recyclerView.layoutManager = LinearLayoutManager(this)
    }
}