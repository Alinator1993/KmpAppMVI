package com.example.kmpappmvi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform