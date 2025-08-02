package com.example.sangeet.player

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sangeet.components.SongCard
import com.example.sangeet.data.Song
import com.example.sangeet.vm.SongListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen2(
    navController: NavController,
    viewModel: SongListViewModel
)
{
    val categories = listOf("Romance", "Lo-fi", "Devotional", "Chill", "Party")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Good Evening",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Binayak",
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.DarkGray).padding(top = 132.dp))
        {
            LazyColumn(
                modifier = Modifier
            ) {
                item {
                    Text(text = "What's your mood upto",
                        fontWeight = FontWeight.ExtraBold,
                        //fontStyle = FontStyle.Italic,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color(0xFFCC7722)
                    )
                    Spacer(Modifier.height(24.dp))
                }

                item {
                    LazyRow {
                        items(categories){ category ->
                            PlayCard(
                                category = category,
                                onClick = {
                                    Log.d("NAVIGATION", "Navigating to playlist_screen/$category")
                                    navController.navigate("playlistscreen/$category") }
                            )
                        }
                    }
                }

                items(categories) {category ->
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = category,
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Italic)

                }
            }
        }
    }
}

@Composable
fun PlayCard(
    category: String,
    onClick: () -> Unit
)
{
    Card (
        modifier = Modifier
            .padding(8.dp)
            .size(320.dp, 200.dp)
            .clickable {onClick()}
    ){
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta),
            contentAlignment = Alignment.BottomCenter
        ){
            Text(text = category,
                fontSize = 32.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold
                )
        }
    }
}

@Composable
fun playlistscreen(
    category: String,
    viewModel: SongListViewModel,
    onSongClick: (Song) -> Unit
)
{
    val songs by viewModel.songListState.collectAsState()
    val filteredSongs = songs.filter { it.genre.toLowerCase() == category.toLowerCase() }

    LazyColumn {
        items(filteredSongs) { song ->
            SongCard(song = song) {
                onSongClick(song)
            }
        }
    }
}