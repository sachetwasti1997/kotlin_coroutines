package com.example.kotlin_coroutines.request

data class SearchRequest(
    var userName: String ?= null,
    var userId: String ?= null
)