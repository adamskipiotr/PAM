package com.example.pam

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.pam.api.StudentApi
import com.example.pam.api.TeacherApi
import com.example.pam.services.RetrofitBuilderService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddStudentGroupActivity : AppCompatActivity() {
    val context: Context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_students_group)
        val sendNewGroupButton = findViewById<Button>(R.id.joinSelectedGroupButton)
        val inputGroupName = findViewById<TextView>(R.id.addGroupInput)


        sendNewGroupButton.setOnClickListener {
            val groupName = inputGroupName.text.toString()

            val teacherApi: TeacherApi = RetrofitBuilderService.buildRetrofitService(TeacherApi::class.java)
            val call: Call<Void> = teacherApi.createNewGroup(groupName)
            call.enqueue(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Błąd dodawania grupy grup",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Toast.makeText(
                        applicationContext,
                        "Grupa została dodana do bazy danych",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }
}