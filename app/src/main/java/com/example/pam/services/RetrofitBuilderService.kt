package com.example.pam.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilderService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pam-polsl.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<T> buildRetrofitService(service: Class<T>): T{
        return retrofit.create(service)
    }
}