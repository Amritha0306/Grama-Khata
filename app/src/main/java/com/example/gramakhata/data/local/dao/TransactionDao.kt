package com.example.gramakhata.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gramakhata.data.local.entity.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE customerId = :customerId ORDER BY timestamp DESC")
    fun getTransactionsForCustomer(customerId: Long): Flow<List<Transaction>>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'PAYMENT' AND timestamp >= :startOfDay AND timestamp <= :endOfDay")
    suspend fun getDailyCollection(startOfDay: Long, endOfDay: Long): Double?
    @Query("DELETE FROM transactions")
    suspend fun deleteAll()
}
