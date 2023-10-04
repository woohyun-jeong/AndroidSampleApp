package com.example.sampleapp.core.model

data class Sponsor(
    val name: String,
    val imageUrl: String,
    val homepage: String,
    val grade: Grade,
) {
    enum class Grade(val priority: Int) { PLATINUM(0), GOLD(1), }
}
