// Automatically generated - do not modify!

package web.codecs

import js.buffer.AllowSharedBufferSource
import js.core.JsLong

sealed external interface EncodedVideoChunkInit {
    var data: AllowSharedBufferSource
    var duration: JsLong?
    var timestamp: JsLong
    var type: EncodedVideoChunkType
}
