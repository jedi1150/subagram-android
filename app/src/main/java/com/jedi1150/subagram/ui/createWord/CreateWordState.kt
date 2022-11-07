package com.jedi1150.subagram.ui.createWord

data class CreateWordState(
    val input: String = String(),
    val error: CreateWordError? = null,
)
