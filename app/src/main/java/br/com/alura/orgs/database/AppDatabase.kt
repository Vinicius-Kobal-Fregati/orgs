package br.com.alura.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converters
import br.com.alura.orgs.database.dao.ProdutoDao
import br.com.alura.orgs.model.Produto

// Além de exportar o schema precisamos configurar o build.gradle(app)
@Database(entities = [Produto::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    // Como nossa classe é abstrata, precisamos que o método seja estático para podermos acessar ele
    // Para isso, usamos o companion object
    companion object {
        fun instancia(contexto: Context): AppDatabase {
            return Room.databaseBuilder(
                contexto,
                AppDatabase::class.java,
                "orgs.db"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}