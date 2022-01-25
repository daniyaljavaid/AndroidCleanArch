package com.dj.app.core.user.data.remote.dto.refreshToken

import com.dj.app.core.base.BaseResponse

data class RefreshTokenResponseDto(val token: String, val refreshToken: String) : BaseResponse()