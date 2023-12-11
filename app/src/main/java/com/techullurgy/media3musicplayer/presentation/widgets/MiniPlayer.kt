package com.techullurgy.media3musicplayer.presentation.widgets

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.techullurgy.media3musicplayer.R
import com.techullurgy.media3musicplayer.data.local.models.AudioItem

@Composable
fun MiniPlayer(
    musicItem: AudioItem,
    progress: Float,
    onProgressCallback: (Float) -> Unit,
    isMusicPlaying: Boolean,
    onStartCallback: () -> Unit,
    onNextCallback: () -> Unit,
) {
    BottomAppBar(
        content = {
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ArtistInfoTab(
                        audioItem = musicItem,
                        modifier = Modifier.weight(1f)
                    )
                    MiniPlayerControls(
                        isMusicPlaying = isMusicPlaying,
                        onStartCallback = onStartCallback,
                        onNextCallback = onNextCallback
                    )
                }
                Slider(
                    value = progress,
                    valueRange = 0f..100f,
                    onValueChange = {
                        onProgressCallback(it)
                    }
                )
            }
        }
    )
}

@Composable
private fun MiniPlayerControls(
    modifier: Modifier = Modifier,
    isMusicPlaying: Boolean,
    onStartCallback: () -> Unit,
    onNextCallback: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MiniPlayerIcon(
            icon = if (isMusicPlaying) Icons.Default.Pause
            else Icons.Default.PlayArrow
        ) {
            onStartCallback()
        }
        Icon(
            modifier = Modifier
                .clickable {
                    onNextCallback()
                },
            imageVector = Icons.Default.SkipNext,
            contentDescription = "next"
        )
    }
}

@Composable
private fun ArtistInfoTab(
    audioItem: AudioItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (audioItem.artWork != null) {
            AsyncImage(
                model = audioItem.artWork,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
        } else {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.music_icon),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = audioItem.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
            Text(
                text = audioItem.artist,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun MiniPlayerIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClickCallback: () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .clickable {
                onClickCallback()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = "player icon")
    }
}

@Preview
@Composable
private fun PreviewMiniPlayer() {
    MaterialTheme {
        Surface(Modifier.background(MaterialTheme.colorScheme.background)) {
            MiniPlayer(
                musicItem = AudioItem(
                    0, Uri.parse(""), "Song Name", "Artist Name", 0, "title", "", null
                ),
                1.4f, { it }, false, {}
            ) {}
        }
    }
}
