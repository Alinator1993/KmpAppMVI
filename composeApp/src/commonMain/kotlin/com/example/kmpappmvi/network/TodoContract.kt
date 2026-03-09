package com.example.kmpappmvi.network

import com.example.kmpappmvi.network.TodoDTO

sealed class TodoIntent {
    data object LoadTodos : TodoIntent()
}

sealed class TodoState {
    data object Nothing : TodoState()
    data object Loading : TodoState()
    data class Error(val msg: String) : TodoState()
    data class Success(val todos: List<TodoDTO>) : TodoState()
}

sealed class TodoEffect {
    data class ShowError(val msg: String) : TodoEffect()
}