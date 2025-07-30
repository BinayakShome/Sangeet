package com.example.sangeet.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sangeet.data.Playlist
import com.example.sangeet.player.HomeScreen
import com.example.sangeet.player.PlayerScreen
import com.example.sangeet.player.PlaylistScreen
import com.example.sangeet.vm.SongListViewModel

@Composable
fun AppNavHost(navController: NavHostController, viewModel: SongListViewModel) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onSongClick = { song ->
                    // Navigate to player screen with audioUrl and title as arguments
                    val encodedUrl = Uri.encode(song.streamUrl)
                    val encodedTitle = Uri.encode(song.title)
                    navController.navigate("player/$encodedUrl/$encodedTitle")
                }
            )
        }

        composable(
            "player/{audioUrl}/{title}",
            arguments = listOf(
                navArgument("audioUrl") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val audioUrl = backStackEntry.arguments?.getString("audioUrl") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: "Unknown Title"

            PlayerScreen(
                audioUrl = audioUrl,
                title = title,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

