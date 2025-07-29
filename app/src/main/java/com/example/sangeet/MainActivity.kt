package com.example.sangeet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sangeet.data.Song
import com.example.sangeet.player.PlayerScreen
import com.example.sangeet.player.SongListScreen
import com.example.sangeet.ui.theme.SangeetTheme
import com.example.sangeet.vm.SongListViewModel
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SangeetTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val viewModel: SongListViewModel = viewModel()
                    val songs by viewModel.songListState.collectAsState()
                    var selectedSong by remember { mutableStateOf<Song?>(null) }

                    if (selectedSong == null) {
                        SongListScreen(
                            songs = songs,
                            onSongSelected = { selectedSong = it }
                        )
                    } else {
                        PlayerScreen(
                            audioUrl = selectedSong!!.streamUrl,
                            title = selectedSong!!.title,
                            onBack = { selectedSong = null }
                        )
                    }
                }
            }
        }
    }
}
