package com.example.gramakhata.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gramakhata.data.local.entity.Customer
import com.example.gramakhata.data.local.entity.CustomerWithBalance
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer): Long

    @Query("SELECT * FROM customers WHERE id = :id")
    suspend fun getCustomerById(id: Long): Customer?

    @Query("""
        SELECT c.id, c.name, c.phoneNumber, c.photoUri, 
        COALESCE(SUM(CASE WHEN t.type = 'CREDIT' THEN t.amount ELSE 0 END), 0) - 
        COALESCE(SUM(CASE WHEN t.type = 'PAYMENT' THEN t.amount ELSE 0 END), 0) as netBalance
        FROM customers c
        LEFT JOIN transactions t ON c.id = t.customerId
        GROUP BY c.id
        ORDER BY netBalance DESC
    """)
    fun getCustomersWithBalance(): Flow<List<CustomerWithBalance>>
    @Query("DELETE FROM customers")
    suspend fun deleteAll()
}
