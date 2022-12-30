// Automatically generated - do not modify!

@file:Suppress(
    "EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER",
)

package web.storage

import web.events.Event
import web.events.EventInit
import web.events.EventType
import web.url.URL

external interface StorageEventInit : EventInit {
    var key: String?
    var newValue: String?
    var oldValue: String?
    var storageArea: Storage?
    var url: String?
}

open external class StorageEvent(
    override val type: EventType<StorageEvent>,
    init: StorageEventInit = definedExternally,
) : Event {
    /** Returns the key of the storage item being changed. */
    val key: String?

    /** Returns the new value of the key of the storage item whose value is being changed. */
    val newValue: String?

    /** Returns the old value of the key of the storage item whose value is being changed. */
    val oldValue: String?

    /** Returns the Storage object that was affected. */
    val storageArea: Storage?

    /** Returns the URL of the document whose storage item changed. */
    val url: String
    fun initStorageEvent(
        type: String,
        bubbles: Boolean = definedExternally,
        cancelable: Boolean = definedExternally,
        key: String? = definedExternally,
        oldValue: String? = definedExternally,
        newValue: String? = definedExternally,
        url: URL,
        storageArea: Storage? = definedExternally,
    )

    companion object
}
