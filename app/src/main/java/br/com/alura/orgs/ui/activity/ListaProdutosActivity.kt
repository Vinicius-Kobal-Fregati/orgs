package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosBinding
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.*

class ListaProdutosActivity : AppCompatActivity() {

    //private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(
        context = this
    )
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //O R pode acessar tudo que está dentro de resources
        //setContentView(R.layout.activity_lista_produtos)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()

        // Escopo da main
        val scope = MainScope()
        // Roda no scopo de courotine
        scope.launch {
            // Aqui mudamos o contexto da courotine
            val produtos = withContext(Dispatchers.IO) {
                //delay(2000)
                // Retorna a última linha
                produtoDao.buscaTodos()
            }
            //Quando ficar pronto, vai atualizar a tela
            adapter.atualiza(produtos)
        }
//        adapter.atualiza(dao.buscaTodos())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val produtosOrdenado: List<Produto>? = when (item.itemId) {
            R.id.menu_lista_produtos_ordenar_nome_asc ->
                produtoDao.buscaTodosOrdenadorPorNomeAsc()
            R.id.menu_lista_produtos_ordenar_nome_desc ->
                produtoDao.buscaTodosOrdenadorPorNomeDesc()
            R.id.menu_lista_produtos_ordenar_descricao_asc ->
                produtoDao.buscaTodosOrdenadorPorDescricaoAsc()
            R.id.menu_lista_produtos_ordenar_descricao_desc ->
                produtoDao.buscaTodosOrdenadorPorDescricaoDesc()
            R.id.menu_lista_produtos_ordenar_valor_asc ->
                produtoDao.buscaTodosOrdenadorPorValorAsc()
            R.id.menu_lista_produtos_ordenar_valor_desc ->
                produtoDao.buscaTodosOrdenadorPorValorDesc()
            R.id.menu_lista_produtos_ordenar_sem_ordem ->
                produtoDao.buscaTodos()
            else -> null
        }
        produtosOrdenado?.let { produtos ->
            adapter.atualiza(produtos)
        }
        return super.onOptionsItemSelected(item)
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

    private fun vaiParaFormularioProdutoComId(id: Long) {
        Intent(this, FormularioProdutoActivity::class.java)
            .apply { putExtra(CHAVE_PRODUTO_ID, id) }
            .let { startActivity(it) }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutoRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItemListener = { produto ->
            val intentParaDetalhes = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
//                putExtra(CHAVE_PRODUTO, produto)
                putExtra(CHAVE_PRODUTO_ID, produto.id)
            }
            startActivity(intentParaDetalhes)
        }
        adapter.quandoClicaEmEditar = { produto ->
            vaiParaFormularioProdutoComId(produto.id)
        }
        adapter.quandoClicaEmRemover = { produto, index ->
            produtoDao.remove(produto)
            adapter.remove(index)
        }
    }

    // O RecyclerView exige um layoutManager para exibir a lista
    // Pode ser por código ou pelo layout
    //recyclerView.layoutManager = LinearLayoutManager(this)
}