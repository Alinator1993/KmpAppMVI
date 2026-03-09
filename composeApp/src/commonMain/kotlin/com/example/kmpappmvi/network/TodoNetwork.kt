package com.example.kmpappmvi.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object TodoNetwork {
    val networkClient = HttpClient{
        install(ContentNegotiation){
            json(
                Json{
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}