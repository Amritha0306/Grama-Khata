package com.example.gramakhata.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gramakhata.data.local.SessionManager
import com.example.gramakhata.data.local.entity.Shopkeeper
import com.example.gramakhata.data.repository.LedgerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val ledgerRepository: LedgerRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(sessionManager.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    fun signup(name: String, shopName: String, phone: String, onSignupSuccess: () -> Unit) {
        viewModelScope.launch {
            val shopkeeper = Shopkeeper(
                name = name,
                shopName = shopName,
                phoneNumber = phone
            )
            ledgerRepository.signupShopkeeper(shopkeeper)
            onSignupSuccess()
        }
    }

    fun login(shopName: String, phone: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            val shopkeeper = ledgerRepository.getShopkeeperByPhone(phone)
            if (shopkeeper != null && shopkeeper.shopName.equals(shopName, ignoreCase = true)) {
                sessionManager.setLoggedIn(true, phone)
                _isLoggedIn.value = true
                onLoginSuccess()
            } else {
                _loginError.value = "Invalid shop name or phone number."
            }
        }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        sessionManager.setLoggedIn(false)
        _isLoggedIn.value = false
        onLogoutSuccess()
    }
    
    fun clearError() {
        _loginError.value = null
    }
}
