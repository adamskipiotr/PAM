package com.example.pam

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val registerActivityButton = findViewById<Button>(R.id.loginButton)
        val loginActivityButton = findViewById<Button>(R.id.loginLayoutButton)

        registerActivityButton.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java).apply {};
            startActivity(intent)
        }

        loginActivityButton.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java).apply {};
            startActivity(intent)
        }
    }

}