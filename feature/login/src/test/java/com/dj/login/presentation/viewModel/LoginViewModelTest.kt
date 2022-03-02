package com.dj.login.presentation.viewModel

import app.cash.turbine.test
import com.dj.login.domain.repository.LoginRepository
import com.dj.login.domain.useCase.UseCaseLogin
import com.dj.login.presentation.state.LoginState
import com.dj.login.util.LoginValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.mockito.Mockito.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    lateinit var viewModel: LoginViewModel
    lateinit var useCase: UseCaseLogin
    val validator = LoginValidator()
    lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        loginRepository = mock(LoginRepository::class.java)
        useCase = UseCaseLogin(validator, loginRepository)
        viewModel = LoginViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `login with incorrect email id returns Failure UiState`() {
        viewModel.login("daniyaljavaid95gmail.com", "Test@123")
        runTest {
            launch(Dispatchers.Main) {
                viewModel.loginState.test {
                    Assert.assertEquals(LoginState.Failure, awaitItem())
                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }

    @Test
    fun `login with incorrect password returns Failure UiState`() {
        viewModel.login("daniyaljavaid@95gmail.com", "Test123")
        runTest {
            launch(Dispatchers.Main) {
                viewModel.loginState.test {
                    Assert.assertEquals(LoginState.Failure, awaitItem())
                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }

}