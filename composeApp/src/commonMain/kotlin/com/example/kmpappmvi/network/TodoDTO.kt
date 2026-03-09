package com.example.kmpappmvi.network

import kotlinx.serialization.Serializable

@Serializable
data class TodoDTO(
    val id : Int,
    val userId : Int,
    val title : String,
    val completed : Boolean

)