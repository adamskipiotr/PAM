package com.example.pam.dto

class TeacherDTO {

     private var username: String? = null
     private var password: String? = null


    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }

    constructor()
}
