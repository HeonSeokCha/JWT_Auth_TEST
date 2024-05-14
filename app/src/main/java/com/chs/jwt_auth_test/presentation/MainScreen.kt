package com.chs.jwt_auth_test.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chs.jwt_auth_test.common.ApiResult

@Composable
fun MainScreen(
    modifier: Modifier,
    state: FakeState,
    onEvent: (FakeUiEvent) -> Unit
) {
    
    var authState by remember { mutableStateOf("") }

    if (state.loginState != null) {
        authState = when (state.loginState) {
            is ApiResult.Authorized -> {
                "Your Are Authorized.."
            }
            is ApiResult.UnAuthorized -> {
                "Your Are UnAuthorized.."
            }
            is ApiResult.UnknownError -> {
                "Error : ${state.loginState.message}"
            }
        }
    }
    if (state.userState != null) {
        authState = when (state.userState) {
            is ApiResult.Authorized -> {
                "Your Are Authorized.."
            }

            is ApiResult.UnAuthorized -> {
                "Your Are UnAuthorized.."
            }

            is ApiResult.UnknownError -> {
                "Error : ${state.userState.message}"
            }
        }
    }
    
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = authState)
            
            Spacer(modifier = Modifier.height(32.dp))

            if (state.userState != null && state.userState is ApiResult.Authorized) {
                if (state.userState.data != null) {
                    Text(text = state.userState.data.name)
                }
            }
        }

        Button(
            onClick = {
                if (state.loginState != null && state.loginState is ApiResult.Authorized) {
                    onEvent(FakeUiEvent.RequestScenario)
                } else {
                    onEvent(FakeUiEvent.LogIn)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 200.dp)
        ) {
            if (state.loginState != null && state.loginState is ApiResult.Authorized) {
                Text("Get User Info.")
            } else {
                Text("LogIn.")
            }
        }

        if (state.userState != null && state.userState is ApiResult.Authorized) {
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    onEvent(FakeUiEvent.MultipleRequestScenario)
                }
            ) {
                Text(text = "Get Multiple User Info.")
            }

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    onEvent(FakeUiEvent.ExpiredScenario)
                }
            ) {
                Text(text = "Let's break Token.")
            }
        }
    }


    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}