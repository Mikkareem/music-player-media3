package com.techullurgy.media3musicplayer.di

import android.annotation.SuppressLint
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import com.techullurgy.media3musicplayer.data.local.ContentResolverHelper
import com.techullurgy.media3musicplayer.data.repository.MusicRepository
import com.techullurgy.media3musicplayer.media_player.media_notification.MusicNotificationManager
import com.techullurgy.media3musicplayer.media_player.service.MusicServiceHandler
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


@SuppressLint("UnsafeOptInUsageError")
val appModule = module {
    single<AudioAttributes> {
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
    }

    single<Player> {
        ExoPlayer.Builder(get())
            .setAudioAttributes(get(), true)
            .setHandleAudioBecomingNoisy(true)
            .setTrackSelector(DefaultTrackSelector(get()))
            .build()
    }

    factory<MediaSession> {
        MediaSession.Builder(get(), get()).build()
    }

    single<MusicNotificationManager> {
        MusicNotificationManager(
            context = get(),
            exoPlayer = get()
        )
    }

    single<MusicServiceHandler> {
        MusicServiceHandler(get())
    }
}

val dataModule = module {
    singleOf(::ContentResolverHelper)
    singleOf(::MusicRepository)
}