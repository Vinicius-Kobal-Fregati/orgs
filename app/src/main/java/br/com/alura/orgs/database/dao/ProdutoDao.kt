package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(vararg produtos: Produto)

    // O Delete e Update operam sobre o Id
    @Delete
    fun remove(produto: Produto)

    // Com o onConflict Replace no salve, não precisamos do update
//    @Update
//    fun altera(produto: Produto)

    // Como podemos não ter um produto com o id específico, pode-se retornar null
    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaPorId(id: Long) : Produto?
}