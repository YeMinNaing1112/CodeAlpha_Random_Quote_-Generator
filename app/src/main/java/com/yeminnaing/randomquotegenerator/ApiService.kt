package com.yeminnaing.randomquotegenerator

import com.yeminnaing.randomquotegenerator.response.QuoteResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("quotes/{id}")
    suspend fun getQuotes(@Path("id") id: Int): QuoteResponse
}