package com.example.sangeet.player

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sangeet.components.SongCard
import com.example.sangeet.data.Song
import com.example.sangeet.vm.SongListViewModel

@Composable
fun SongListScreen(
    songs: List<Song>,
    onSongClick: (Song) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(8.dp)
    ) {
        items(songs) { song ->
            SongCard (song = song, onClick = { onSongClick(song) })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}