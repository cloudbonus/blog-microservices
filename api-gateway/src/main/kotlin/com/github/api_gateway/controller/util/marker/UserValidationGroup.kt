package com.github.api_gateway.controller.util.marker

import jakarta.validation.GroupSequence
import jakarta.validation.groups.Default

/**
 * @author Raman Haurylau
 */
interface UserValidationGroup {
    @GroupSequence(value = [BaseMarker.First::class, Default::class, BaseMarker.Second::class, BaseMarker.Third::class])
    interface onCreate

    @GroupSequence(value = [Default::class, BaseMarker.Second::class, BaseMarker.Third::class])
    interface onUpdate

    @GroupSequence(value = [BaseMarker.First::class, Default::class])
    interface onAuthenticate
}