package com.example.pam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UserMessages : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_messages)

        val list = mutableListOf(
            "Golden yellow",
            "Han purple",
            "Hansa yellow",
            "Jazzberry jam"
        )

        // initialize an array adapter
        val adapter:ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            list
        )
        val listViewItem = findViewById<ListView>(R.id.listView)
        // attach the array adapter with list view
        listViewItem.adapter = adapter

        // list view item click listener
        listViewItem.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->

            // remove clicked item from list view
            list.removeAt(position)
            adapter.notifyDataSetChanged()
        }

        // another list to populate second list view
        val list2 = mutableListOf(
            "Android green",
            "Antique brass",
            "Antique white",
            "Avocado"
        )

        // initialize another array adapter
        val adapter2:ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            list2
        )

        val listView2 = findViewById<ListView>(R.id.listView2)
        // attach the array adapter with second list view
        listView2.adapter = adapter2


        // second list view item click listener
        listView2.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->

            val clickedItem = parent.getItemAtPosition(position).toString()

            // show an alert dialog to confirm
            MaterialAlertDialogBuilder(this).apply {
                setTitle("Item: $clickedItem")
                setMessage("Are you want to delete it from list view?")
                setPositiveButton("Delete"){_,_->
                    // remove clicked item from second list view
                    list2.removeAt(position)
                    adapter2.notifyDataSetChanged()
                }
                setNeutralButton("Cancel"){_,_->}
            }.create().show()
        }
    }
}