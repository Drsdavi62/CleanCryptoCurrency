package com.plcoding.cryptocurrencyappyt.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Stats(
    val subscribers: Int,
    val contributors: Int,
    val stars: Int,
    val followers: Int
)