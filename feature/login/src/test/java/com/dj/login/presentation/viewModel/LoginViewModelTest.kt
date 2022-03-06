package com.dj.login.presentation.viewModel

import app.cash.turbine.test
import com.dj.core.user.domain.model.User
import com.dj.core.util.result.ResultState
import com.dj.login.domain.model.LoginRequest
import com.dj.login.domain.useCase.UseCaseLogin
import com.dj.login.presentation.state.LoginState
import com.dj.login.util.LoginValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    private fun stubLoginUseCase(request: LoginRequest, flow: Flow<ResultState<User>>) {
        `when`(useCase.login(request)).thenReturn(flow)
    }

    @Test
    fun `login with incorrect email id returns Failure UiState`() {
        val email = "daniyaljavaidgmail.com"
        val pass = "Test@123"
        stubLoginUseCase(LoginRequest(email, pass), flow {
            emit(ResultState.Error(message = "Invalid Email"))
        })
        viewModel.login(email, pass)
        runTest {
            launch(Dispatchers.Main) {
                viewModel.loginState.test {
                    Assert.assertEquals(LoginState.Initial, awaitItem())
                    val failureState = awaitItem()
                    Assert.assertEquals(true, failureState is LoginState.Failure)
                    Assert.assertEquals("Invalid Email", failureState.message)
                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }

    @Test
    fun `login with incorrect password returns Failure UiState`() {
        val email = "daniyaljavaid@gmail.com"
        val pass = "Test123"
        stubLoginUseCase(LoginRequest(email, pass), flow {
            emit(ResultState.Error(message = "Invalid Password"))
        })
        runTest {
            launch(Dispatchers.Main) {
                viewModel.loginState.test {
                    Assert.assertEquals(LoginState.Initial, awaitItem())
                    val failureState = awaitItem()
                    Assert.assertEquals(true, failureState is LoginState.Failure)
                    Assert.assertEquals("Invalid Password", failureState.message)
                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }

    @Test
    fun `login with correct email & password returns Success LoginState`() {
        val email = "daniyaljavaid@gmail.com"
        val pass = "Test@123"
        stubLoginUseCase(LoginRequest(email, pass), flow {
            emit(ResultState.Loading())
            delay(1000)
            emit(ResultState.Success(message = "Login Success"))
        })
        viewModel.login(email, pass)
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