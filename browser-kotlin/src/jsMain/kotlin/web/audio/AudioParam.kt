// Automatically generated - do not modify!

package web.audio

import js.array.ReadonlyArray
import js.typedarrays.Float32Array

/**
 * The Web Audio API's AudioParam interface represents an audio-related parameter, usually a parameter of an AudioNode (such as GainNode.gain).
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam)
 */
sealed external class AudioParam {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/automationRate)
     */
    var automationRate: AutomationRate

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/defaultValue)
     */
    val defaultValue: Double

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/maxValue)
     */
    val maxValue: Double

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/minValue)
     */
    val minValue: Double

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/value)
     */
    var value: Double

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/cancelAndHoldAtTime)
     */
    fun cancelAndHoldAtTime(cancelTime: Double): AudioParam

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/cancelScheduledValues)
     */
    fun cancelScheduledValues(cancelTime: Double): AudioParam

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/exponentialRampToValueAtTime)
     */
    fun exponentialRampToValueAtTime(
        value: Number,
        endTime: Double,
    ): AudioParam

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/linearRampToValueAtTime)
     */
    fun linearRampToValueAtTime(
        value: Number,
        endTime: Double,
    ): AudioParam

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/setTargetAtTime)
     */
    fun setTargetAtTime(
        target: Number,
        startTime: Double,
        timeConstant: Number,
    ): AudioParam

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/setValueAtTime)
     */
    fun setValueAtTime(
        value: Number,
        startTime: Double,
    ): AudioParam

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/AudioParam/setValueCurveAtTime)
     */
    fun setValueCurveAtTime(
        values: ReadonlyArray<Double>,
        startTime: Double,
        duration: Double,
    ): AudioParam

    fun setValueCurveAtTime(
        values: Float32Array,
        startTime: Double,
        duration: Double,
    ): AudioParam
}
