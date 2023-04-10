package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.alura.orgs.extension.formataParaMoedaBrasileira
import br.com.alura.orgs.extension.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.dialog.FormularioImagemDialog
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
    private var idProduto = 0L

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

        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            title = "Alterar produto"
            url = produtoCarregado.imagem
            idProduto = produtoCarregado.id
            with(binding) {
                activityFormularioProdutoImagem
                    .tentaCarregarImagem(
                        produtoCarregado.imagem,
                        this@FormularioProdutoActivity
                    )
                activityFormularioProdutoNome.setText(produtoCarregado.nome)
                activityFormularioProdutoDescricao.setText(produtoCarregado.descricao)
                activityFormularioProdutoValor.setText(produtoCarregado.valor.toPlainString())
            }
        }
    }

    private fun configuraBotaoSalvar() {
        //val botaoSalvar = findViewById<Button>(R.id.activity_formulario_produto_botao_salvar)
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar
//        val dao = ProdutosDao()
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()

        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
//            dao.adiciona(produtoNovo)
            produtoDao.salva(produtoNovo)
            //Finaliza a activity
            finish()
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
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}