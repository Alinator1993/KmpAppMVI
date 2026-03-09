package com.example.kmpappmvi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmpappmvi.network.*
import kotlinx.coroutines.flow.collectLatest

@Composable
@Preview
fun App() {
    MaterialTheme {
        val todoViewModel = viewModel<TodoViewModel>(factory = todoModelFactory)
        val state by todoViewModel.state.collectAsStateWithLifecycle()
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(Unit) {
            todoViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is TodoEffect.ShowError -> snackbarHostState.showSnackbar(effect.msg)
                }
            }
        }

        Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(getPlatformName())
                Button(onClick = {
                    todoViewModel.processIntent(TodoIntent.LoadTodos)
                }) {
                    Text("Click me!")
                }

                when (state) {
                    is TodoState.Error -> Text("Some Error Occurred: ${(state as TodoState.Error).msg}")
                    TodoState.Loading -> Text("Fetching Todos.....")
                    TodoState.Nothing -> Text("Click on load to fetch Todos")
                    is TodoState.Success -> {
                        LazyColumn(
                            Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items((state as TodoState.Success).todos) { todo ->
                                Card(Modifier.fillMaxWidth()) {
                                    Column(Modifier.fillMaxWidth().padding(12.dp)) {
                                        Text("Title ${todo.title}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}