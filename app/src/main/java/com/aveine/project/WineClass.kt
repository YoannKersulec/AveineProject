package com.aveine.project

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class WineClass(
    val id : String? = null,
    val designation : String? = null,
    val color : ColorClass? = null,
    val wine_label : String? = null,
    val vintage : Int? = null,
    val recommended_aeration : Int? = null,
    val region : RegionClass? = null,
    val country : CountryClass? = null,
    val serving_temperature_min : Int? = null,
    val serving_temperature_max : Int? = null,
) : Parcelable {

    fun createFromJson(json : String) : WineClass {
        val gson = Gson()
        return gson.fromJson(json, WineClass::class.java)
    }

}