package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): List<Produto>

    @Insert
    fun salva(vararg produtos: Produto)

    // O Delete e Update operam sobre o Id
    @Delete
    fun remove(produto: Produto)

    @Update
    fun altera(produto: Produto)
}