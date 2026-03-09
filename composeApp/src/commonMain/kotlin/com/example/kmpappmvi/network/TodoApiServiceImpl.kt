package com.example.kmpappmvi.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
const val BASE_URL = "https://jsonplaceholder.typicode.com/"


class TodoApiServiceImpl(private val client: HttpClient) : TodoApiService {
    override suspend fun getTodos(): Response<List<TodoDTO>> {

        try {
            val todos = client.get("${BASE_URL}todos") {
                accept(ContentType.Application.Json)
            }
            println(todos.body<List<TodoDTO>>().toString())
            return Response.Success(todos.body() as List<TodoDTO>)
        }
        catch(e:Exception) {
            return Response.Error(e.message.toString())
        }
    }
}