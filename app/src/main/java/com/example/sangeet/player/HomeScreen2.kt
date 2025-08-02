package com.example.sangeet.player

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sangeet.components.SongCard
import com.example.sangeet.data.Song
import com.example.sangeet.ui.theme.GoldenYellow
import com.example.sangeet.ui.theme.SunsetOrange
import com.example.sangeet.vm.SongListViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen2(
    navController: NavController,
    viewModel: SongListViewModel
)
{
    val categories = listOf("Romance", "Lo-fi", "Devotional", "Chill", "Party", "Bengali", "HollyWood", "Old 90s")
    var currentHour by remember { mutableStateOf(getCurrentHour()) }
    var greet by remember { mutableStateOf("Hello") }

    //For current hour
    LaunchedEffect(currentHour) {
        greet = when {
            currentHour in 4..11 -> "Morning"
            currentHour in 12..16 -> "Afternoon"
            currentHour in 17..21 -> "Evening"
            else -> "Night"
        }
    }
    Scaffold(
        topBar = {
            Spacer(modifier = Modifier.height(32.dp))
            TopAppBar(
                title = {

                    Column {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color.White)) {
                                    append("Good")
                                }
                                withStyle(style = SpanStyle(color = GoldenYellow)) {
                                    append(greet)
                                }
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,

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
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = GoldenYellow)) {
                                append("Pick a mood,")
                            }
                            withStyle(style = SpanStyle(color = SunsetOrange)) {
                                append("\n      Set the groove!")
                            }
                        },
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(start = 8.dp),
                        color = SunsetOrange
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
                .background(Color.Transparent),
            contentAlignment = Alignment.BottomEnd
        ){
            Text(text = category,
                fontSize = 32.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
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

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentHour(): Int {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH") // Use "HH" for 24-hour format
    return currentDateTime.format(formatter).toInt() // Format to display only the hour
}