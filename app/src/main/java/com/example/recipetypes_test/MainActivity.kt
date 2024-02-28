package com.example.recipetypes_test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var addButton: Button
    private lateinit var listView: ListView
    private lateinit var adapter: CustomArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spinner)
        addButton = findViewById(R.id.addButton)
        listView = findViewById(R.id.listView)

        // Adapter for the ListView with custom item layout
        adapter = CustomArrayAdapter(this, mutableListOf())
        listView.adapter = adapter

        // Use the existing parseXml function to populate the Spinner from recipetypes.xml
        val recipeTypes = parseXml(resources.openRawResource(R.raw.recipetypes))
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, recipeTypes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        // Set up the button click listener to add items to the ListView
        addButton.setOnClickListener {
            val selectedCategory = spinner.selectedItem.toString()
            val newItem = "$selectedCategory - Item ${adapter.count + 1}"
            adapter.add(newItem)
            adapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val itemText = adapter.getItem(position)
            // Handle click event for the entire item (TextView + Button)
            // You can perform additional actions or show a confirmation dialog here
        }

        adapter.setOnDeleteButtonClickListener(object : CustomArrayAdapter.OnDeleteButtonClickListener {
            override fun onDeleteButtonClick(position: Int) {
                adapter.remove(adapter.getItem(position))
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun parseXml(inputStream: InputStream): List<String> {
        val recipeTypes = mutableListOf<String>()

        try {
            val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser: XmlPullParser = factory.newPullParser()

            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            var currentRecipeType = ""

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.name == "type") {
                            currentRecipeType = parser.nextText()
                            recipeTypes.add(currentRecipeType)
                        } else if (parser.name == "recipe") {
                            val recipe = parser.nextText()
                            recipeTypes.add("$currentRecipeType - $recipe")
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
        }

        return recipeTypes
    }
}
