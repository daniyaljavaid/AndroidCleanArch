package com.dj.login.data.repositoryImpl

import app.cash.turbine.test
import com.dj.core.user.data.remote.dto.UserDto
import com.dj.core.user.domain.model.User
import com.dj.core.util.result.ResultState
import com.dj.login.data.remote.dto.LoginResponseDto
import com.dj.login.data.remote.service.LoginService
import com.dj.login.domain.model.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryImplTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    private lateinit var loginService: LoginService
    private lateinit var loginRepository: LoginRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)

        loginRepository = LoginRepositoryImpl(loginService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    private suspend fun stubLoginService(loginRequest: LoginRequest, resp: LoginResponseDto) {
        Mockito.`when`(loginService.login(loginRequest.toDto())).thenReturn(resp)
    }

    @Test
    fun `correct email & password returns user object`() {
        val request = LoginRequest("daniyal@gmail.com", "Test@123")
        val mockResp = Mockito.mock(LoginResponseDto::class.java)
        val mockUser = Mockito.mock(User::class.java)
        val mockUserDto = Mockito.mock(UserDto::class.java)

        Mockito.`when`(mockUser.id).thenReturn("111-222")
        Mockito.`when`(mockResp.user).thenReturn(mockUserDto)
        Mockito.`when`(mockUserDto.toUser()).thenReturn(mockUser)
        Mockito.`when`(mockResp.isSuccessful).thenReturn(true)
        Mockito.`when`(mockResp.message).thenReturn("Login Success")

        runTest {
            launch(Dispatchers.Main) {
                stubLoginService(request, mockResp)
                loginRepository.submitLogin(request).test {
                    assertTrue(awaitItem() is ResultState.Loading)
                    val successResultState = awaitItem()
                    assertTrue(successResultState is ResultState.Success)
                    assertEquals("111-222", successResultState.data?.id)

                    cancelAndConsumeRemainingEvents()

                }

            }
        }
    }

    @Test
    fun `incorrect email or password returns error message`() {
        val request = LoginRequest("da@gm.com", "Test@123")
        val mockResp = Mockito.mock(LoginResponseDto::class.java)

        Mockito.`when`(mockResp.isSuccessful).thenReturn(false)
        Mockito.`when`(mockResp.message).thenReturn("Incorrect email or password")

        runTest {
            launch(Dispatchers.Main) {
                stubLoginService(request, mockResp)
                loginRepository.submitLogin(request).test {
                    assertTrue(awaitItem() is ResultState.Loading)
                    assertTrue(awaitItem() is ResultState.Error)

                    cancelAndConsumeRemainingEvents()

                }

            }
        }
    }

    @Test
    fun `service throws exception`() {
        val request = LoginRequest("da@gm.com", "Test@123")

        runTest {
            launch(Dispatchers.Main) {
                Mockito.`when`(loginService.login(request.toDto())).thenThrow(
                    HttpException(
                        Response.error<Any>(
                            500,
                            "Test Server Error".toResponseBody("text/plain".toMediaType())
                        )
                    )
                )

                loginRepository.submitLogin(request).test {
                    assertTrue(awaitItem() is ResultState.Loading)
                    val errorState = awaitItem()
                    assertTrue(errorState is ResultState.Error)
                    println(errorState.message)
                    cancelAndConsumeRemainingEvents()

                }

            }
        }
    }
}