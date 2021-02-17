package com.aveine.project

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryClass(
    val id : String? = null,
    val code : String? = null,
    val name : NameClass? = null
) : Parcelable