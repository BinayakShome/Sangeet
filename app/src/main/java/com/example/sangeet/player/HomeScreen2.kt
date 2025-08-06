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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.sangeet.R
import com.example.sangeet.components.BottomSignature
import com.example.sangeet.components.NoInternet
import com.example.sangeet.components.PlayCard
import com.example.sangeet.components.indiviualCategory
import com.example.sangeet.data.Song
import com.example.sangeet.ui.theme.GoldenYellow
import com.example.sangeet.ui.theme.SunsetOrange
import com.example.sangeet.vm.SongListViewModel
import com.google.firebase.components.Lazy
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen2(
    navController: NavController,
    viewModel: SongListViewModel,
    onSongClick: (Song) -> Unit
) {
    val context = LocalContext.current
    val categories = listOf("Romance", "Devotional", "Gear Up", "Party", "Bengali", "HollyWood", "Old 90s", "Emotional")
    var currentHour by remember { mutableStateOf(getCurrentHour()) }
    var greet by remember { mutableStateOf("Hello") }
    val showNoInternet by viewModel.showNoInternet.collectAsState()


    LaunchedEffect(currentHour) {
        greet = when (currentHour) {
            in 4..11 -> "Morning"
            in 12..16 -> "Afternoon"
            in 17..21 -> "Evening"
            else -> "Night"
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkInternetAvailability(context)
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(top = 132.dp)
        ) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ){
                when {
                    showNoInternet -> {
                        item { NoInternet() }
                        Log.d("internet","no internet detected")
                    }
                    else -> {
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
                                items(categories) { category ->
                                    PlayCard(
                                        category = category,
                                        onClick = {
                                            Log.d("NAVIGATION", "Navigating to playlist_screen/$category")
                                            navController.navigate("playListscreen2/$category")
                                        }
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                        }

                        items(categories) { category ->
                            Spacer(modifier = Modifier.height(14.dp))
                            indiviualCategory(category = category,
                                viewModel = viewModel,
                                onSongClick = {song ->
                                    onSongClick(song)
                                })
                        }

                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                            BottomSignature()
                            Spacer(modifier = Modifier.height(60.dp))
                        }
                    }
                }
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