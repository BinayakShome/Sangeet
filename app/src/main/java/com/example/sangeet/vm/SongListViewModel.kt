package com.example.sangeet.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sangeet.data.NetworkUtils
import com.example.sangeet.data.Playlist
import com.example.sangeet.data.Song
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SongListViewModel : ViewModel() {

    private val _songListState = MutableStateFlow<List<Song>>(emptyList())
    val songListState: StateFlow<List<Song>> = _songListState.asStateFlow()

    private val db = FirebaseFirestore.getInstance()

    init {
        fetchSongs()
    }

    private fun fetchSongs() {
        db.collection("songs")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.e("Firestore", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val songs = snapshots?.documents?.mapNotNull {
                    it.toObject(Song::class.java)
                }.orEmpty()

                _songListState.value = songs
            }
    }

    // Returns a playlist (filtered songs by genre)
    fun getPlaylistByName(name: String): Playlist {
        val songs = _songListState.value.filter { it.genre == name }
        return Playlist(title = name, songs = songs)
    }

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    fun playSong(song: Song) {
        _currentSong.value = song
        _isPlaying.value = true
    }

    private val _showNoInternet = MutableStateFlow(false)
    val showNoInternet: StateFlow<Boolean> = _showNoInternet
    fun checkInternetAvailability(context: Context) {
        viewModelScope.launch {
            _showNoInternet.value = !NetworkUtils.isInternetAvailable(context)
        }
    }

    private var playQueue: List<Song> = emptyList()
    private var currentIndex = 0

    fun playAllSongs(genre: String) {
        playQueue = _songListState.value.filter { it.genre.equals(genre, ignoreCase = true) }
        currentIndex = 0

        if (playQueue.isNotEmpty()) {
            playSong(playQueue[currentIndex])
        }
    }

    fun playNextSong() {
        if (currentIndex + 1 < playQueue.size) {
            currentIndex++
            playSong(playQueue[currentIndex])
        } else {
            // End of playlist
            _isPlaying.value = false
        }
    }
}