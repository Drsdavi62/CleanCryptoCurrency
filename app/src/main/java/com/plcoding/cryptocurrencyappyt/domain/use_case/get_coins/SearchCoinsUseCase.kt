package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins

import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import java.util.*
import javax.inject.Inject

class SearchCoinsUseCase @Inject constructor() {

    operator fun invoke(unfilteredList: List<Coin>, query: String): List<Coin> {
        val filteredList: List<Coin> = if (query.isNotBlank()) {
            unfilteredList.filter {
                it.name.lowercase(Locale.getDefault()).contains(
                    query.lowercase(Locale.getDefault())
                )
            }
        } else {
            unfilteredList
        }
        return filteredList
    }
}