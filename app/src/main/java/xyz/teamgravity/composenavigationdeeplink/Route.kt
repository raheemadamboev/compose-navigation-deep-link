package xyz.teamgravity.composenavigationdeeplink

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home

    @Serializable
    data class DeepLink(
        val id: Int
    )
}