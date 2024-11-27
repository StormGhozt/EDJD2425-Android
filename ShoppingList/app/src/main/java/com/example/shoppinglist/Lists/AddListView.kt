package com.example.shoppinglist.Lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddListView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    val viewModel: ALVModel = viewModel()
    val state = viewModel.state.value

    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(16.dp)
        ) {
            Text(
                text = "Add List",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterStart),
                color = Color.White
            )
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_keyboard_return_24),
                    contentDescription = "Return",
                    tint = Color.White
                )
            }
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text("Enter list name")
            },
            value = state.name,
            onValueChange = viewModel::onNameChange
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                if (state.name.isNotBlank()) {
                    viewModel.addList()
                    navController.popBackStack()
                }
            }) {
            Text("Add")
        }
    }
}