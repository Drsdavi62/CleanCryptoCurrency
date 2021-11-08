package com.plcoding.cryptocurrencyappyt.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.cryptocurrencyappyt.domain.model.Coin

@Entity
data class CoinEntity(

    @PrimaryKey
    val id: String,
    val isActive: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
)

fun CoinEntity.toCoin(): Coin {
    return Coin(
        id = id,
        isActive = isActive,
        name = name,
        rank = rank,
        symbol = symbol,
    )
}

fun Coin.toCoinEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        isActive = isActive,
        name = name,
        rank = rank,
        symbol = symbol,
    )
}