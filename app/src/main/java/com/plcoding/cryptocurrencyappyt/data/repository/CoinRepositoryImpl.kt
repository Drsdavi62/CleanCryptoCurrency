package com.plcoding.cryptocurrencyappyt.data.repository

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.cache.CoinDao
import com.plcoding.cryptocurrencyappyt.data.cache.model.toCoin
import com.plcoding.cryptocurrencyappyt.data.cache.model.toCoinEntity
import com.plcoding.cryptocurrencyappyt.data.remote.CoinPaprikaApi
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoinDetail
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi,
    private val dao: CoinDao
) : CoinRepository {

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getCoins(): Resource<List<Coin>> {
        return try {
            val coins = api.getCoins().map { it.toCoin() }

            GlobalScope.async {
                println("COMECOU")
                coins.map { dao.insertCoin(it.toCoinEntity()) }
                println("FOI")
            }

            Resource.Success<List<Coin>>(data = coins)
        } catch (e: IOException) {
            getCoinsFromCache()
        } catch (e: HttpException) {
            getCoinsFromCache()
        }
    }

    private suspend fun getCoinsFromCache(): Resource<List<Coin>> {
        return try {
            val cachedCoins = dao.getCoins().map { it.toCoin() }

            Resource.Success<List<Coin>>(data = cachedCoins)
        } catch (e: Exception) {
            Resource.Error<List<Coin>>(
                message = e.localizedMessage ?: "Unable to load data"
            )
        }
    }

    override suspend fun getCoinById(coinId: String): Resource<CoinDetail> {
        return try {
            val coin = api.getCoinById(coinId).toCoinDetail()
            Resource.Success<CoinDetail>(data = coin)
        } catch (e: HttpException) {
            Resource.Error<CoinDetail>(
                message = e.localizedMessage ?: "An unexpected error occurred"
            )
        } catch (e: IOException) {
            Resource.Error<CoinDetail>(message = "Couldn't reach server, check your internet connection")
        }
    }

}