// Automatically generated - do not modify!

package web.svg

/**
 * Corresponds to the <feTurbulence> element.
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/SVGFETurbulenceElement)
 */
sealed external class SVGFETurbulenceElement :
    SVGElement,
    SVGFilterPrimitiveStandardAttributes {
    val baseFrequencyX: SVGAnimatedNumber
    val baseFrequencyY: SVGAnimatedNumber
    val numOctaves: SVGAnimatedInteger
    val seed: SVGAnimatedNumber
    val stitchTiles: SVGAnimatedEnumeration<Short>
    val type: SVGAnimatedEnumeration<Short>
    val SVG_TURBULENCE_TYPE_UNKNOWN: Short
    val SVG_TURBULENCE_TYPE_FRACTALNOISE: Short
    val SVG_TURBULENCE_TYPE_TURBULENCE: Short
    val SVG_STITCHTYPE_UNKNOWN: Short
    val SVG_STITCHTYPE_STITCH: Short
    val SVG_STITCHTYPE_NOSTITCH: Short

    companion object {
        val SVG_TURBULENCE_TYPE_UNKNOWN: Short
        val SVG_TURBULENCE_TYPE_FRACTALNOISE: Short
        val SVG_TURBULENCE_TYPE_TURBULENCE: Short
        val SVG_STITCHTYPE_UNKNOWN: Short
        val SVG_STITCHTYPE_STITCH: Short
        val SVG_STITCHTYPE_NOSTITCH: Short
    }
}
