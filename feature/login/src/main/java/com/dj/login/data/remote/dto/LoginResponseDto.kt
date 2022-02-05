package com.dj.login.data.remote.dto

import com.dj.core.base.BaseResponse
import com.dj.core.user.data.remote.dto.UserDto


data class LoginResponseDto(val user: UserDto) : BaseResponse()