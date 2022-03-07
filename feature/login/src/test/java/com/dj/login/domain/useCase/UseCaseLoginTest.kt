package com.dj.login.domain.useCase

import app.cash.turbine.test
import com.dj.core.user.domain.model.User
import com.dj.core.util.result.ResultState
import com.dj.login.domain.model.LoginRequest
import com.dj.login.domain.repository.LoginRepository
import com.dj.login.util.LoginValidator
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UseCaseLoginTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var useCase: UseCaseLogin

    @Mock
    private lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)

        useCase = UseCaseLogin(LoginValidator(), loginRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    private fun stubLoginRepository(loginRequest: LoginRequest, flow: Flow<ResultState<User>>) {
        Mockito.`when`(loginRepository.submitLogin(loginRequest)).thenReturn(flow)
    }

    @Test
    fun `correct email password emits User object`() {
        val request = LoginRequest("daniyal@gmail.com", "Test@123")
        val mockUser = mock(User::class.java)
        Mockito.`when`(mockUser.id).thenReturn("111-222")
        stubLoginRepository(request, flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(data = mockUser, message = "Login Successful"))
        })
        runTest {
            launch(Dispatchers.Main) {
                useCase.login(request).test {
                    assertTrue(awaitItem() is ResultState.Loading)
                    val successResultState = awaitItem()
                    assertTrue(successResultState is ResultState.Success)
                    assertEquals("Login Successful", successResultState.message)
                    assertEquals("111-222", successResultState.data?.id)

                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }

    @Test
    //server emits error for wrong email/pass
    fun `incorrect email password emits Error`() {
        val request = LoginRequest("incorrect@email.com", "Wrong@Pass123")
        stubLoginRepository(request, flow {
            emit(ResultState.Loading())
            emit(ResultState.Error(message = "Incorrect email or password"))
        })
        runTest {
            launch(Dispatchers.Main) {
                useCase.login(request).test {
                    assertTrue(awaitItem() is ResultState.Loading)
                    assertTrue(awaitItem() is ResultState.Error)

                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }

    @Test
    fun `invalid email emits Error`() {
        val request = LoginRequest("daniyalgmail.com", "Test@123")
        // no stubbing required because invalid email doesnot proceed to call loginRepo function
        runTest {
            launch(Dispatchers.Main) {
                useCase.login(request).test {
                    assertTrue(awaitItem() is ResultState.Error)

                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }

    @Test
    fun `invalid password emits Error`() {
        val request = LoginRequest("daniyal@gmail.com", "Test123")
        // no stubbing required because invalid email doesnot proceed to call loginRepo function
        runTest {
            launch(Dispatchers.Main) {
                useCase.login(request).test {
                    assertTrue(awaitItem() is ResultState.Error)

                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }
}