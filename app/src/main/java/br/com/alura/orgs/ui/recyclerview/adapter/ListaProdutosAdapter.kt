package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ProdutoItemBinding
import br.com.alura.orgs.extension.formataParaMoedaBrasileira
import br.com.alura.orgs.extension.tentaCarregarImagem
import br.com.alura.orgs.model.Produto

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto> = emptyList(),
    var quandoClicaNoItemListener: (produto: Produto) -> Unit = {},
    var quandoClicaEmEditar: (produto: Produto) -> Unit = {},
    var quandoClicaEmRemover: (produto: Produto, index: Int) -> Unit = { produto: Produto, index: Int -> }
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    // Trabalhamos com uma cópia, mantendo o original seguro
    private val produtos = produtos.toMutableList()

    // Sem o View Binding
    //class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    //Utilizamos o inner para ter acesso aos membros da classe superior, o quandoClicaNoItemListener
    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        // O lateinit evita nullables, assim a property pode ser inicializada depois
        private lateinit var produto: Produto

        init {
            itemView.setOnClickListener {
                // Verifica se existe os valores na property
                if (::produto.isInitialized) {
                    // Precisa do inner para podermos usar este membro
                    quandoClicaNoItemListener(produto)
                }
            }

            itemView.setOnLongClickListener {
                PopupMenu(context, it).apply {
                    //inflate(R.menu.menu_detalhes_produto)
                    // Poderíamos ter usado o inflate também
                    menuInflater.inflate(
                        R.menu.menu_detalhes_produto,
                        menu
                    )

                    // Se não tivéssemos implementado a interface OnMenuItemClickListener,
                    // poderíamos ter feito dessa forma
//                    setOnMenuItemClickListener { item ->
//                        when (item.itemId) {
//                            R.id.menu_detalhes_produto_remover -> {
//                                quandoClicaEmRemover(produto)
//                            }
//                            R.id.menu_detalhes_produto_editar -> {
//                                quandoClicaEmEditar(produto)
//                            }
//                        }
//                        return@setOnMenuItemClickListener true
//                    }

                    // Usará o onMenuItemClick
                    setOnMenuItemClickListener(this@ViewHolder)
                }.show()
                // Retornamos true para ele consumir o evento e não acionar outro listener,
                // como o clickListener
                true
            }
        }

        fun vincula(produto: Produto, context: Context) {
            this.produto = produto
            // Esse itemView é a view que mandamos para ele no construtor.
            //val nome = itemView.findViewById<TextView>(R.id.produto_item_nome)
            val nome = binding.produtoItemNome
            nome.text = produto.nome
            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao
            val valor = binding.produtoItemValor
            valor.text = produto.valor.formataParaMoedaBrasileira()

            val visibilidade = if (produto.imagem != null) {
                View.VISIBLE
            } else {
                // Não aparece a view e ainda remove seu container (não ocupa espaço)
                View.GONE
                // O INVISIBLE não aparece a view mas ainda tem o espaço ocupado
            }

            binding.imageView.visibility = visibilidade

            // Esse load é uma extension function do coil
            binding.imageView.tentaCarregarImagem(produto.imagem, context)
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when (it.itemId) {
                    R.id.menu_detalhes_produto_editar -> {
                        quandoClicaEmEditar(produto)
                    }
                    R.id.menu_detalhes_produto_remover -> {
                        quandoClicaEmRemover(produto, bindingAdapterPosition)
                    }
                }
            }
            // Consome o evento de click
            return true
        }
    }

    // Cria a referência viewHolder (resposável por fazer o bind das views)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // Forma de transformar um layout em uma view, inflando ele
        val inflater = LayoutInflater.from(context)
        // O attachToRoot precisa ser falso para o RecyclerView poder ter o controle do que exibir
        //val view = inflater.inflate(R.layout.produto_item, parent, false)
        val binding = ProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    // Indica qual o item que o adapter está colocando, indicando sua posição
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto, context)
    }

    // Determina quantos itens queremos exibir dentro dele
    override fun getItemCount(): Int = produtos.size

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        // Notifica que os dados foram alterados
        notifyDataSetChanged()
    }

    // Assim evitamos uma busca a mais no banco de dados
    // Se tivermos utilizado alguma ordenação, dessa forma elas erá mantida!
    fun remove(id: Int) {
        this.produtos.removeAt(id)
        notifyItemRemoved(id)
    }
}
