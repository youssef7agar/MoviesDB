package com.example.moviesdb.common

import android.content.Context
import javax.inject.Inject

class Preferences @Inject constructor(
    context: Context
) {

    private var watchlistLocal by context.getSharedPreferences(
        Constants.WATCHLIST,
        Context.MODE_PRIVATE
    ).stringSet(key = { "watchlist" })

    fun addWatchList(list: List<String>) {
        this.watchlistLocal = list.toSet()
    }

    fun getWatchList(): List<String> {
        return this.watchlistLocal.toList()
    }
}