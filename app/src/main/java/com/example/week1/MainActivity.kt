package com.example.week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week1.ui.theme.Week1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Ensure edge-to-edge UI
        setContent {
            Week1Theme {
                AppScaffold()
            }
        }
    }
}

@Composable
fun AppScaffold() {
    var isLoggedIn by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (isLoggedIn) {
            PostScreen(
                modifier = Modifier.padding(innerPadding),
                postFunction = { post -> println("Posted: $post") },
                logoutFunction = { isLoggedIn = false }
            )
        } else {
            LoginScreen(
                modifier = Modifier.padding(innerPadding),
                loginFunction = { username, password ->
                    println("Logging in with: $username, $password")
                    isLoggedIn = true
                },
                signupFunction = { username, password ->
                    println("Signing up with: $username, $password")
                }
            )
        }
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginFunction: (String, String) -> Unit,
    signupFunction: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                loginFunction(username, password)
                username = ""
                password = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        TextButton(
            onClick = {
                signupFunction(username, password)
                username = ""
                password = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Signup")
        }
    }
}

@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    postFunction: (String) -> Unit,
    logoutFunction: () -> Unit
) {
    var post by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = post,
            onValueChange = { post = it },
            label = { Text("Post") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                postFunction(post)
                post = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Post")
        }
        TextButton(
            onClick = logoutFunction,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Logout")
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    Week1Theme {
        LoginScreen(loginFunction = { _, _ -> }, signupFunction = { _, _ -> })
    }
}

@Preview
@Composable
fun PreviewPostScreen() {
    Week1Theme {
        PostScreen(postFunction = {}, logoutFunction = {})
    }
}