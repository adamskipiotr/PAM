package com.example.pam.dto

class MessageDTO {
    var groupID: Long? = null
    var title: String? = null
    var author: String? = null
    var seenByUser: Boolean? = null
    var contents: String? = null

    constructor(groupID: Long, contents: String, title: String, author: String) {
        this.groupID = groupID
        this.contents = contents
        this.title = title
        this.author = author
    }

    constructor()

    override fun toString(): String {
        return "$contents"
    }
}