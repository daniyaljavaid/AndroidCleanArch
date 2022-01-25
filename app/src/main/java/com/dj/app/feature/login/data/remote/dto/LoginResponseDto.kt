package com.dj.app.feature.login.data.remote.dto

import com.dj.app.core.base.BaseResponse
import com.dj.app.core.user.data.remote.dto.UserDto

data class LoginResponseDto(val user: UserDto) : BaseResponse()