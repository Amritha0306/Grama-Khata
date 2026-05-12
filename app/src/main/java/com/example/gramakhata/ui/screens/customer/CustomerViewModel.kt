package com.example.gramakhata.ui.screens.customer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gramakhata.data.local.entity.Customer
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

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: LedgerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val customerId: Long = checkNotNull(savedStateHandle["customerId"])

    private val _customer = MutableStateFlow<Customer?>(null)
    val customer: StateFlow<Customer?> = _customer.asStateFlow()

    val transactions: StateFlow<List<Transaction>> = repository.getTransactionsForCustomer(customerId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            _customer.value = repository.getCustomerById(customerId)
        }
    }

    fun addTransaction(amount: Double, type: TransactionType) {
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
}
