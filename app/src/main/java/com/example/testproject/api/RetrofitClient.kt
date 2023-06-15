package com.example.testproject.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val URLGlobal = "https://api.coingecko.com/api/v3/"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()


    //retrofit builder for Order List
    fun getCoinList(): ApiInterfaces.GetCoinList {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URLGlobal)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiInterfaces.GetCoinList::class.java)
    }



}



