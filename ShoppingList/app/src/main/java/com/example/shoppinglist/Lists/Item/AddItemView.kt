package com.example.shoppinglist.Lists.Item

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AddItemView(
    navController: NavController,
    listId: String,
    modifier: Modifier = Modifier
) {
    val viewModel: AIVModel = viewModel()
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = itemQuantity,
            onValueChange = { itemQuantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.addItem(listId, itemName, itemQuantity.toDoubleOrNull() ?: 0.0)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Item")
        }
    }
}