package com.example.pam.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pam.R
import com.example.pam.dto.MessageDTO

class MessageAdapter(context:Context, private val dataSource: ArrayList<MessageDTO>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
      return  dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_recipe, parent, false)

        // Get title element
        val titleTextView = rowView.findViewById(R.id.recipe_list_title) as TextView

        // Get subtitle element
        val detailsTextView = rowView.findViewById(R.id.recipe_list_subtitle) as TextView

        // Get detail element
        val authorTextView = rowView.findViewById(R.id.recipe_list_detail) as TextView

        // 1
        val message = getItem(position) as MessageDTO

// 2
        titleTextView.text = message.title
        detailsTextView.text = message.contents
        authorTextView.text = message.author
        
        return rowView
    }
}