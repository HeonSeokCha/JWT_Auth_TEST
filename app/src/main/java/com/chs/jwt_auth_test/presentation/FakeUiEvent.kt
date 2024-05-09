package com.chs.jwt_auth_test.presentation

sealed class FakeUiEvent {

    data object LogIn : FakeUiEvent()
    data object RequestScenario : FakeUiEvent()
    data object ExpiredScenario : FakeUiEvent()
    data object MultipleRequestScenario : FakeUiEvent()
}