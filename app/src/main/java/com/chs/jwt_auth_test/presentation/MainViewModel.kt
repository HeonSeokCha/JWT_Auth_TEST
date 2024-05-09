package com.chs.jwt_auth_test.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.trace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.jwt_auth_test.common.ApiResult
import com.chs.jwt_auth_test.domain.GetUserInfoUseCase
import com.chs.jwt_auth_test.domain.RequestLoginUseCase
import com.chs.jwt_auth_test.domain.SetExpiredTokenUseCase
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
    private val requestUserInfoUseCase: GetUserInfoUseCase,
    private val setExpiredTokenUseCase: SetExpiredTokenUseCase
): ViewModel() {

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

            is FakeUiEvent.ExpiredScenario -> {
                setExpiredTokenInfo()
            }

            is FakeUiEvent.MultipleRequestScenario -> {
                getMultipleUserInfo()
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = requestLoginUseCase(
                userEmail = "john@mail.com",
                userPassword = "changeme"
            )

            state = state.copy(
                isLoading = false,
                loginState = result,
            )
        }
    }

    private fun getSingleUserInfo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = requestUserInfoUseCase()

            state = state.copy(
                isLoading = false,
                userState = result
            )
        }
    }

    private fun setExpiredTokenInfo() {
        viewModelScope.launch {
            setExpiredTokenUseCase()
        }
    }

    private fun getMultipleUserInfo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
//            val userA = async { requestUserInfoUseCase() }
//            val userB = async { requestUserInfoUseCase() }
//            val userC = async { requestUserInfoUseCase() }
//            val userD = async { requestUserInfoUseCase() }
//            val userE = async { requestUserInfoUseCase() }
//            val userF = async { requestUserInfoUseCase() }
//            val userG = async { requestUserInfoUseCase() }
//
//            awaitAll(
//                userA, userB, userC, userD, userE, userF, userG
//            ).forEach {
//                Log.e("CHS_LOG", it.toString())
//            }


            val userA = requestUserInfoUseCase().also {
                Log.e("CHS_LOG", it.toString())
            }
            val userB = requestUserInfoUseCase().also {
                Log.e("CHS_LOG", it.toString())
            }
            val userC = requestUserInfoUseCase().also {
                Log.e("CHS_LOG", it.toString())
            }
            val userD = requestUserInfoUseCase().also {
                Log.e("CHS_LOG", it.toString())
            }
            val userE = requestUserInfoUseCase().also {
                Log.e("CHS_LOG", it.toString())
            }
            val userF = requestUserInfoUseCase().also {
                Log.e("CHS_LOG", it.toString())
            }
            val userG = requestUserInfoUseCase().also {
                Log.e("CHS_LOG", it.toString())
            }
            state = state.copy(isLoading = false)
        }
    }
}