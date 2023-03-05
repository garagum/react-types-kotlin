// Automatically generated - do not modify!

package web.cssom

import js.core.ArrayLike
import js.core.ReadonlyArray
import web.geometry.DOMMatrix

external class CSSTransformValue(
    transforms: ReadonlyArray<CSSTransformComponent>,
) : CSSStyleValue,
    ArrayLike<CSSTransformComponent> {
    val is2D: Boolean
    fun toMatrix(): DOMMatrix
    fun forEach(action: (item: CSSTransformComponent) -> Unit)
}
