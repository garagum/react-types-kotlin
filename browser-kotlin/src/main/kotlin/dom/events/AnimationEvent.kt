// Automatically generated - do not modify!

@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package dom.events

import kotlinx.js.HighResTimeStamp
import org.w3c.dom.EventInit
import web.events.Event

external interface AnimationEventInit : EventInit {
    var animationName: String?
    var elapsedTime: HighResTimeStamp?
    var pseudoElement: String?
}

external class AnimationEvent(
    type: String,
    init: AnimationEventInit = definedExternally,
) : Event {
    val animationName: String
    val elapsedTime: HighResTimeStamp
    val pseudoElement: String

    companion object
}
