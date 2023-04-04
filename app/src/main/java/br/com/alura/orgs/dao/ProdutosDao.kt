package br.com.alura.orgs.dao

import br.com.alura.orgs.model.Produto

class ProdutosDao {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodos() : List<Produto> {
        // Aqui retornamos uma cópia de valor da lista
        // Se alguém quiser editar, não altera a original
        return produtos.toList()
    }

    // Esse companion object é a forma de deixarmos nosso atributo estático
    companion object {
        val produtos = mutableListOf<Produto>()
    }
}