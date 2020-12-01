package com.example.pam.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.text.toSpannable
import com.example.pam.R
import com.example.pam.dto.MessageDTO


class MessageAdapter(context: Context, private val dataSource: ArrayList<MessageDTO>) : BaseAdapter() {

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
        val rowView = inflater.inflate(R.layout.list_item_message, parent, false)

         val titleTextView = rowView.findViewById(R.id.message_list_title) as TextView

        val detailsTextView = rowView.findViewById(R.id.message_list_subtitle) as TextView

        val authorTextView = rowView.findViewById(R.id.message_list_detail) as TextView
        val message = getItem(position) as MessageDTO

        titleTextView.text = message.title
        detailsTextView.text = message.contents
        authorTextView.text = message.author

        if(message.seenByUser == false){
            rowView.setBackgroundColor(0xFFFB9F9F.toInt())
        }


        val str = titleTextView.text.toSpannable()

        str.setSpan(
             StyleSpan (Typeface.BOLD),
            0,
           2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        
        return rowView
    }
}