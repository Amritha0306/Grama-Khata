package com.example.gramakhata.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gramakhata.data.local.entity.Shopkeeper
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopkeeperDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopkeeper(shopkeeper: Shopkeeper): Long

    @Query("SELECT * FROM shopkeeper WHERE phoneNumber = :phoneNumber LIMIT 1")
    suspend fun getShopkeeperByPhone(phoneNumber: String): Shopkeeper?

    @Query("SELECT * FROM shopkeeper LIMIT 1")
    fun getFirstShopkeeper(): Flow<Shopkeeper?>

    @Query("DELETE FROM shopkeeper")
    suspend fun deleteAll()
}
