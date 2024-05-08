package com.chs.jwt_auth_test.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chs.jwt_auth_test.common.ApiResult
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
    modifier: Modifier,
    state: FakeState,
    onEvent: (FakeUiEvent) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {

        Button(
            onClick = {
                onEvent(FakeUiEvent.LogIn)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("LogIn")
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