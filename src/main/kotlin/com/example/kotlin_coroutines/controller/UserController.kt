package com.example.kotlin_coroutines.controller

import com.example.kotlin_coroutines.model.User
import com.example.kotlin_coroutines.service.UserService
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

}