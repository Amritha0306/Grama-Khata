package com.example.gramakhata.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gramakhata.data.local.entity.Customer
import com.example.gramakhata.data.local.entity.CustomerWithBalance
import com.example.gramakhata.data.local.entity.Transaction
import com.example.gramakhata.data.local.entity.TransactionType
import com.example.gramakhata.data.repository.LedgerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.example.gramakhata.data.local.SessionManager

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: LedgerRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _shopkeeper = MutableStateFlow<com.example.gramakhata.data.local.entity.Shopkeeper?>(null)
    val shopkeeper: StateFlow<com.example.gramakhata.data.local.entity.Shopkeeper?> = _shopkeeper.asStateFlow()

    private val _shopName = MutableStateFlow<String?>(null)
    val shopName: StateFlow<String?> = _shopName.asStateFlow()

    private val _shopkeeperName = MutableStateFlow<String?>(null)
    val shopkeeperName: StateFlow<String?> = _shopkeeperName.asStateFlow()

    private val _language = MutableStateFlow(sessionManager.getLanguage())
    val language: StateFlow<String> = _language.asStateFlow()

    init {
        viewModelScope.launch {
            val phone = sessionManager.getLoggedInPhone()
            if (phone != null) {
                val sk = repository.getShopkeeperByPhone(phone)
                _shopkeeper.value = sk
                _shopName.value = sk?.shopName
                _shopkeeperName.value = sk?.name
            }
        }
    }

    fun updateLanguage(newLang: String) {
        sessionManager.setLanguage(newLang)
        _language.value = newLang
    }

    val customers: StateFlow<List<CustomerWithBalance>> = repository.getCustomersWithBalance()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addCustomer(name: String, phoneNumber: String, photoUri: String?) {
        viewModelScope.launch {
            repository.addCustomer(
                Customer(
                    name = name,
                    phoneNumber = phoneNumber,
                    photoUri = photoUri
                )
            )
        }
    }

    fun quickAddTransaction(customerId: Long, amount: Double, type: TransactionType) {
        viewModelScope.launch {
            repository.addTransaction(
                Transaction(
                    customerId = customerId,
                    amount = amount,
                    type = type
                )
            )
        }
    }
    
    suspend fun getDailyReport(): Double {
        return repository.getDailyCollection()
    }

    fun updateProfile(name: String, shopName: String, phone: String) {
        viewModelScope.launch {
            val current = _shopkeeper.value
            if (current != null) {
                val updated = current.copy(name = name, shopName = shopName, phoneNumber = phone)
                repository.signupShopkeeper(updated)
                _shopkeeper.value = updated
                _shopName.value = shopName
                _shopkeeperName.value = name
                sessionManager.setLoggedIn(true, phone)
            }
        }
    }

    fun logout() {
        sessionManager.setLoggedIn(false)
    }
}
