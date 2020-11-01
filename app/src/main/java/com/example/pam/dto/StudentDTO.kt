package com.example.pam.dto


class StudentDTO {
    var username: String? = null
    var password: String? = null

    constructor(username: String,password: String) {
        this.username = username
        this.password = password
    }

    constructor()
}
