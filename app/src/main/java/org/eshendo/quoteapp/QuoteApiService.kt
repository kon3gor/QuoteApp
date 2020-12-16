package org.eshendo.quoteapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface QuoteApiService {
    @GET("/quotes/random/")
    @Headers(
        Constants.HEADER1,
        Constants.HEADER2,
        Constants.HEADER3
    )
    fun getQuote() : Call<Quote>
}