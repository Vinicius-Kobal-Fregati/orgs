package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {
    // Graças ao suspend e do androidx.room:room-ktx, não precisamos executar essas funções
    // no escopo IO, o programa que vai cuidar disso

    @Query("SELECT * FROM Produto")
    // Quando o retorno é um flow, não podemos ter uma suspend function
    // Para o flow funcionar, a instância do banco de dados deve ser Singleton
    fun buscaTodos(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    fun buscaTodosOrdenadorPorNomeAsc(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    fun buscaTodosOrdenadorPorNomeDesc(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY descricao ASC")
    fun buscaTodosOrdenadorPorDescricaoAsc(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY descricao DESC")
    fun buscaTodosOrdenadorPorDescricaoDesc(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY valor ASC")
    fun buscaTodosOrdenadorPorValorAsc(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto ORDER BY valor DESC")
    fun buscaTodosOrdenadorPorValorDesc(): Flow<List<Produto>>

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