package com.jedi1150.subagram.ui

import com.jedi1150.subagram.R
import com.jedi1150.subagram.utils.Shared.getResource

sealed class Screen(
    val route: String,
    val title: String,
) {
    object Home : Screen("home", getResource.getString(R.string.app_name))
    object CreateWord : Screen("create-word", getResource.getString(R.string.screen_create_word_title))
    data class Word(private val value: String = String()) : Screen("word", title = value)
    object Settings: Screen("settings", getResource.getString(R.string.screen_settings_title))
}
