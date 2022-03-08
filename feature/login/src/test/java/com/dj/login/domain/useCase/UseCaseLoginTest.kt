package com.dj.login.domain.useCase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.dj.core.user.domain.model.User
import com.dj.core.util.result.ResultState
import com.dj.login.MainCoroutineScopeRule
import com.dj.login.domain.model.LoginRequest
import com.dj.login.domain.repository.LoginRepository
import com.dj.login.util.LoginValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UseCaseLoginTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private lateinit var useCase: UseCaseLogin

    @Mock
    private lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = UseCaseLogin(LoginValidator(), loginRepository)
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
        coroutineScope.runTest {
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

    @Test
    //server emits error for wrong email/pass
    fun `incorrect email password emits Error`() {
        val request = LoginRequest("incorrect@email.com", "Wrong@Pass123")
        stubLoginRepository(request, flow {
            emit(ResultState.Loading())
            emit(ResultState.Error(message = "Incorrect email or password"))
        })
        coroutineScope.runTest {
            useCase.login(request).test {
                assertTrue(awaitItem() is ResultState.Loading)
                assertTrue(awaitItem() is ResultState.Error)

                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `invalid email emits Error`() {
        val request = LoginRequest("daniyalgmail.com", "Test@123")
        // no stubbing required because invalid email doesnot proceed to call loginRepo function
        coroutineScope.runTest {
            useCase.login(request).test {
                val errorState = awaitItem()
                assertTrue(errorState is ResultState.Error)
                assertTrue(errorState.message == "Invalid Email")

                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `invalid password emits Error`() {
        val request = LoginRequest("daniyal@gmail.com", "Test123")
        // no stubbing required because invalid email doesnot proceed to call loginRepo function
        coroutineScope.runTest {
            useCase.login(request).test {
                val errorState = awaitItem()
                assertTrue(errorState is ResultState.Error)
                assertTrue(errorState.message == "Invalid Password")

                cancelAndConsumeRemainingEvents()
            }
        }
    }
}