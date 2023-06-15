package com.example.testproject.api

import com.example.testproject.DataModel
import retrofit2.Call
import retrofit2.http.*


interface ApiInterfaces{



    // This is for Order Details Interface
    interface GetCoinList {
        @GET("coins/list/")
        fun coinList(
            @Query("include_platform") ip: Boolean,
        ): Call<DataModel>
    }


}
