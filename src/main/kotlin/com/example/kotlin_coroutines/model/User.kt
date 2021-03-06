package com.example.kotlin_coroutines.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(value = "user")
data class User(
    @Id
    var userId: String ?= null,
    var userName: String ?= null,
    var firstName: String ?= null,
    var lastName: String ?= null
)