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
            val call: Call<List<StudentsGroupDTO>> = teacherApi.getAllStudentsGroups()
            call.enqueue(object : Callback<List<StudentsGroupDTO>> {

                override fun onFailure(call: Call<List<StudentsGroupDTO>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Błąd pobierania listy grup",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                override fun onResponse(
                    call: Call<List<StudentsGroupDTO>>,
                    response: Response<List<StudentsGroupDTO>>
                ) {
                    val studentsGroupsNames: MutableList<String> =
                        LinkedList<String>().toMutableList()
                    studentsGroupsList = response.body()
                    studentsGroupsList?.forEach {
                        studentsGroupsNames += it.toString()
                    }
                    val adapter =
                        ArrayAdapter(
                            context,
                            android.R.layout.simple_spinner_item,
                            studentsGroupsNames
                        )
                    spinner.adapter = adapter
                }
            })
        }
    }
}