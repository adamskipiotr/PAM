package com.example.pam.dto

class MessageDTO {
     var groupID:Long? = null
     var contents:String? = null

    constructor(groupID: Long,contents:String) {
        this.groupID = groupID
        this.contents = contents
    }

    constructor()

    override fun toString(): String {
        return "$contents"
    }
}