package com.aveine.project

import com.google.gson.annotations.SerializedName
import java.util.*

data class AttributeFavoriteClass(
    @SerializedName("created-at")
    val created_at : Date?,

    @SerializedName("updated-at")
    val updated_at : Date?,
    val deleted : Boolean?
)