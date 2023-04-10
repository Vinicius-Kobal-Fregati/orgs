package br.com.alura.orgs.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {

    @TypeConverter
    // Como aqui receberíamos do banco de dados, pode ser null
    fun deDouble(valor: Double?): BigDecimal {
        // O toString evita alguns erros que o construtor do bigDecimal tem com Double
        return valor?.let { BigDecimal(valor.toString()) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun bigDecimalParaDouble(valor: BigDecimal?): Double? {
        return valor?.let { valor.toDouble() }
    }
}