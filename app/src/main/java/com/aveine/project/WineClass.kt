package com.aveine.project

import com.google.gson.Gson
import kotlinx.serialization.Serializable

@Serializable
data class WineClass(
    val id : String? = null,
    val designation : String? = null,
    val color : ColorClass? = null,
    val wine_label : String? = null
) {
    fun createFromJson(json : String) : WineClass {
        val gson = Gson()
        return gson.fromJson(json, WineClass::class.java)
    }
}