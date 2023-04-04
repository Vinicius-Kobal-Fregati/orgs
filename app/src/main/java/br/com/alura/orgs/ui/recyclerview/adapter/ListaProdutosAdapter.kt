package br.com.alura.orgs.ui.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.model.Produto

class ListaProdutosAdapter(
    private val produtos: List<Produto>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // Cria a referência viewHolder (resposável por fazer o bind das views)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    // Indica qual o item que o adapter está colocando, indicando sua posição
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    // Determina quantos itens queremos exibir dentro dele
    override fun getItemCount(): Int = produtos.size

}
