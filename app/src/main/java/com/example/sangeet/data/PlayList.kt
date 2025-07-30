package com.example.sangeet.data

data class Playlist(
    val title: String = "",
    val songs: List<Song> = emptyList()
)