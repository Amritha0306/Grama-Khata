package com.example.gramakhata.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopkeeper")
data class Shopkeeper(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val shopName: String,
    val phoneNumber: String,
    val shopImageUri: String? = null
)
