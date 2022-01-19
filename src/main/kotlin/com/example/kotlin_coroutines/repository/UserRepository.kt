package com.example.kotlin_coroutines.repository

import com.example.kotlin_coroutines.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.allAndAwait
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    val reactiveMongoTemplate: ReactiveMongoTemplate
) {

    suspend fun saveUser(user: User):User?{
        return reactiveMongoTemplate.save(user).awaitSingleOrNull()
    }

    suspend fun find(query: Query): Flow<User> {
        return reactiveMongoTemplate.find(query, User::class.java).asFlow()
    }

    suspend fun deleteAll() = reactiveMongoTemplate.remove(User::class.java).allAndAwait()

    suspend fun delete(query: Query) = reactiveMongoTemplate.remove(query, User::class.java).awaitSingleOrNull()

}