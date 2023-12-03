// Automatically generated - do not modify!

package web.fonts

import js.collections.MutableSetLike
import js.core.ReadonlyArray
import js.promise.Promise
import web.events.Event
import web.events.EventHandler
import web.events.EventTarget

/** [MDN Reference](https://developer.mozilla.org/docs/Web/API/FontFaceSet) */
external class FontFaceSet(
    initialFaces: ReadonlyArray<FontFace>,
) : EventTarget,
    MutableSetLike<FontFace> {
    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/FontFaceSet/loading_event) */
    var onloading: EventHandler<Event>?

    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/FontFaceSet/loadingdone_event) */
    var onloadingdone: EventHandler<Event>?

    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/FontFaceSet/loadingerror_event) */
    var onloadingerror: EventHandler<Event>?

    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/FontFaceSet/ready) */
    val ready: Promise<FontFaceSet>

    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/FontFaceSet/status) */
    val status: FontFaceSetLoadStatus

    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/FontFaceSet/check) */
    fun check(
        font: String,
        text: String = definedExternally,
    ): Boolean

    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/FontFaceSet/load) */
    fun load(
        font: String,
        text: String = definedExternally,
    ): Promise<ReadonlyArray<FontFace>>
}
