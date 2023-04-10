package br.com.alura.orgs.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extension.formataParaMoedaBrasileira
import br.com.alura.orgs.extension.tentaCarregarImagem
import br.com.alura.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {

    private var produtoId: Long = 0L
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    // Com lateinit, podemos inicializar posteriormente essa variável
    // Evita o nullable
    //private lateinit var produto: Produto
    private var produto: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaProdutoNoBanco()
    }

    private fun buscaProdutoNoBanco() {
        produto = produtoDao.buscaPorId(produtoId)
        produto?.let {
            preencheCampos(it, this)
        } ?: finish()
    }

    // Sobrescrita para criar menus
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Sobrescrita para configurar o listener dos itens do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Isso dá segurança no lateinit, evita exceptions
        // Não foi usado mais pois tirou-se o lateinit
//        if (::produto.isInitialized) {
        when (item.itemId) {
            R.id.menu_detalhes_produto_remover -> {
                produto?.let { produtoDao.remove(it) }
                finish()
            }
            R.id.menu_detalhes_produto_editar -> {
                Intent(this, FormularioProdutoActivity::class.java)
                    .apply {
                        putExtra(CHAVE_PRODUTO_ID, produtoId)
                    }.let {
                        startActivity(it)
                    }
            }
        }
//        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        /*
        // Como passou a buscar pelo id, não faz mais sentido receber o produto completo
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            // Dessa forma, os dados do produto não seriam atualizados, usaria sempre o que está
            // em memória
            //produto = produtoCarregado
            produtoId = produtoCarregado.id
        } ?: finish()
         */
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun preencheCampos(produtoCarregado: Produto, contexto: Context) {
        with(binding) {
            activityDetalhesProdutoImageview.tentaCarregarImagem(produtoCarregado.imagem, contexto)
            activityDetalhesProdutoNome.text = produtoCarregado.nome
            activityDetalhesProdutoDescricao.text = produtoCarregado.descricao
            activityDetalhesProdutoValor.text =
                produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }
}