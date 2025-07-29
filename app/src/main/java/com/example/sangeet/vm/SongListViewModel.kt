package com.example.sangeet.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sangeet.data.Song
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SongListViewModel : ViewModel() {

    private val _songListState = MutableStateFlow<List<Song>>(emptyList())
    val songListState = _songListState.asStateFlow()

    private val db = FirebaseFirestore.getInstance()

    init {
        fetchSongs()
    }

    private fun fetchSongs() {
        db.collection("songs")
            .get()
            .addOnSuccessListener { result ->
                val songs = result.documents.mapNotNull { it.toObject(Song::class.java) }
                _songListState.value = songs
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Failed to fetch songs", exception)
            }
    }
}
