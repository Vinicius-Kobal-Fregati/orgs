package br.com.alura.orgs.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extension.formataParaMoedaBrasileira
import br.com.alura.orgs.extension.tentaCarregarImagem
import br.com.alura.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    // Sobrescrita para criar menus
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Sobrescrita para configurar o listener dos itens do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_detalhes_produto_remover -> {
                Log.i("itemSelected", "onOptionsItemSelected: Remover")
            }
            R.id.menu_detalhes_produto_editar -> {
                Log.i("itemSelected", "onOptionsItemSelected: Editar")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            preencheCampos(produtoCarregado, this)
        } ?: finish()
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