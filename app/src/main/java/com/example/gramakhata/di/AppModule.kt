package com.example.gramakhata.di

import android.content.Context
import androidx.room.Room
import com.example.gramakhata.data.local.AppDatabase
import com.example.gramakhata.data.local.dao.CustomerDao
import com.example.gramakhata.data.local.dao.TransactionDao
import com.example.gramakhata.data.repository.LedgerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "gramakhata_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideShopkeeperDao(db: AppDatabase): com.example.gramakhata.data.local.dao.ShopkeeperDao {
        return db.shopkeeperDao
    }

    @Provides
    @Singleton
    fun provideCustomerDao(db: AppDatabase): CustomerDao {
        return db.customerDao
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: AppDatabase): TransactionDao {
        return db.transactionDao
    }

    @Provides
    @Singleton
    fun provideLedgerRepository(
        customerDao: CustomerDao,
        transactionDao: TransactionDao,
        shopkeeperDao: com.example.gramakhata.data.local.dao.ShopkeeperDao
    ): LedgerRepository {
        return LedgerRepository(customerDao, transactionDao, shopkeeperDao)
    }
}
