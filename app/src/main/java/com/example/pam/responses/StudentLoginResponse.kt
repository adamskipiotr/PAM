package com.example.pam.responses

import com.example.pam.dto.StudentDTO

class StudentLoginResponse {
    private var activeStudent: StudentDTO? = null
    private var result: Boolean? = null


    constructor(activeStudent: StudentDTO, result: Boolean) {
        this.activeStudent = activeStudent
        this.result = result
    }

    constructor()

    fun  getResult():Boolean{
        return this.result!!
    }
}