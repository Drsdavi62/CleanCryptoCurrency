package com.plcoding.cryptocurrencyappyt.presentation.coin_list

sealed class CoinListEvents {
    object StartSearch: CoinListEvents()
    object EndSearch: CoinListEvents()
    data class Search(val query: String): CoinListEvents()
}
