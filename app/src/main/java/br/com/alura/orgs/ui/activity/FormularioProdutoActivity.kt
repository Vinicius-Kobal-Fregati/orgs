package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.database.dao.ProdutoDao
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.alura.orgs.extension.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

// Sem usar o View Bind seria dessa forma
//class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {

class FormularioProdutoActivity : AppCompatActivity() {

    // Inicialização por delegação. Evita problemas por conta do ciclo de vida
    // É computada uma única vez, as outras chamadas devolvem o mesmo valor
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao: ProdutoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    // Com o AppCompatActivity, podemos deixar de usar o setContentView passando
    // para seu construtor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_formulario_produto)

        // Com o View Binding, precisamos passar esse root
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraBotaoSalvar()

        binding
            .activityFormularioProdutoImagem
            .setOnClickListener {
                FormularioImagemDialog(this)
                    .mostra(url) { imagem ->
                        url = imagem
                        binding.activityFormularioProdutoImagem
                            .tentaCarregarImagem(url, this)
                    }
            }

        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        scope.launch {
            produtoDao.buscaPorId(produtoId)?.let {
                withContext(Dispatchers.Main) {
                    title = "Alterar produto"
                    preencheCampos(it)
                }
            }
        }
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)

        /*
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            produtoId = produtoCarregado.id
            preencheCampos(produtoCarregado)
        }
         */
    }

    private fun preencheCampos(produto: Produto) {
        url = produto.imagem
        with(binding) {
            activityFormularioProdutoImagem
                .tentaCarregarImagem(
                    produto.imagem,
                    this@FormularioProdutoActivity
                )
            activityFormularioProdutoNome.setText(produto.nome)
            activityFormularioProdutoDescricao.setText(produto.descricao)
            activityFormularioProdutoValor.setText(produto.valor.toPlainString())
        }
    }

    private fun configuraBotaoSalvar() {
        //val botaoSalvar = findViewById<Button>(R.id.activity_formulario_produto_botao_salvar)
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar
//        val dao = ProdutosDao()

        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            /*
            // Com o Replace no onConflict do salva, não precisamos mais desse código
            if (produtoId > 0) {
                produtoDao.altera(produtoNovo)
            } else {
//              dao.adiciona(produtoNovo)
                produtoDao.salva(produtoNovo)

            }
             */
            scope.launch {
                produtoDao.salva(produtoNovo)
                // Finaliza a activity
                finish()
            }
        }
    }

    private fun criaProduto(): Produto {
        // Processo de bind, vinculação entre o código fonte e o arquivo de layout
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()
        // Forma sem o View Binding
        //val campoDescricao = findViewById<EditText>(R.id.activity_formulario_produto_descricao)
        val campoDescricao = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()

        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            // Como possibilitamos o update, precisamos mandar o id também
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}