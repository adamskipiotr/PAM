package com.example.pam

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.pam.api.StudentApi
import com.example.pam.dto.StudentsGroupDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class JoinStudentsGroupActivity : AppCompatActivity() {
    lateinit var spinner: Spinner
    val context: Context = this
    var studentsGroupsList: List<StudentsGroupDTO>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_students_group)
        spinner = findViewById(R.id.spinnerStudentsGroupToJoin)

        val builder = Retrofit.Builder()
        builder.baseUrl("http://IP-KOMPUTERA:8080/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit
        retrofit = builder.build()
        val studentApi: StudentApi = retrofit.create(StudentApi::class.java)
        val call: Call<List<StudentsGroupDTO>> = studentApi.getAllStudentsGroups()
        call.enqueue(object : Callback<List<StudentsGroupDTO>> {

            override fun onFailure(call: Call<List<StudentsGroupDTO>>, t: Throwable) {
                Toast.makeText(applicationContext, "Błąd pobierania listy grup", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(
                call: Call<List<StudentsGroupDTO>>,
                response: Response<List<StudentsGroupDTO>>
            ) {
                val studentsGroupsNames: MutableList<String> = LinkedList<String>().toMutableList()
                studentsGroupsList = response.body()
                studentsGroupsList?.forEach {
                    studentsGroupsNames += it.toString()
                }
                val adapter =
                    ArrayAdapter(context, android.R.layout.simple_spinner_item, studentsGroupsNames)
                spinner.adapter = adapter
            }
        })
    }
}