package com.example.movieapplication.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapplication.ui.route.AppView
import com.example.movieapplication.ui.viewmodel.LoginViewModel

@Composable
fun LoginView(
    viewModel: LoginViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
){

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    val isEmailValid = isValidEmail(email)
    val isPasswordValid = isValidPassword(password)
    val formValid = isEmailValid && isPasswordValid

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ){
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            isError = email.isNotEmpty() && !isEmailValid,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password"
                    )
                }
            },
            isError = false,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.submit(email, password, navController){ token ->
                    if (token.isEmpty()) {
                        showDialog = true
                    }
                }
            },
            enabled = formValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ){
            Text("Submit")
        }

        Text(
            buildAnnotatedString {
                append("Don't have an account? ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Sign up")
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp)
                .clickable {
                    navController.navigate(AppView.Register.name)
                },
            textAlign = TextAlign.Center
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Login Failed") },
            text = { Text("Invalid email or password") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {Text("OK") }
            }
        )
    }
}

private fun isValidEmail(email: String): Boolean {
    return email.trim().matches(Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+$"))
}

private fun isValidPassword(password: String): Boolean {
    return password.length >= 8
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun LoginPreview(){
    LoginView()
}