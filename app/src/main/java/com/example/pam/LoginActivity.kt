package com.example.pam

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.pam.api.StudentApi
import com.example.pam.api.TeacherApi
import com.example.pam.dto.StudentDTO
import com.example.pam.dto.TeacherDTO
import com.example.pam.responses.StudentLoginResponse
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val usernameInput = findViewById<TextView>(R.id.loginInput)
        val passwordInput = findViewById<TextView>(R.id.passwordInput)

        loginButton.setOnClickListener {
            var loginUserResult: Boolean
            val builder = Retrofit.Builder()
            builder.baseUrl("http://192.168.0.213:8080/")
            builder.addConverterFactory(GsonConverterFactory.create())
            val retrofit: Retrofit
            retrofit = builder.build()
            val studentApi: StudentApi
            studentApi = retrofit.create(StudentApi::class.java)
            val testStudent =
                StudentDTO(1L, usernameInput.text.toString(), passwordInput.text.toString())

            val call: Call<StudentLoginResponse> = studentApi.loginStudent(testStudent)
            call.enqueue(object : Callback<StudentLoginResponse> {

                override fun onFailure(call: Call<StudentLoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Błąd serwera", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<StudentLoginResponse>, response: Response<StudentLoginResponse>) {
                    val serverLoginStatus: StudentLoginResponse = response.body()!!
                    loginUserResult = serverLoginStatus.getResult()
                    if (loginUserResult) {
                        val intent = Intent(context, StudentActivity::class.java).apply {};
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Błąd logowania", Toast.LENGTH_LONG)
                            .show()
                        loginTeacher(
                            usernameInput.text.toString(),
                            passwordInput.text.toString()
                        )
                    }
                }
            })
        }
    }

    private fun loginTeacher(usernameInput: String, passwordInput: String) {
        var loginUserResult: Boolean
        val builder = Retrofit.Builder()
        builder.baseUrl("http://192.168.0.213:8080/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit
        retrofit = builder.build()

        val teacherDto = TeacherDTO(usernameInput, passwordInput)
        val teacherApi: TeacherApi = retrofit.create(TeacherApi::class.java)
        val call = teacherApi.loginTeacher(teacherDto)
        call.enqueue(object : Callback<Boolean> {

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(applicationContext, "Błąd serwera", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                val serverLoginStatus: String = response.body()!!.toString()
                loginUserResult = serverLoginStatus.toBoolean()
                if (loginUserResult) {
                    val intent = Intent(context, TeacherActivity::class.java).apply {};
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Błąd logowania", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }
}

