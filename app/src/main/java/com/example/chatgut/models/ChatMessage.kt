package com.example.chatgut.models

class ChatMessage(val id: String, val text: String, val fromId: String, val toId: String, val timestamp: Long, val messageSeen:Boolean) {
    constructor() : this("","","","", -1, false)
}