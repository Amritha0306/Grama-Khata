package com.example.gramakhata.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gramakhata.data.local.dao.ShopkeeperDao
import com.example.gramakhata.data.local.dao.CustomerDao
import com.example.gramakhata.data.local.dao.TransactionDao
import com.example.gramakhata.data.local.entity.Shopkeeper
import com.example.gramakhata.data.local.entity.Customer
import com.example.gramakhata.data.local.entity.Transaction

@Database(
    entities = [Customer::class, Transaction::class, Shopkeeper::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val customerDao: CustomerDao
    abstract val transactionDao: TransactionDao
    abstract val shopkeeperDao: ShopkeeperDao
}
