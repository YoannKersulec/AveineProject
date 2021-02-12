package com.aveine.project

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.function.Consumer


class CallController : Callback<TokenClass> {

    fun start() {
        val gson = GsonBuilder()
                .setLenient()
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val aveineAPI: AveineOauthService = retrofit.create(AveineOauthService::class.java)
        val body = AuthClass(
            client_id = "nLk99G06VJIJQj7ZWYjxlhPOcRDk3LsC",
            client_secret = "g5FFoqeO9vwcy8QfyCW_pJjkNqPpjbsMaAJ84KjYo_6Vl8SFpJ8mB5crMGgzAz0",
                audience = "api.aveine.paris",
                grant_type = "client_credentials"
        )
        val call: Call<TokenClass> = aveineAPI.auth(body)
        call.enqueue(this)
    }

    override fun onResponse(call: Call<TokenClass>?, response: Response<TokenClass>) {
        if (response.isSuccessful()) {
            val body = response.body()
            val oui = 1
//            val changesList: List<Change> = response.body()
//            changesList.forEach(Consumer<Change> { change: Change -> System.out.println(change.subject) })
        } else {
            System.out.println(response.errorBody())
        }
    }

    override fun onFailure(call: Call<TokenClass>?, t: Throwable) {
        t.printStackTrace()
    }

    companion object {
        const val BASE_URL = "https://v2.consumer.api.aveine.paris/"
    }
}