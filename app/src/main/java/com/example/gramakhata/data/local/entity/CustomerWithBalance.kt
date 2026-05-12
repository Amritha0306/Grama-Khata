package com.example.gramakhata.data.local.entity

data class CustomerWithBalance(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val photoUri: String?,
    val netBalance: Double // Positive means they owe money (credit > payment)
)
