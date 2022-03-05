package com.dj.login.presentation.viewModel

import app.cash.turbine.test
import com.dj.core.util.result.ResultState
import com.dj.login.domain.model.LoginRequest
import com.dj.login.domain.useCase.UseCaseLogin
import com.dj.login.presentation.state.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito.*

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: LoginViewModel

    @Mock
    private lateinit var useCase: UseCaseLogin

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = LoginViewModel(useCase)
        stubLoginUseCase()

    }

    private fun stubLoginUseCase() {
        `when`(
            useCase.login(
                LoginRequest(
                    "daniyaljavaid@gmail.com",
                    "Test@123"
                )
            )
        ).thenReturn(
            flow {
                emit(ResultState.Loading())
                delay(10)
                emit(ResultState.Success(message = "gg"))
            }
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `login with incorrect email id returns Failure UiState`() {
        viewModel.login("daniyaljavaidgmail.com", "Test@123")
        runTest {
            launch(Dispatchers.Main) {
                viewModel.loginState.test {
                    Assert.assertEquals(LoginState.Initial, awaitItem())
                    Assert.assertEquals(LoginState.Failure, awaitItem())
                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }

    @Test
    fun `login with incorrect password returns Failure UiState`() {
        viewModel.login("daniyaljavaid@gmail.com", "Test123")
        runTest {
            launch(Dispatchers.Main) {
                viewModel.loginState.test {
                    Assert.assertEquals(LoginState.Initial, awaitItem())
                    Assert.assertEquals(LoginState.Failure, awaitItem())
                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }

    @Test
    fun `login with correct password returns Success UiState`() {
        viewModel.login("daniyaljavaid@gmail.com", "Test@123")
        runTest {
            launch(Dispatchers.Main) {
                viewModel.loginState.test {
                    Assert.assertEquals(LoginState.Initial, awaitItem())
                    Assert.assertEquals(LoginState.Success, awaitItem())
                    cancelAndIgnoreRemainingEvents()
                }
            }
        }
    }

}