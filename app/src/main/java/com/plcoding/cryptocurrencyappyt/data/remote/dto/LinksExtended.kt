package com.plcoding.cryptocurrencyappyt.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LinksExtended(
    val url: String,
    val type: String,
    val stats: Stats
)