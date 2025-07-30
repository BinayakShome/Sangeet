package com.example.sangeet.navigation

sealed class Screen(val route: String)
{
    object LoginScreen : Screen("Loginscreen")
}