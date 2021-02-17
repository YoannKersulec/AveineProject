package com.aveine.project

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelationShipClass(
    val wine : BasicWineClass?
) : Parcelable