package com.aveine.project

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CallController : Callback<TokenClass> {

    var aveineService : AveineService? = null

    var token : String? = null

    fun start() {
        val gson = GsonBuilder()
                .setLenient()
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl(AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        aveineService = retrofit.create(AveineService::class.java)
        auth()
    }

    fun auth() {
        val body = AuthClass(
            client_id = "nLk99G06VJIJQj7ZWYjxlhPOcRDk3LsC",
            client_secret = "g5FFoqeO9vwcy8QfyC-W_pJjkNqPpjbsMaAJ84KjYo_6Vl8SFpJ8mB5crMGgzAz0",
            audience = "api.aveine.paris",
            grant_type = "client_credentials"
        )
        val call: Call<TokenClass>? = aveineService?.auth(body)
        call?.enqueue(this)
    }

    fun changeUrlAndService() {
        val gson = GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        aveineService = retrofit.create(AveineService::class.java)
    }

    override fun onResponse(call: Call<TokenClass>?, response: Response<TokenClass>) {
        if (response.isSuccessful) {
            val body = response.body()
            changeUrlAndService()
            token = body?.access_token
        } else {
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdmVpbmUucGFyaXMiLCJpYXQiOjE2MTM0NjU4MTksImV4cCI6MTYxNjU1MjIxOSwiYXVkIjoiYXBpLmF2ZWluZS5wYXJpcyIsInN1YiI6ImI2NjZkMWU2LWZlMWUtNGQyZi1iYTYwLWM2ODEzMDBjNzc5ZiJ9.IHAeSvZ933L8E-q8Go5mqBXpjMAIdJb9iRIHwH8_VYo"
            // TODO Need to fix that
            changeUrlAndService()
            System.out.println(response.errorBody())
        }
    }

    override fun onFailure(call: Call<TokenClass>?, t: Throwable) {
        t.printStackTrace()
    }

    companion object {
        const val AUTH_URL = "https://aveine.eu.auth0.com/"
        const val BASE_URL = "https://v2.consumer.api.aveine.paris/"
    }
}