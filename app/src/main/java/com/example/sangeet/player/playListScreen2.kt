package com.example.sangeet.player

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sangeet.components.SongCard
import com.example.sangeet.data.Song
import com.example.sangeet.ui.theme.GoldenYellow
import com.example.sangeet.vm.SongListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun playListscreen2(
    category: String,
    viewModel: SongListViewModel,
    onSongClick: (Song) -> Unit,
    onBack: () -> Unit,
    onPlayAllClick: (List<Song>) -> Unit
)
{
    val songs by viewModel.songListState.collectAsState()
    val filteredSongs = songs.filter { it.genre.toLowerCase() == category.toLowerCase() }

    Scaffold(
        topBar = {
            Spacer(modifier = Modifier.height(32.dp))
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { onBack() },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Navigate Back",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = category, fontWeight = FontWeight.Bold,
                            fontSize = 28.sp, color = GoldenYellow
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        modifier = Modifier.padding(top = 8.dp)
    )
    {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.DarkGray).padding(top = 132.dp)
        ) {
            LazyColumn {
                item {
                    if (filteredSongs.isNotEmpty()) {
                        Button(
                            onClick = {
                                onPlayAllClick
                            },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Play All")
                        }
                    }
                }
                items(filteredSongs) { song ->
                    SongCard(song = song) {
                        onSongClick(song)
                    }
                }
            }
        }
    }
}