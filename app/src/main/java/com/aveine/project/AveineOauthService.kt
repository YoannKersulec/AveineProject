package com.aveine.project

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AveineOauthService {
    @Headers("Content-Type: application/json")
    @POST("/oauth/token")
    fun auth(@Body body: AuthClass) : Call<TokenClass>
}