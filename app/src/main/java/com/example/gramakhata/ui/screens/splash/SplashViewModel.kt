package com.example.gramakhata.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gramakhata.data.local.SessionManager
import com.example.gramakhata.data.repository.LedgerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val ledgerRepository: LedgerRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination.asStateFlow()

    init {
        determineStartDestination()
    }

    private fun determineStartDestination() {
        viewModelScope.launch {
            val isLoggedIn = sessionManager.isLoggedIn()
            if (isLoggedIn) {
                _startDestination.value = "dashboard"
            } else {
                val firstShopkeeper = ledgerRepository.getFirstShopkeeper().firstOrNull()
                if (firstShopkeeper != null) {
                    _startDestination.value = "login"
                } else {
                    _startDestination.value = "signup"
                }
            }
        }
    }
}
