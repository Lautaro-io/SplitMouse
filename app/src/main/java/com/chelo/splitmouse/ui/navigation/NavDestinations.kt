package com.chelo.splitmouse.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
object Home

@Serializable
data class EventDetail(val eventId : Long)