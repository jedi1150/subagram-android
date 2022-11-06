package com.jedi1150.subagram.ui

sealed class Screen(
    val route: String,
    val title: String,
) {
    companion object {
        fun find(screenRoute: String) =
            Screen::class.sealedSubclasses
                .map { it.objectInstance }
                .firstOrNull { it?.route == screenRoute }
    }

    object Home : Screen("home", "Subagram")
    object CreateWord : Screen("create-word", "Create word")
    data class Word(private val value: String = String()) : Screen("word", title = value)
}