package com.avalith.seminariokotlin.model

data class Weather (
    val location: Location,
    val current: Current
)

data class Location(
    val name: String
)

data class Current(
    val temp_c: Float,
    val feelslike_c: Float,
    val condition: Condition
)

data class Condition(
    val icon: String
)