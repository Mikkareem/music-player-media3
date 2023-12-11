package com.techullurgy.media3musicplayer.utils

sealed class HomeUIState{
    object InitialHome: HomeUIState()
    object HomeReady: HomeUIState()
}
