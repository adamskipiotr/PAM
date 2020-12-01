package com.example.pam

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.pam.api.StudentApi
import com.example.pam.api.TeacherApi
import com.example.pam.dto.StudentsGroupDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AddStudentGroupActivity : AppCompatActivity() {
    lateinit var spinner: Spinner
    val context: Context = this
    var studentsGroupsList: List<StudentsGroupDTO>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_students_group)
        val sendNewGroupButton = findViewById<Button>(R.id.addGroupButton)
        val inputGroupName = findViewById<TextView>(R.id.addGroupInput)


        sendNewGroupButton.setOnClickListener {
            val groupName = inputGroupName.text.toString()
            val builder = Retrofit.Builder()
            builder.baseUrl("http://192.168.0.213:8080/")
            builder.addConverterFactory(GsonConverterFactory.create())
            val retrofit: Retrofit
            retrofit = builder.build()
            val teacherApi: TeacherApi = retrofit.create(TeacherApi::class.java)
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