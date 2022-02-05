package com.dj.core.user.data.remote.dto.refreshToken

import com.dj.core.base.BaseResponse

data class RefreshTokenResponseDto(val token: String, val refreshToken: String) : BaseResponse()