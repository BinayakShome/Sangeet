package com.example.sangeet.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sangeet.data.Song
import com.example.sangeet.vm.SongListViewModel

@Composable
fun indiviualCategory(
    category: String,
    viewModel: SongListViewModel,
    onSongClick: (Song) -> Unit,
)
{
    val songs by viewModel.songListState.collectAsState()
    val filteredSongs = songs.filter { it.genre.toLowerCase() == category.toLowerCase() }
    Column (modifier = Modifier.fillMaxWidth()){
        Text(text = category,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp)
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow {
            items(filteredSongs) { song ->
                HomeSongCard(song = song) {
                    onSongClick(song)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = Color.LightGray, modifier = Modifier.padding(24.dp))
    }
}