package com.example.pam.api

import com.example.pam.dto.MessageDTO
import com.example.pam.dto.StudentDTO
import com.example.pam.dto.StudentsGroupDTO
import com.example.pam.dto.TeacherDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TeacherApi {

    @POST("teacher/sendMessage")
    fun sendNewMessage(@Body messageDTO: MessageDTO): Call<Void>

    @GET("group/getAll")
    fun getAllStudentsGroups():Call<List<StudentsGroupDTO>>

    @POST("teacher/login")
    fun loginTeacher(@Body teacherDTO: TeacherDTO):Call<Boolean>
}