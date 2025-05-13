package com.yeminnaing.randomquotegenerator.repositories

import com.yeminnaing.randomquotegenerator.ApiService
import com.yeminnaing.randomquotegenerator.Resources
import com.yeminnaing.randomquotegenerator.response.QuoteResponse
import javax.inject.Inject

class GetQuoteRepo @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getQuote(id: Int): Resources<QuoteResponse> {
        return try {
            val response = apiService.getQuotes(id)
            Resources.Success(response)
        } catch (e: Exception) {
            Resources.Error(e.localizedMessage ?: "Unknown Error")
        }
    }
}