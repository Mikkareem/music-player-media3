package com.techullurgy.media3musicplayer.data.repository

import com.techullurgy.media3musicplayer.data.local.ContentResolverHelper
import com.techullurgy.media3musicplayer.data.local.models.AudioItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MusicRepository(
    private val contentResolverHelper: ContentResolverHelper,
) {
    suspend fun getAudioData(): List<AudioItem> = withContext(Dispatchers.IO) {
        contentResolverHelper.getAudioData()
    }
}