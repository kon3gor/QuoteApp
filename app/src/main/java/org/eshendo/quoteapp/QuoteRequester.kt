package org.eshendo.quoteapp

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuoteRequester {

    private val api  = Retrofit.Builder()
        .baseUrl(Constants.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuoteApiService::class.java)

    fun call(callback: RequestCallback){
        api.getQuote().enqueue(object : Callback<Quote>{
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                if (response.isSuccessful){
                    callback.onResult(response.body()!!)
                }else{
                    val message = "${response.code()} with ${response.message()}"
                    callback.onError(message)
                }
            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {
                Log.e("duck", "", t)
                callback.onError("Check logs")
            }
        })
    }
}