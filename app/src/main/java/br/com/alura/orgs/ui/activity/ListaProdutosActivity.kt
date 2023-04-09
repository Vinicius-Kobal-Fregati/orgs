package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.com.alura.orgs.dao.ProdutosDao
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosBinding
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class ListaProdutosActivity : AppCompatActivity() {

    private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(
        context = this,
        produtos = dao.buscaTodos()
    )
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //O R pode acessar tudo que está dentro de resources
        //setContentView(R.layout.activity_lista_produtos)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "orgs.db"
        )
            .allowMainThreadQueries()
            .build()

        val produtoDao = db.produtoDao()
        produtoDao.salva(
            Produto(
                nome = "Teste nome 1",
                descricao = "Teste desc 1",
                valor = BigDecimal("10.0")
            )
        )
        adapter.atualiza(produtoDao.buscaTodos())
    }

    override fun onResume() {
        super.onResume()
//        adapter.atualiza(dao.buscaTodos())
    }

    private fun configuraFab() {
        //val fab = findViewById<FloatingActionButton>(R.id.activity_lista_produto_fab)
        val fab = binding.activityListaProdutoFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutoRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItemListener = { produto ->
            val intentParaDetalhes = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO, produto)
            }
            startActivity(intentParaDetalhes)
        }

        // O RecyclerView exige um layoutManager para exibir a lista
        // Pode ser por código ou pelo layout
        //recyclerView.layoutManager = LinearLayoutManager(this)
    }
}