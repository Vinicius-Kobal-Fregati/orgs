package br.com.alura.orgs.dao

import br.com.alura.orgs.model.Produto
import java.math.BigDecimal

class ProdutosDao {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodos(): List<Produto> {
        // Aqui retornamos uma cópia de valor da lista
        // Se alguém quiser editar, não altera a original
        return produtos.toList()
    }

    // Esse companion object é a forma de deixarmos nosso atributo estático
    companion object {
        val produtos = mutableListOf<Produto>(
            Produto(
                nome = "Salada de frutas",
                descricao = "Laranja, maçãs e uva",
                valor = BigDecimal("19.83")
            )
        )
    }
}