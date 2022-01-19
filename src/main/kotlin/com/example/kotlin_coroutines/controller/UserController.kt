package com.example.kotlin_coroutines.controller

import com.example.kotlin_coroutines.model.User
import com.example.kotlin_coroutines.request.SearchRequest
import com.example.kotlin_coroutines.service.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    val userService: UserService
) {

    @PostMapping("/save")
    suspend fun saveUser(@RequestBody user: Mono<User>):User?{
        val userRequest = user.awaitSingle()
        return userService.saveUser(userRequest)
    }

    @PostMapping("/get")
    suspend fun getUser(@RequestBody searchRequest: SearchRequest):Flow<User>{
        return userService.find(searchRequest)
    }

}