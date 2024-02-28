package com.example.recipetypes_test

data class RecipeData(
    val name: String,
    val type: String,
    val imageUrl: String,
    val ingredients: List<String>,
    val steps: List<String>
)