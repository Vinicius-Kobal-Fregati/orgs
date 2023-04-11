package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto

@Dao
interface ProdutoDao {
    // Graças ao suspend e do androidx.room:room-ktx, não precisamos executar essas funções
    // no escopo IO, o programa que vai cuidar disso

    @Query("SELECT * FROM Produto")
    suspend fun buscaTodos(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    suspend fun buscaTodosOrdenadorPorNomeAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    suspend fun buscaTodosOrdenadorPorNomeDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao ASC")
    suspend fun buscaTodosOrdenadorPorDescricaoAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao DESC")
    suspend fun buscaTodosOrdenadorPorDescricaoDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor ASC")
    suspend fun buscaTodosOrdenadorPorValorAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor DESC")
    suspend fun buscaTodosOrdenadorPorValorDesc(): List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg produtos: Produto)

    // O Delete e Update operam sobre o Id
    @Delete
    suspend fun remove(produto: Produto)

    // Com o onConflict Replace no salve, não precisamos do update
//    @Update
//    fun altera(produto: Produto)

    // Como podemos não ter um produto com o id específico, pode-se retornar null
    @Query("SELECT * FROM Produto WHERE id = :id")
    suspend fun buscaPorId(id: Long): Produto?
}