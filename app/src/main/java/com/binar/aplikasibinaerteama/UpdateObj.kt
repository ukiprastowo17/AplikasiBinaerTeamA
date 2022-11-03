package com.binar.aplikasibinaerteama

import java.io.Serializable

class UpdateObj(action: String, playerName: String) : Serializable {
    var action: String? = null
    val playerName: String

    init {
        try {
            if (action == "add" || action == "delete") {
                this.action = action
            } else {
                throw Exception("Update action must be \"add\" or \"delete\"")
            }
        } catch (e: Exception) {
            e.message
        }
        this.playerName = playerName
    }
}