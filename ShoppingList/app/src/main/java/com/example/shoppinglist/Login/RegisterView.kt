package com.example.shoppinglist.Login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun RegisterView(
    modifier: Modifier = Modifier,
    onRegisterSuccess: () -> Unit = {}
) {
    val viewModel: RegisterViewModel = viewModel()
    val state = viewModel.state.collectAsState().value

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onRegisterClick(onRegisterSuccess) },
                content = { Text("Register") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (state.error != null) {
                Text(state.error ?: "")
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RegisterViewPreview() {
    ShoppingListTheme {
        RegisterView()
    }
}