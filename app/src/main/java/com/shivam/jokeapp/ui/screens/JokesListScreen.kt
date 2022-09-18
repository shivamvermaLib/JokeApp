package com.shivam.jokeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shivam.jokeapp.R
import com.shivam.jokeapp.data.models.Joke
import com.shivam.jokeapp.ui.viewmodel.JokeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun JokesListScreen(
    jokeScreenViewModel: JokeScreenViewModel = hiltViewModel(),
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val uiState by jokeScreenViewModel.uiStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = "home") {
        jokeScreenViewModel.initiateFetching()
        launch {
            jokeScreenViewModel.uiStateFlow.collect {
                if (it.error != null) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.error,
                    )
                }
            }
        }
    }
    Scaffold(
        topBar = {
            JokesListTopBar()
        }
    ) {
        JokesList(items = uiState.list)
    }
}

@Composable
fun JokesListTopBar() {
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.jokes))
    })
}

@Composable
fun JokesList(items: List<Joke>) {
    LazyColumn {
        items(items) { joke ->
            JokeItem(joke = joke)
        }
    }
}

@Composable
fun JokeItem(joke: Joke) {
    Row(modifier = Modifier.padding(all = 10.dp)) {
        Text(text = joke.id.toString(),
            fontSize = 17.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(all = 2.dp)
                .size(30.dp)
                .background(
                    color = Color.Gray,
                    shape = CircleShape
                ),
            textAlign = TextAlign.Center)
        Text(text = joke.title,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1.0f))
    }
}