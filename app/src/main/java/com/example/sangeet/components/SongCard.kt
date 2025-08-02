package com.example.sangeet.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sangeet.R
import com.example.sangeet.data.Song

@Composable
fun SongCard(song: Song, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier
            .padding(8.dp)
        ) {
            Row {
            Image(
                painter = painterResource(id = R.drawable.music_icon),
                contentDescription = "Music Icon",
                modifier = Modifier.fillMaxSize(.13f)
                    .padding(top = 0.dp)// You can adjust the size
            )
                Spacer(modifier = Modifier.width(18.dp))
            Text(
                text = song.title,
                style = MaterialTheme.typography.headlineMedium
            )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    val sampleSong = Song(
        title = "Sample Song",
    )

    SongCard(song = sampleSong, onClick = {})
}