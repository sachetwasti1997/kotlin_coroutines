package com.example.kotlin_coroutines.exceptionHandler

import com.example.kotlin_coroutines.exceptions.UserNotFoundException
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalExceptionHandler: ErrorWebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val dataFactory = exchange.response.bufferFactory()
        val errorResponse: ErrorResponse
        if (ex is UserNotFoundException){
            exchange.response.statusCode = HttpStatus.NOT_FOUND
            errorResponse = ErrorResponse(ex.message, HttpStatus.NOT_FOUND)
        }else{
            exchange.response.statusCode = HttpStatus.NOT_FOUND
            errorResponse = ErrorResponse(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        val errorMessage = dataFactory.wrap(errorResponse.toString().toByteArray())
        return exchange.response.writeWith (Mono.just(errorMessage))
    }
}