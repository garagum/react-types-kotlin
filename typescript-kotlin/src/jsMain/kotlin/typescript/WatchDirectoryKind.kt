// Automatically generated - do not modify!

package typescript

import seskar.js.JsIntValue
import seskar.js.JsVirtual

@JsVirtual
sealed external interface WatchDirectoryKind {
    companion object {
        @JsIntValue(0)
        val UseFsEvents: WatchDirectoryKind

        @JsIntValue(1)
        val FixedPollingInterval: WatchDirectoryKind

        @JsIntValue(2)
        val DynamicPriorityPolling: WatchDirectoryKind

        @JsIntValue(3)
        val FixedChunkSizePolling: WatchDirectoryKind
    }
}
