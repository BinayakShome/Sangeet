package com.example.sangeet

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.sangeet.navigation.AppNavHost
import com.example.sangeet.ui.theme.SangeetTheme
import com.example.sangeet.vm.SongListViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SangeetTheme {
                val navController = rememberNavController()
                val viewModel: SongListViewModel = viewModel()

                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}