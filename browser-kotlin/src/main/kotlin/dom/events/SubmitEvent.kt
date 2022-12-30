// Automatically generated - do not modify!

@file:Suppress(
    "EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER",
)

package dom.events

import web.events.Event
import web.events.EventInit
import web.events.EventType
import web.html.HTMLElement

external interface SubmitEventInit : EventInit {
    var submitter: HTMLElement?
}

open external class SubmitEvent(
    override val type: EventType<SubmitEvent>,
    init: SubmitEventInit = definedExternally,
) : Event {
    /** Returns the element representing the submit button that triggered the form submission, or null if the submission was not triggered by a button. */
    val submitter: HTMLElement?

    companion object
}
