package com.example.sangeet.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sangeet.components.SongCard
import com.example.sangeet.data.Playlist
import com.example.sangeet.data.Song
import com.example.sangeet.vm.SongListViewModel

@Composable
fun PlaylistScreen(
    playlist: Playlist,
    onSongClick: (Song) -> Unit,
    onBack: () -> Unit,
    navController: NavController,
    viewModel: SongListViewModel
) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(16.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Text(
            text = playlist.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        LazyRow {
            items(playlist.songs) { song ->
                SongCard(song = song) {
                    onSongClick(song)
                }
            }
        }
    }
}
