package com.aveine.project

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegionClass(
    val id: String? = null,
    val name: NameClass? = null,
    val country : CountryClass? = null
) : Parcelable