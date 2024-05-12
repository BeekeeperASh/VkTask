package com.example.vktask.presentation.detail_screen

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AudioPlaySection(
    modifier: Modifier = Modifier,
    audioUrl: String
) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Play the pokemon sound",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start
        )
        IconButton(onClick = {
            val mediaPlayer: MediaPlayer = MediaPlayer()
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            try {
                mediaPlayer.setDataSource(context, Uri.parse(audioUrl))
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: Exception){
                e.printStackTrace()
            }
        }) {
            Icon(
                imageVector = Icons.Default.PlayCircle,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }
    }
    
}