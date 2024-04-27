// Automatically generated - do not modify!

package actions.core

import seskar.js.JsIntValue
import seskar.js.JsVirtual

@JsVirtual
sealed external interface ExitCode {
    companion object {
        /**
         * A code indicating that the action was successful
         */
        @JsIntValue(0)
        val Success: ExitCode

        /**
         * A code indicating that the action was a failure
         */
        @JsIntValue(1)
        val Failure: ExitCode
    }
}
