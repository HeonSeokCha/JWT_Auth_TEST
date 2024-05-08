package com.chs.jwt_auth_test.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.trace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.jwt_auth_test.common.ApiResult
import com.chs.jwt_auth_test.domain.GetUserInfoUseCase
import com.chs.jwt_auth_test.domain.RequestLoginUseCase
import com.chs.jwt_auth_test.domain.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MainViewModel @Inject constructor(
    private val requestLoginUseCase: RequestLoginUseCase,
    private val requestUserInfoUseCase: GetUserInfoUseCase
): ViewModel() {

    private var _loginState: MutableStateFlow<ApiResult<Unit>> = MutableStateFlow(ApiResult.UnAuthorized())
    val loginState = _loginState.asStateFlow()

    private var _singleUserState: MutableStateFlow<ApiResult<UserInfo>> = MutableStateFlow(ApiResult.UnAuthorized())
    val singleUserState = _singleUserState.asStateFlow()


    var state: FakeState by mutableStateOf(FakeState())
        private set

    fun onEvent(event: FakeUiEvent) {
        when (event) {
            is FakeUiEvent.LogIn -> {
                login()
            }

            is FakeUiEvent.RequestScenario -> {
                getSingleUserInfo()
            }

            is FakeUiEvent.MultipleRequestScenario -> {
                getMultipleUserInfo()
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            _loginState.update {
                requestLoginUseCase(
                    userEmail = "john@mail.com",
                    userPassword = "changeme"
                )
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun getSingleUserInfo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            _singleUserState.update {
                requestUserInfoUseCase(false)
            }
        }
    }

    private fun getMultipleUserInfo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val userA = async { requestUserInfoUseCase(false) }
            val userB = async { requestUserInfoUseCase(true) }
            val userC = async { requestUserInfoUseCase(true) }
            val userD = async { requestUserInfoUseCase(true) }
            val userE = async { requestUserInfoUseCase(true) }
            val userF = async { requestUserInfoUseCase(true) }
            val userG = async { requestUserInfoUseCase(true) }

            val list = awaitAll(
                userA, userB, userC, userD, userE, userF, userG
            )

            state = state.copy(
                isLoading = false,
                anotherUsersInfo = list
            )
        }
    }
}