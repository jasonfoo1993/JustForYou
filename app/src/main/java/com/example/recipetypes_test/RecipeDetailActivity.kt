package com.example.recipetypes_test

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipeDetailTextView: TextView = findViewById(R.id.recipeDetailTextView)
        val recipeNameTextView: TextView = findViewById(R.id.recipeNameTextView)
        val recipeTypeTextView: TextView = findViewById(R.id.recipeTypeTextView)
        val ingredientsTextView: TextView = findViewById(R.id.ingredientsTextView)
        val stepsTextView: TextView = findViewById(R.id.stepsTextView)
        val editRecipeButton: Button = findViewById(R.id.editRecipeButton)

        // Retrieve the selected recipe from Intent or ViewModel
        val selectedRecipe = intent.getSerializableExtra("recipe") as RecipeData

        // Bind data to views
        recipeDetailTextView.text = "Your Text Here" // Set the desired text
        recipeNameTextView.text = selectedRecipe.name
        recipeTypeTextView.text = selectedRecipe.type
        ingredientsTextView.text = selectedRecipe.ingredients.joinToString("\n")
        stepsTextView.text = selectedRecipe.steps.joinToString("\n")

        // Button click listener for editing the recipe
        editRecipeButton.setOnClickListener {
            // Implement the logic to navigate to the EditRecipeActivity or show an edit dialog
            // You might need to pass the selectedRecipe to the edit screen
        }
    }
}