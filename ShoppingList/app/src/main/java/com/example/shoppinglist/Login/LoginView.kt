package com.example.shoppinglist.Login
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shoppinglist.Screen

@Composable
fun LoginView(modifier: Modifier = Modifier,
              navController: NavController,
              onLoginSuccess: () -> Unit = {}) {

    val viewModel : LoginViewModel = viewModel()
    val state = viewModel.state.value

    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(value = state.email,
                onValueChange = {
                    viewModel.onEmailChange(it)
                },
                placeholder = {
                    Text("email")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = state.password,
                onValueChange = {
                    viewModel.onPasswordChange(it)
                },
                placeholder = {
                    Text("password")
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = viewModel::onLoginClick,
                content = {
                    Text("Login")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("Don't have an account?")
            TextButton(onClick = {navController.navigate(Screen.Register.route)}){
                Text("Register")
            }

            if (state.error != null)
                Text(state.error?:"")
            if (state.isLoading)
                CircularProgressIndicator()
        }
    }
}

