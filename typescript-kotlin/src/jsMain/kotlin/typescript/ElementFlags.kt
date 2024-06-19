// Automatically generated - do not modify!

package typescript

import seskar.js.JsIntValue

sealed external interface ElementFlags {
    companion object {
        @JsIntValue(1)
        val Required: ElementFlags

        @JsIntValue(2)
        val Optional: ElementFlags

        @JsIntValue(4)
        val Rest: ElementFlags

        @JsIntValue(8)
        val Variadic: ElementFlags

        @JsIntValue(3)
        val Fixed: ElementFlags

        @JsIntValue(12)
        val Variable: ElementFlags

        @JsIntValue(14)
        val NonRequired: ElementFlags

        @JsIntValue(11)
        val NonRest: ElementFlags
    }
}
