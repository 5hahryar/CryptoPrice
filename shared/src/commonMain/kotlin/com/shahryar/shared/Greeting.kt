package com.shahryar.shared

class Greeting {
    fun greeting(): String {
        return "Helloooo, ${Platform().platform}!"
    }
}