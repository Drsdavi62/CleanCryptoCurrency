package com.plcoding.cryptocurrencyappyt.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plcoding.cryptocurrencyappyt.data.cache.model.CoinEntity
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Query("SELECT * FROM coinentity")
    suspend fun getCoins(): List<CoinEntity>

    @Query("SELECT * FROM coinentity WHERE id = :id")
    suspend fun getCoinById(id: Int): CoinEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coinEntity: CoinEntity)
}