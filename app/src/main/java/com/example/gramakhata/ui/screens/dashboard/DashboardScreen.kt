package com.example.gramakhata.ui.screens.dashboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gramakhata.data.local.entity.CustomerWithBalance
import com.example.gramakhata.ui.theme.CreditColor
import com.example.gramakhata.ui.theme.PaymentColor
import com.example.gramakhata.ui.screens.auth.ProfileScreen
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToCustomer: (Long) -> Unit,
    onNavigateToAddCustomer: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    val customers by viewModel.customers.collectAsState()
    val shopName by viewModel.shopName.collectAsState()
    val shopkeeperName by viewModel.shopkeeperName.collectAsState()
    val shopkeeper by viewModel.shopkeeper.collectAsState()
    val language by viewModel.language.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("HOME", fontWeight = FontWeight.Bold, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { 
                        Text("|||", fontWeight = FontWeight.Bold, color = if (selectedTab == 1) MaterialTheme.colorScheme.primary else Color.Gray, modifier = Modifier.rotate(90f)) 
                    },
                    label = { Text("DASHBOARD", fontWeight = FontWeight.Bold, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Filled.Notifications, contentDescription = "Reminders") },
                    label = { Text("REMINDERS", fontWeight = FontWeight.Bold, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeTab(
                    customers = customers, 
                    shopkeeper = shopkeeper, 
                    language = language,
                    onNavigateToCustomer = onNavigateToCustomer, 
                    onNavigateToAddCustomer = onNavigateToAddCustomer,
                    onUpdateProfile = { name, sName, phone -> viewModel.updateProfile(name, sName, phone) },
                    onUpdateLanguage = { viewModel.updateLanguage(it) },
                    onLogout = {
                        viewModel.logout()
                        onNavigateToLogin()
                    }
                )
                1 -> CollectionStatusTab(customers)
                2 -> RemindersTab(customers, context, shopName, shopkeeperName, language)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTab(
    customers: List<CustomerWithBalance>,
    shopkeeper: com.example.gramakhata.data.local.entity.Shopkeeper?,
    language: String,
    onNavigateToCustomer: (Long) -> Unit,
    onNavigateToAddCustomer: () -> Unit,
    onUpdateProfile: (String, String, String) -> Unit,
    onUpdateLanguage: (String) -> Unit,
    onLogout: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showProfileDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val filteredCustomers = if (searchQuery.isBlank()) {
        customers
    } else {
        customers.filter { it.name.contains(searchQuery, ignoreCase = true) || it.phoneNumber.contains(searchQuery) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Grama-Khata",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = (shopkeeper?.shopName ?: "Shop Name").uppercase(),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.primary)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = { Text("Profile") },
                        onClick = { expanded = false; showProfileDialog = true },
                        leadingIcon = { Icon(Icons.Filled.AccountCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
                    )
                    DropdownMenuItem(
                        text = { Text("Change Language") },
                        onClick = { expanded = false; showLanguageDialog = true },
                        leadingIcon = { Icon(Icons.Filled.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
                    )
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = { expanded = false; showLogoutDialog = true },
                        leadingIcon = { Icon(Icons.Filled.ExitToApp, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search customer...", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = "Search", tint = Color.Gray) },
                modifier = Modifier.weight(1f).height(52.dp),
                singleLine = true,
                shape = RoundedCornerShape(26.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { onNavigateToAddCustomer() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Customer", tint = Color.White, modifier = Modifier.size(28.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredCustomers, key = { it.id }) { customer ->
                CustomerCard(customer = customer, onClick = { onNavigateToCustomer(customer.id) })
            }
        }
    }

    if (showProfileDialog && shopkeeper != null) {
        var isEditing by remember { mutableStateOf(false) }
        var editName by remember { mutableStateOf(shopkeeper.name) }
        var editShopName by remember { mutableStateOf(shopkeeper.shopName) }
        var editPhone by remember { mutableStateOf(shopkeeper.phoneNumber) }
        
        AlertDialog(
            onDismissRequest = { showProfileDialog = false },
            title = { 
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Profile", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    if (!isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Outlined.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            },
            text = {
                Column {
                    if (isEditing) {
                        OutlinedTextField(value = editName, onValueChange = { editName = it }, label = { Text("Owner Name") }, singleLine = true)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(value = editShopName, onValueChange = { editShopName = it }, label = { Text("Shop Name") }, singleLine = true)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(value = editPhone, onValueChange = { editPhone = it }, label = { Text("Phone Number") }, singleLine = true)
                    } else {
                        Text("Owner Name", fontSize = 12.sp, color = Color.Gray)
                        Text(shopkeeper.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text("Shop Name", fontSize = 12.sp, color = Color.Gray)
                        Text(shopkeeper.shopName, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text("Phone Number", fontSize = 12.sp, color = Color.Gray)
                        Text(shopkeeper.phoneNumber, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            },
            confirmButton = {
                if (isEditing) {
                    Button(onClick = {
                        onUpdateProfile(editName, editShopName, editPhone)
                        isEditing = false
                    }) { Text("Save") }
                } else {
                    Button(onClick = { showProfileDialog = false }) { Text("Close") }
                }
            },
            dismissButton = {
                if (isEditing) {
                    TextButton(onClick = {
                        isEditing = false
                        editName = shopkeeper.name
                        editShopName = shopkeeper.shopName
                        editPhone = shopkeeper.phoneNumber
                    }) { Text("Cancel") }
                }
            },
            containerColor = Color.White
        )
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text("Select Language", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
            text = {
                Column {
                    listOf("English", "Kannada", "Hindi").forEach { lang ->
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable {
                                onUpdateLanguage(lang)
                                showLanguageDialog = false
                            }.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = language == lang,
                                onClick = {
                                    onUpdateLanguage(lang)
                                    showLanguageDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(lang, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showLanguageDialog = false }) { Text("Cancel") }
            },
            containerColor = Color.White
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
            text = { Text("Are you sure you want to logout?", fontSize = 16.sp) },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) { Text("Logout") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancel") }
            },
            containerColor = Color.White
        )
    }
}

@Composable
fun CollectionStatusTab(customers: List<CustomerWithBalance>) {
    val totalDue = customers.filter { it.netBalance > 0 }.sumOf { it.netBalance }
    val totalAdvance = customers.filter { it.netBalance < 0 }.sumOf { -it.netBalance }
    val highestDues = customers.filter { it.netBalance > 0 }.sortedByDescending { it.netBalance }.take(5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Collection Status",
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f).height(120.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("TOTAL DUE", color = CreditColor, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("₹${totalDue.toInt()}", color = CreditColor, fontWeight = FontWeight.Black, fontSize = 28.sp)
                }
            }
            Card(
                modifier = Modifier.weight(1f).height(120.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("ADVANCE PAY", color = Color.LightGray, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("₹${totalAdvance.toInt()}", color = Color.White, fontWeight = FontWeight.Black, fontSize = 28.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp).fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Info, contentDescription = null, tint = CreditColor, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("HIGHEST DUES", fontWeight = FontWeight.Black, fontSize = 16.sp, letterSpacing = 1.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(highestDues, key = { it.id }) { customer ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0xFFEBEBEB)),
                                contentAlignment = Alignment.Center
                            ) {
                                val initial = if (customer.name.isNotEmpty()) customer.name.first().uppercase() else "?"
                                Text(initial, fontWeight = FontWeight.Black, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(customer.name, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("₹${customer.netBalance}", fontWeight = FontWeight.Black, fontSize = 18.sp, color = CreditColor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RemindersTab(
    customers: List<CustomerWithBalance>, 
    context: Context,
    shopName: String?,
    shopkeeperName: String?,
    language: String
) {
    val outstandingCustomers = customers.filter { it.netBalance > 0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Daily Reminders",
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(outstandingCustomers, key = { it.id }) { customer ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Text(customer.name, fontWeight = FontWeight.Black, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(customer.phoneNumber, color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("₹${customer.netBalance}", fontWeight = FontWeight.Black, fontSize = 22.sp, color = CreditColor)
                                Text("OUTSTANDING", fontSize = 10.sp, fontWeight = FontWeight.Black, color = CreditColor.copy(alpha = 0.6f), letterSpacing = 1.sp)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { sendWhatsAppReminder(context, customer.phoneNumber, customer.netBalance, shopName, shopkeeperName, language) },
                                modifier = Modifier.weight(1f).height(48.dp),
                                shape = RoundedCornerShape(16.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF00C853)),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF00C853))
                            ) {
                                Text("WHATSAPP", fontWeight = FontWeight.Black, fontSize = 10.sp, letterSpacing = 1.sp)
                            }
                            OutlinedButton(
                                onClick = { sendSmsReminder(context, customer.phoneNumber, customer.netBalance, shopName, shopkeeperName, language) },
                                modifier = Modifier.weight(1f).height(48.dp),
                                shape = RoundedCornerShape(16.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2962FF)),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2962FF))
                            ) {
                                Text("TEXT SMS", fontWeight = FontWeight.Black, fontSize = 10.sp, letterSpacing = 1.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getReminderMessage(amount: Double, shopName: String?, shopkeeperName: String?, language: String): String {
    val sName = shopName ?: "our shop"
    val kName = shopkeeperName ?: "the shopkeeper"
    return when (language) {
        "Kannada" -> "ನಮಸ್ಕಾರ, ನಾನು $kName, $sName ನಲ್ಲಿ ನಿಮ್ಮ ಬಾಕಿ ₹$amount ಇದೆ. ದಯವಿಟ್ಟು ಆದಷ್ಟು ಬೇಗ ಪಾವತಿಸಿ."
        "Hindi" -> "नमस्कार, मैं $kName हूँ, $sName पर आपका ₹$amount बकाया है। कृपया इसे जल्द से जल्द चुकाएं।"
        else -> "Namaskara, i Am $kName, your due at $sName is ₹$amount Please clear it at your earliest convenience."
    }
}

fun sendWhatsAppReminder(context: Context, phoneNumber: String, amount: Double, shopName: String?, shopkeeperName: String?, language: String) {
    val message = getReminderMessage(amount, shopName, shopkeeperName, language)
    val encodedMessage = URLEncoder.encode(message, "UTF-8")
    val intent = Intent(Intent.ACTION_VIEW)
    val number = if (!phoneNumber.startsWith("+91")) "+91$phoneNumber" else phoneNumber
    intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$number&text=$encodedMessage")
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        sendSmsReminder(context, phoneNumber, amount, shopName, shopkeeperName, language)
    }
}

fun sendSmsReminder(context: Context, phoneNumber: String, amount: Double, shopName: String?, shopkeeperName: String?, language: String) {
    val message = getReminderMessage(amount, shopName, shopkeeperName, language)
    val encodedMessage = URLEncoder.encode(message, "UTF-8")
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("sms:$phoneNumber?body=$encodedMessage")
    context.startActivity(intent)
}

@Composable
fun CustomerCard(customer: CustomerWithBalance, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp).clip(RoundedCornerShape(14.dp)).background(Color(0xFFEBEBEB)),
                contentAlignment = Alignment.Center
            ) {
                val initial = if (customer.name.isNotEmpty()) customer.name.first().uppercase() else "?"
                Text(initial, fontWeight = FontWeight.Black, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(customer.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                Text(customer.phoneNumber, color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
            Column(horizontalAlignment = Alignment.End) {
                val isDue = customer.netBalance >= 0
                val amountText = if (isDue) "₹${customer.netBalance.toInt()}" else "₹${-customer.netBalance.toInt()}"
                val amountColor = if (isDue) CreditColor else PaymentColor
                
                Text(amountText, fontWeight = FontWeight.Black, fontSize = 18.sp, color = amountColor)
                Text(if (isDue) "DUE" else "ADVANCE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            }
        }
    }
}
