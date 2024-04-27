// Automatically generated - do not modify!

package web.http

import seskar.js.JsValue
import seskar.js.JsVirtual

@JsVirtual
sealed external interface RequestMode {
    companion object {
        @JsValue("cors")
        val cors: RequestMode

        @JsValue("navigate")
        val navigate: RequestMode

        @JsValue("no-cors")
        val noCors: RequestMode

        @JsValue("same-origin")
        val sameOrigin: RequestMode
    }
}
