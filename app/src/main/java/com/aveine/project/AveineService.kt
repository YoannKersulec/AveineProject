package com.aveine.project

import retrofit2.Call
import retrofit2.http.*

interface AveineService {
    @Headers("Content-Type: application/json")
    @POST("/oauth/token")
    fun auth(@Body body: AuthClass) : Call<TokenClass>

    @Headers(
        "Content-Type: application/json")
        @GET("/users/self/favorites")
        fun getFavorites(@Header("Authorization") authHeader : String) : Call<FavoriteWineListClass>
}