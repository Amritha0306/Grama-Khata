package com.example.gramakhata.data.repository

import com.example.gramakhata.data.local.dao.CustomerDao
import com.example.gramakhata.data.local.dao.TransactionDao
import com.example.gramakhata.data.local.entity.Customer
import com.example.gramakhata.data.local.entity.CustomerWithBalance
import com.example.gramakhata.data.local.entity.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class LedgerRepository @Inject constructor(
    private val customerDao: CustomerDao,
    private val transactionDao: TransactionDao,
    private val shopkeeperDao: com.example.gramakhata.data.local.dao.ShopkeeperDao
) {
    fun getCustomersWithBalance(): Flow<List<CustomerWithBalance>> = customerDao.getCustomersWithBalance()

    suspend fun getCustomerById(id: Long): Customer? = customerDao.getCustomerById(id)

    suspend fun addCustomer(customer: Customer): Long = customerDao.insertCustomer(customer)

    fun getTransactionsForCustomer(customerId: Long): Flow<List<Transaction>> = transactionDao.getTransactionsForCustomer(customerId)

    suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun getDailyCollection(): Double {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis

        return transactionDao.getDailyCollection(startOfDay, endOfDay) ?: 0.0
    }

    // Shopkeeper Auth
    suspend fun signupShopkeeper(shopkeeper: com.example.gramakhata.data.local.entity.Shopkeeper) {
        shopkeeperDao.insertShopkeeper(shopkeeper)
    }

    suspend fun getShopkeeperByPhone(phone: String): com.example.gramakhata.data.local.entity.Shopkeeper? {
        return shopkeeperDao.getShopkeeperByPhone(phone)
    }
    
    fun getFirstShopkeeper(): Flow<com.example.gramakhata.data.local.entity.Shopkeeper?> {
        return shopkeeperDao.getFirstShopkeeper()
    }
    
    suspend fun deleteAccount() {
        shopkeeperDao.deleteAll()
        customerDao.deleteAll() // Delete all customers
        transactionDao.deleteAll() // Delete all transactions
    }
}
