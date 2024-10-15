package com.github.api_gateway.controller.util.marker

import jakarta.validation.GroupSequence
import jakarta.validation.groups.Default

/**
 * @author Raman Haurylau
 */
interface BaseMarker {
    interface First
    interface Second
    interface Third

    interface Create
    interface Update

    @GroupSequence(value = [Create::class, Default::class])
    interface onCreate

    @GroupSequence(value = [Update::class, Default::class])
    interface onUpdate
}