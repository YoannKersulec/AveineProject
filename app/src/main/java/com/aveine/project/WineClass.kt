package com.aveine.project

data class WineClass(
    val type: String,
    val attributes: Attribute
)

data class Attribute(
    val id : String?,
    val designation : String?,
    val color : String?
)