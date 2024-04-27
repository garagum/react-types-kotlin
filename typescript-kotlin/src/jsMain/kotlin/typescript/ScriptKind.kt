// Automatically generated - do not modify!

package typescript

import seskar.js.JsIntValue
import seskar.js.JsVirtual

@JsVirtual
sealed external interface ScriptKind {
    companion object {
        @JsIntValue(0)
        val Unknown: ScriptKind

        @JsIntValue(1)
        val JS: ScriptKind

        @JsIntValue(2)
        val JSX: ScriptKind

        @JsIntValue(3)
        val TS: ScriptKind

        @JsIntValue(4)
        val TSX: ScriptKind

        @JsIntValue(5)
        val External: ScriptKind

        @JsIntValue(6)
        val JSON: ScriptKind

        /**
         * Used on extensions that doesn't define the ScriptKind but the content defines it.
         * Deferred extensions are going to be included in all project contexts.
         */
        @JsIntValue(7)
        val Deferred: ScriptKind
    }
}
