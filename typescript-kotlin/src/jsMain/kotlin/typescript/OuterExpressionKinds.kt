// Automatically generated - do not modify!

package typescript

import seskar.js.JsIntValue
import seskar.js.JsVirtual

@JsVirtual
sealed external interface OuterExpressionKinds {
    companion object {
        @JsIntValue(1)
        val Parentheses: OuterExpressionKinds

        @JsIntValue(2)
        val TypeAssertions: OuterExpressionKinds

        @JsIntValue(4)
        val NonNullAssertions: OuterExpressionKinds

        @JsIntValue(8)
        val PartiallyEmittedExpressions: OuterExpressionKinds

        @JsIntValue(6)
        val Assertions: OuterExpressionKinds

        @JsIntValue(15)
        val All: OuterExpressionKinds

        @JsIntValue(16)
        val ExcludeJSDocTypeAssertion: OuterExpressionKinds
    }
}
