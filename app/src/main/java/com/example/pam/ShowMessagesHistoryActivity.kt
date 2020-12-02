package com.example.pam

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.pam.adapters.MessageAdapter
import com.example.pam.api.TeacherApi
import com.example.pam.dto.MessageDTO
import com.example.pam.dto.TeacherDTO
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class ShowMessagesHistoryActivity : AppCompatActivity() {

    var arrayListMessage: ArrayList<MessageDTO>? = null
    var messagesToRead: List<MessageDTO>? = null
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_messages)
        val sp: SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)

        val builder = Retrofit.Builder()
        builder.baseUrl("http://192.168.0.213:8080/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit
        retrofit = builder.build()
        val teacherApi: TeacherApi = retrofit.create(TeacherApi::class.java)
        val ID = sp.getLong("ID", -1)
        val username = sp.getString("username", "EMPTY")
        val password = sp.getString("password", "EMPTY")
        val activeTeacher = TeacherDTO(ID, username!!, password!!)
        val call: Call<List<MessageDTO>> = teacherApi.getAllTeacherMessages(activeTeacher)
        call.enqueue(object : Callback<List<MessageDTO>> {

            override fun onFailure(call: Call<List<MessageDTO>>, t: Throwable) {
                Toast.makeText(applicationContext, "Błąd serwera", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<MessageDTO>>,
                response: Response<List<MessageDTO>>
            ) {
                Toast.makeText(applicationContext, "Sukces", Toast.LENGTH_LONG).show()
                val messagesToShow: MutableList<String> = LinkedList<String>().toMutableList()
                arrayListMessage = java.util.ArrayList()
                messagesToRead = response.body()
                messagesToRead?.forEach {
                    messagesToShow += it.toString()
                    arrayListMessage!!.add(it)
                }

                val adapter: ArrayAdapter<String> = ArrayAdapter(
                    context,
                    android.R.layout.simple_dropdown_item_1line,
                    messagesToShow
                )
                val listViewItem = findViewById<ListView>(R.id.listViewTeacher)
                val adapterCustom = MessageAdapter(context, arrayListMessage!!)
                listViewItem.adapter = adapterCustom

                listViewItem.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->

                        val clickedItem :MessageDTO = parent.getItemAtPosition(position) as MessageDTO


                        MaterialAlertDialogBuilder(context).apply {
                            setTitle("Wiadomość: ${clickedItem.title}")
                            setMessage("Wyświetlone przez: ${clickedItem.contents}")
                            setPositiveButton("Potwierdź") { _, _ ->
                            }
                            setNeutralButton("Anuluj") { _, _ -> }
                        }.create().show()
                    }
            }
        })
    }
}