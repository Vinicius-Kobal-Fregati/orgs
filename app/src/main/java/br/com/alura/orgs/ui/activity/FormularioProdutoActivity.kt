package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.dao.ProdutosDao
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.alura.orgs.databinding.FormularioImagemBinding
import br.com.alura.orgs.extension.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
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

    // Com o AppCompatActivity, podemos deixar de usar o setContentView passando
    // para seu construtor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_formulario_produto)

        // Com o View Binding, precisamos passar esse root
        setContentView(binding.root)
        configuraBotaoSalvar()

        binding
            .activityFormularioProdutoImagem
            .setOnClickListener {
                val bindingFormularioImagem = FormularioImagemBinding
                    .inflate(layoutInflater)
                bindingFormularioImagem.formularioImagemBotaoCarregar.setOnClickListener {
                    val url = bindingFormularioImagem.formularioImagemUrl.text.toString()
                    bindingFormularioImagem
                        .formularioImagemImageview
                        .tentaCarregarImagem(url, this)
                }

                AlertDialog.Builder(this)
                    //.setTitle("Título de teste")
                    //.setMessage("Mensagem de teste")
                    // Dessa forma não temos acesso aos campos, vamos ter que inflar a view
                    //.setView(R.layout.formulario_imagem)
                    .setView(bindingFormularioImagem.root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        url = bindingFormularioImagem.formularioImagemUrl.text.toString()
                        binding.activityFormularioProdutoImagem.tentaCarregarImagem(url, this)
                    }
                    .setNegativeButton("Cancelar") { _, _ ->

                    }
                    .show()
            }
    }

    private fun configuraBotaoSalvar() {
        //val botaoSalvar = findViewById<Button>(R.id.activity_formulario_produto_botao_salvar)
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar
        val dao = ProdutosDao()
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            dao.adiciona(produtoNovo)
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