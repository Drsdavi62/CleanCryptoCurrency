package com.plcoding.cryptocurrencyappyt.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.cryptocurrencyappyt.data.cache.model.CoinEntity

@Database(
    version = 1,
    entities = [CoinEntity::class]
)
abstract class CoinDatabase: RoomDatabase() {

    abstract val coinDao: CoinDao

    companion object {
        const val DATABASE_NAME = "coin_db"
    }
}