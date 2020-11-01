package com.example.pam.api

import com.example.pam.dto.MessageDTO
import com.example.pam.dto.StudentDTO
import retrofit2.Call
import retrofit2.http.*

 interface StudentApi {

    @POST("student/add")
    fun createStudent(@Body studentDTO: StudentDTO): Call<StudentDTO>

    @POST("student/login")
    fun loginStudent(@Body studentDTO: StudentDTO): Call<Boolean>

    @POST("student/getAllMessages")
    fun getAllMessagesForStudent(@Body studentDTO: StudentDTO):Call<List<MessageDTO>>
}