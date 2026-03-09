package com.example.kmpappmvi.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kmpappmvi.network.TodoDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    private val apiService = TodoApiServiceImpl(TodoNetwork.networkClient)

    private val _state = MutableStateFlow<TodoState>(TodoState.Nothing)
    val state get() = _state.asStateFlow()

    private val _effect = Channel<TodoEffect>(Channel.BUFFERED)
    val effect get() = _effect.receiveAsFlow()

    fun processIntent(intent: TodoIntent) {
        when (intent) {
            is TodoIntent.LoadTodos -> fetchTodos()
        }
    }

    private fun fetchTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { TodoState.Loading }
            when (val response = apiService.getTodos()) {
                is Response.Error -> {
                    _state.update { TodoState.Error(response.message) }
                    _effect.send(TodoEffect.ShowError(response.message))
                }
                is Response.Success<*> -> {
                    _state.update {
                        TodoState.Success(response.result as List<TodoDTO>)
                    }
                }
            }
        }
    }
}

val todoModelFactory = viewModelFactory {
    initializer { TodoViewModel() }
}