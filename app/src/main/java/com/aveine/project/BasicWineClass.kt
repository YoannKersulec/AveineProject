package com.aveine.project

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicWineClass(
    val data : BasicInfoClass? = null
) : Parcelable

@Parcelize
data class BasicInfoClass(
    val id : String? = null,
    val type : String? = null
) : Parcelable