package com.aveine.project

import com.google.gson.annotations.SerializedName

data class WineGetClass(
    val data : WineGetInfoClass? = null
) {
    fun createWineClass() : WineClass {
        val attributes = this.data?.attributes
        return WineClass(
            id = this.data?.id,
            color = ColorClass(en = attributes?.color, fr = null),
            designation = attributes?.designation,
            vintage = attributes?.vintage,
            recommended_aeration = attributes?.recommended_aeration,
            serving_temperature_min = attributes?.serving_temperature_min,
            serving_temperature_max = attributes?.serving_temperature_max
        )
    }
}

data class WineGetInfoClass(
    val id : String? = null,
    val attributes : Attributes? = null
)

data class Attributes(
    val designation : String? = null,
    val color : String? = null,
    val wine_label : String? = null,
    val vintage : Int? = null,
    @SerializedName("recommended-aeration")
    val recommended_aeration : Int? = null,
    @SerializedName("serving-temperature-min")
    val serving_temperature_min : Int? = null,
    @SerializedName("serving-temperature-max")
    val serving_temperature_max : Int? = null
)

