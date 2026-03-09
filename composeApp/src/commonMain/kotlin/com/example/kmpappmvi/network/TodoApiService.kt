package com.example.kmpappmvi.network

import com.example.kmpappmvi.network.TodoDTO

interface TodoApiService {
    suspend fun getTodos(): Response<List<TodoDTO>>
}