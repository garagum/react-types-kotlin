// Automatically generated - do not modify!

package typescript

import seskar.js.JsIntValue
import seskar.js.JsVirtual

@JsVirtual
sealed external interface WatchFileKind {
    companion object {
        @JsIntValue(0)
        val FixedPollingInterval: WatchFileKind

        @JsIntValue(1)
        val PriorityPollingInterval: WatchFileKind

        @JsIntValue(2)
        val DynamicPriorityPolling: WatchFileKind

        @JsIntValue(3)
        val FixedChunkSizePolling: WatchFileKind

        @JsIntValue(4)
        val UseFsEvents: WatchFileKind

        @JsIntValue(5)
        val UseFsEventsOnParentDirectory: WatchFileKind
    }
}
