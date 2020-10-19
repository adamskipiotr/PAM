package com.example.pam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var dbHandler: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val usernameInput = findViewById<TextView>(R.id.loginInput)
        val passwordInput = findViewById<TextView>(R.id.passwordInput)

        dbHandler = DatabaseHelper(this)

        loginButton.setOnClickListener {
            val loginUserResult = dbHandler.getUser(login = usernameInput.text.toString(),password = passwordInput.text.toString() )

            if(!loginUserResult){
                Toast.makeText(this, "Błąd logowania", Toast.LENGTH_LONG).show()
            }

            if(loginUserResult){
                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
                val intent = Intent(this, User::class.java).apply {};
                startActivity(intent)
            }
        }
    }
}