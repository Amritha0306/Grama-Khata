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
class ProfileViewModel @Inject constructor(
    private val ledgerRepository: LedgerRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _shopkeeper = MutableStateFlow<Shopkeeper?>(null)
    val shopkeeper: StateFlow<Shopkeeper?> = _shopkeeper.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val phone = sessionManager.getLoggedInPhone()
            if (phone != null) {
                _shopkeeper.value = ledgerRepository.getShopkeeperByPhone(phone)
            } else {
                // Try fallback to first
                ledgerRepository.getFirstShopkeeper().collect {
                    _shopkeeper.value = it
                }
            }
        }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        sessionManager.setLoggedIn(false)
        onLogoutSuccess()
    }

    fun deleteAccount(onDeleteSuccess: () -> Unit) {
        viewModelScope.launch {
            ledgerRepository.deleteAccount()
            sessionManager.setLoggedIn(false)
            onDeleteSuccess()
        }
    }
}
