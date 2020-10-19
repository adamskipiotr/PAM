package com.example.pam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import me.grantland.widget.AutofitHelper

class User : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val myStrings = arrayOf("Select", "One", "Two", "Three", "Four")
        val spinner = findViewById<Spinner>(R.id.spinnerRecieverGroup)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, myStrings
            )
            spinner.adapter = adapter
        }


        val checkMessagesButton = findViewById<Button>(R.id.recievedMessagesButton)
        checkMessagesButton.setOnClickListener() {
            val intent = Intent(this, UserMessages::class.java).apply {};
            startActivity(intent)
        }


        val messageTextField = findViewById<EditText>(R.id.messageFieldInput)


        val sendMessageButton = findViewById<Button>(R.id.sendMessageButton)
        sendMessageButton.setOnClickListener() {
            Toast.makeText(this, messageTextField.text.toString(), Toast.LENGTH_LONG).show()
        }
    }
}