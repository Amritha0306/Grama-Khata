package com.example.gramakhata.ui.screens.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gramakhata.data.local.entity.TransactionType
import com.example.gramakhata.ui.theme.CreditColor
import com.example.gramakhata.ui.theme.PaymentColor
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerProfileScreen(
    viewModel: CustomerViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val customer by viewModel.customer.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    var showTransactionSheet by remember { mutableStateOf(false) }
    var transactionType by remember { mutableStateOf(TransactionType.CREDIT) }

    val totalCredit = transactions.filter { it.type == TransactionType.CREDIT }.sumOf { it.amount }
    val totalPaid = transactions.filter { it.type == TransactionType.PAYMENT }.sumOf { it.amount }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        customer?.name ?: "", 
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Delete Action */ }) {
                        Icon(Icons.Outlined.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background,
                tonalElevation = 0.dp,
                modifier = Modifier.fillMaxWidth().height(100.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            transactionType = TransactionType.CREDIT
                            showTransactionSheet = true
                        },
                        modifier = Modifier.weight(1f).height(64.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CreditColor)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("GIVE", fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                            Text("+ CREDIT", fontSize = 14.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                        }
                    }
                    Button(
                        onClick = {
                            transactionType = TransactionType.PAYMENT
                            showTransactionSheet = true
                        },
                        modifier = Modifier.weight(1f).height(64.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PaymentColor)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("RECEIVE", fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                            Text("- PAYMENT", fontSize = 14.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            customer?.let { cust ->
                val netBalance = totalCredit - totalPaid
                val isDue = netBalance >= 0
                val amountText = if (isDue) "₹${netBalance.toInt()}" else "₹${-netBalance.toInt()}"
                val amountColor = if (isDue) CreditColor else PaymentColor
                val statusText = if (isDue) "AWAITING PAYMENT" else "ADVANCE"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("BALANCE STATUS", color = Color.Gray, fontWeight = FontWeight.Black, fontSize = 12.sp, letterSpacing = 2.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(amountText, fontWeight = FontWeight.Black, fontSize = 64.sp, color = amountColor)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(statusText, color = amountColor, fontWeight = FontWeight.Black, fontSize = 14.sp, fontStyle = FontStyle.Italic, letterSpacing = 1.sp)
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        HorizontalDivider(color = Color(0xFFF0F0F0))
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                Text("TOTAL CREDIT", color = Color.Gray, fontWeight = FontWeight.Black, fontSize = 10.sp, letterSpacing = 1.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("₹${totalCredit.toInt()}", fontWeight = FontWeight.Black, fontSize = 20.sp, color = CreditColor)
                            }
                            // Vertical Divider
                            Box(modifier = Modifier.width(1.dp).height(40.dp).background(Color(0xFFF0F0F0)))
                            
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                Text("TOTAL PAID", color = Color.Gray, fontWeight = FontWeight.Black, fontSize = 10.sp, letterSpacing = 1.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("₹${totalPaid.toInt()}", fontWeight = FontWeight.Black, fontSize = 20.sp, color = PaymentColor)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.List, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("TRANSACTION LOG", fontWeight = FontWeight.Black, fontSize = 12.sp, color = Color.Gray, letterSpacing = 2.sp)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(transactions) { transaction ->
                    val isCredit = transaction.type == TransactionType.CREDIT
                    val cardColor = if (isCredit) CreditColor else PaymentColor
                    val bgColor = if (isCredit) CreditColor.copy(alpha = 0.05f) else PaymentColor.copy(alpha = 0.05f)
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(bgColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isCredit) "+" else "-",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 24.sp,
                                    color = cardColor
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(16.dp))
                            
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isCredit) "Credit" else "Payment",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                val sdf = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
                                Text(text = sdf.format(Date(transaction.timestamp)).uppercase(), color = Color.Gray, fontWeight = FontWeight.Black, fontSize = 10.sp)
                            }
                            
                            Text(
                                text = "${if (isCredit) "+" else "-"}₹${transaction.amount.toInt()}",
                                fontWeight = FontWeight.Black,
                                fontSize = 24.sp,
                                color = cardColor
                            )
                        }
                    }
                }
            }
        }
    }

    if (showTransactionSheet) {
        TransactionBottomSheet(
            type = transactionType,
            onDismiss = { showTransactionSheet = false },
            onSave = { amount ->
                viewModel.addTransaction(amount, transactionType)
                showTransactionSheet = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionBottomSheet(
    type: TransactionType,
    onDismiss: () -> Unit,
    onSave: (Double) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var amountText by remember { mutableStateOf("") }
    
    val color = if (type == TransactionType.CREDIT) CreditColor else PaymentColor
    val title = if (type == TransactionType.CREDIT) "Give Credit (You gave ₹)" else "Receive Payment (You got ₹)"

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(title, fontWeight = FontWeight.Black, fontSize = 20.sp, color = color)
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Amount", fontWeight = FontWeight.Bold) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = color,
                    unfocusedBorderColor = Color.LightGray
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    amountText.toDoubleOrNull()?.let { onSave(it) }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = color)
            ) {
                Text("SAVE TRANSACTION", fontSize = 14.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
            }
        }
    }
}
