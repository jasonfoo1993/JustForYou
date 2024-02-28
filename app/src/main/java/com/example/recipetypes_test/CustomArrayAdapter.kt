package com.example.recipetypes_test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class CustomArrayAdapter(context: Context, items: MutableList<String>) : ArrayAdapter<String>(context, 0, items) {

    private var onDeleteButtonClickListener: OnDeleteButtonClickListener? = null

    interface OnDeleteButtonClickListener {
        fun onDeleteButtonClick(position: Int)
    }

    fun setOnDeleteButtonClickListener(listener: OnDeleteButtonClickListener) {
        onDeleteButtonClickListener = listener
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)
        }

        val itemText = getItem(position)
        val textView = itemView!!.findViewById<TextView>(R.id.listItemTextView)
        textView.text = itemText

        val deleteButton = itemView.findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            onDeleteButtonClickListener?.onDeleteButtonClick(position)
        }

        return itemView
    }
}