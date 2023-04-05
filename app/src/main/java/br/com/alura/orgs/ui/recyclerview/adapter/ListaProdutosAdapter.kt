package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.databinding.ProdutoItemBinding
import br.com.alura.orgs.model.Produto

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto>
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    // Trabalhamos com uma cópia, mantendo o original seguro
    private val produtos = produtos.toMutableList()

    // Sem o View Binding
    //class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun vincula(produto: Produto) {
            // Esse itemView é a view que mandamos para ele no construtor.
            //val nome = itemView.findViewById<TextView>(R.id.produto_item_nome)
            val nome = binding.produtoItemNome
            nome.text = produto.nome
            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao
            val valor = binding.produtoItemValor
            valor.text = produto.valor.toPlainString()
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
        holder.vincula(produto)
    }

    // Determina quantos itens queremos exibir dentro dele
    override fun getItemCount(): Int = produtos.size

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        // Notifica que os dados foram alterados
        notifyDataSetChanged()
    }
}
