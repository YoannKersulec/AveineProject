package com.aveine.project

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SparklingClass (
    val fr: String?,
    val en: String?
) : Parcelable

