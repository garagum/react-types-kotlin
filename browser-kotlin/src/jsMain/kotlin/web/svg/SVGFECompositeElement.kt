// Automatically generated - do not modify!

package web.svg

/**
 * Corresponds to the <feComposite> element.
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/SVGFECompositeElement)
 */
sealed external class SVGFECompositeElement :
    SVGElement,
    SVGFilterPrimitiveStandardAttributes {
    val in1: SVGAnimatedString
    val in2: SVGAnimatedString
    val k1: SVGAnimatedNumber
    val k2: SVGAnimatedNumber
    val k3: SVGAnimatedNumber
    val k4: SVGAnimatedNumber
    val operator: SVGAnimatedEnumeration<Short>
    val SVG_FECOMPOSITE_OPERATOR_UNKNOWN: Short
    val SVG_FECOMPOSITE_OPERATOR_OVER: Short
    val SVG_FECOMPOSITE_OPERATOR_IN: Short
    val SVG_FECOMPOSITE_OPERATOR_OUT: Short
    val SVG_FECOMPOSITE_OPERATOR_ATOP: Short
    val SVG_FECOMPOSITE_OPERATOR_XOR: Short
    val SVG_FECOMPOSITE_OPERATOR_ARITHMETIC: Short

    companion object {
        val SVG_FECOMPOSITE_OPERATOR_UNKNOWN: Short
        val SVG_FECOMPOSITE_OPERATOR_OVER: Short
        val SVG_FECOMPOSITE_OPERATOR_IN: Short
        val SVG_FECOMPOSITE_OPERATOR_OUT: Short
        val SVG_FECOMPOSITE_OPERATOR_ATOP: Short
        val SVG_FECOMPOSITE_OPERATOR_XOR: Short
        val SVG_FECOMPOSITE_OPERATOR_ARITHMETIC: Short
    }
}
