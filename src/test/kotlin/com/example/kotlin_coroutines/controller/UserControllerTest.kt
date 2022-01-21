package com.example.kotlin_coroutines.controller

import com.example.kotlin_coroutines.model.User
import com.example.kotlin_coroutines.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class UserControllerTest
@Autowired
constructor(
    val userRepository: UserRepository,
    val webTestClient: WebTestClient
) {

    val id = UUID.randomUUID().toString()

    @BeforeEach
    fun setUp() {

        runBlocking {
            withContext(Dispatchers.IO) {
                val userList = listOf<User>(
                    User(
                        userId = id,
                        userName = "auto1221",
                        firstName = "Auto",
                        lastName = "1221"
                    ),
                    User(
                        userName = "anildeb",
                        firstName = "Anil",
                        lastName = "Deb"
                    ),
                    User(
                        userName = "umadevi",
                        firstName = "Uma",
                        lastName = "Devi"
                    ),
                    User(
                        userName = "raviranjan",
                        firstName = "Ravi",
                        lastName = "Ranjan"
                    )
                )
                userRepository.saveAll(userList).blockFirst()
            }
        }

    }

    @AfterEach
    fun tearDown() {
        runBlocking {
            withContext(Dispatchers.IO) {
                userRepository.deleteAll().block()
            }
        }
    }

    @Test
    fun saveUser() {

        val user = User(
            userName = "randomUser",
            firstName = "random",
            lastName = "User"
        )

        webTestClient
            .post()
            .uri("/api/v1/user/save")
            .bodyValue(user)
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(User::class.java)
            .consumeWith {
                val savedUser = it.responseBody
                Assertions.assertNotNull(savedUser?.userId)
            }

    }

    @Test
    fun findUserById() {
        webTestClient
            .get()
            .uri {
                it
                    .path("/api/v1/user/$id")
                    .build()
            }
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(User::class.java)
            .consumeWith {
                val user = it.responseBody
                Assertions.assertEquals(user?.userId, id)
            }
    }

    @Test
    fun findUserByIdNotFound(){

        val id = UUID.randomUUID().toString()

        webTestClient
            .get()
            .uri("/api/v1/user/$id")
            .exchange()
            .expectStatus()
            .isNotFound
            .expectBody(String::class.java)
            .consumeWith {
                val message = it.responseBody
                println(message)
            }
    }

    @Test
    fun getAllUsers() {
            webTestClient
                .get()
                .uri("/api/v1/user/all")
                .exchange()
                .expectStatus()
                .isOk
                .expectBodyList(User::class.java)
                .consumeWith<WebTestClient.ListBodySpec<User>> {
                    val userList = it.responseBody
                    Assertions.assertEquals(userList?.size, 4)
                }
    }
}