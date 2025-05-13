package com.yeminnaing.randomquotegenerator

sealed class Resources<T> {
    data class Success<T>(val data:T):Resources<T>()
    data class Error<T>(val error: String   ):Resources<T>()
}