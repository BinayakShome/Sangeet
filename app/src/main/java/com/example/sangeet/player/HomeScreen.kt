package com.example.sangeet.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sangeet.components.CategoryCard
import com.example.sangeet.components.SongCard
import com.example.sangeet.data.Song
import com.example.sangeet.vm.SongListViewModel

@Composable
fun HomeScreen(viewModel: SongListViewModel, onSongClick: (Song) -> Unit) {
    val songs by viewModel.songListState.collectAsState()
    val categories = listOf("Romance", "Lo-fi", "Devotional", "Chill", "Party")


    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        LazyRow(
            modifier = Modifier.padding(16.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category = category) {
                    // TODO: Filter songs by category or navigate to playlist screen
                }
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(songs) { song ->
                SongCard(song = song) {
                    onSongClick(song)
                }
            }
        }
    }

//    val currentSong by viewModel.currentSong.collectAsState()
//    val isPlaying by viewModel.isPlaying.collectAsState()
//
//    currentSong?.let { song ->
////        MiniPlayer(
////            song = song,
////            isPlaying = isPlaying,
////            onPlayPauseClicked = { viewModel.togglePlayPause() }
////        )
//    }
}
