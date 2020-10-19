package com.example.pam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerActivityButton = findViewById<Button>(R.id.loginButton)
        registerActivityButton.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java).apply {};
            startActivity(intent)
        }

        val loginActivityButton = findViewById<Button>(R.id.loginLayoutButton)
        loginActivityButton.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java).apply {};
            startActivity(intent)
        }
    }
}