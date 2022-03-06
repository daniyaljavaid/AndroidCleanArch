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
        stubLoginRepository(request, flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(data = mock(User::class.java), message = "Login Successful"))
        })
        runTest {
            launch(Dispatchers.Main) {
                useCase.login(request).test {
                    assertTrue(awaitItem() is ResultState.Loading)
                    val successResultState = awaitItem()
                    assertTrue(successResultState is ResultState.Success)
                    assertTrue(successResultState.message == "Login Successful")
                    assertTrue(successResultState.data is User)

                    cancelAndConsumeRemainingEvents()
                }
            }
        }
    }

//    @Test
//    fun `incorrect email password emits Error`() {
//        val request = LoginRequest("daniyalgmail.com", "Test@123")
//        stubLoginRepository(request, flow {
//
//        })
//        runTest {
//            launch(Dispatchers.Main) {
//                useCase.login(request).test {
//
//                }
//            }
//        }
//    }
}