package com.techullurgy.media3musicplayer.media_player.service

import android.content.Intent
import android.os.Build
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.techullurgy.media3musicplayer.media_player.media_notification.MusicNotificationManager
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent


class MediaService : MediaSessionService() {

    val mediaSession: MediaSession by inject<MediaSession>()

    val musicNotificationManager: MusicNotificationManager by inject<MusicNotificationManager>()

    @UnstableApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            musicNotificationManager.startMusicNotificationService(
                mediaSession = mediaSession,
                mediaSessionService = this
            )
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession = mediaSession


    override fun onDestroy() {
        super.onDestroy()
        mediaSession.apply {
            release()
            if (player.playbackState != Player.STATE_IDLE) {
                player.seekTo(0)
                player.playWhenReady = false
                player.stop()
            }
        }
    }
}