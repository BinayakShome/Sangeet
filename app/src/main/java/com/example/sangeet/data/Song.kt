package com.example.sangeet.data

data class Song(
    val title: String = "",
    //val artist: String = "",
    val driveId: String = "",
    val coverUrl: String = "",
    val genre: String = "",
) {
    val streamUrl: String
        get() = "https://drive.google.com/uc?export=download&id=$driveId"
}