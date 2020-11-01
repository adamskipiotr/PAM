package com.example.pam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.pam.api.StudentApi
import com.example.pam.dto.StudentDTO
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            var loginUserResult:Boolean = false
            val builder = Retrofit.Builder()
            builder.baseUrl("http://IP-KOMPUTERA-UZUPELNIC:8080/")
            builder.addConverterFactory(GsonConverterFactory.create())
            val retrofit :Retrofit
            retrofit = builder.build()
            val studentApi:StudentApi
            studentApi = retrofit.create(StudentApi::class.java)
            val testStudent : StudentDTO = StudentDTO(usernameInput.text.toString(),passwordInput.text.toString())

            val call: Call<StudentDTO> = studentApi.loginStudent(testStudent)
                call.enqueue(object: Callback<StudentDTO>{

                    override fun onFailure(call: Call<StudentDTO>, t: Throwable) {
                        Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<StudentDTO>, response: Response<StudentDTO>) {
                        Toast.makeText(applicationContext, "TEA", Toast.LENGTH_LONG).show()
                         loginUserResult = true
                    }

                })
            val intent = Intent(this, User::class.java).apply {};
            startActivity(intent)


            if(!loginUserResult){
                Toast.makeText(this, "Błąd logowania", Toast.LENGTH_LONG).show()
            }

            if(loginUserResult){
                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()

            }
        }
    }
}