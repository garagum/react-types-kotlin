// Automatically generated - do not modify!

package web.gpu

sealed external interface GPURenderCommandsMixin {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPURenderBundleEncoder/draw)
     */
    fun draw(
        vertexCount: GPUSize32,
        instanceCount: GPUSize32 = definedExternally,
        firstVertex: GPUSize32 = definedExternally,
        firstInstance: GPUSize32 = definedExternally,
    )

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPURenderBundleEncoder/drawIndexed)
     */
    fun drawIndexed(
        indexCount: GPUSize32,
        instanceCount: GPUSize32 = definedExternally,
        firstIndex: GPUSize32 = definedExternally,
        baseVertex: GPUSignedOffset32 = definedExternally,
        firstInstance: GPUSize32 = definedExternally,
    )

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPURenderBundleEncoder/drawIndexedIndirect)
     */
    fun drawIndexedIndirect(
        indirectBuffer: GPUBuffer,
        indirectOffset: GPUSize64,
    )

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPURenderBundleEncoder/drawIndirect)
     */
    fun drawIndirect(
        indirectBuffer: GPUBuffer,
        indirectOffset: GPUSize64,
    )

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPURenderBundleEncoder/setIndexBuffer)
     */
    fun setIndexBuffer(
        buffer: GPUBuffer,
        indexFormat: GPUIndexFormat,
        offset: GPUSize64 = definedExternally,
        size: GPUSize64 = definedExternally,
    )

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPURenderBundleEncoder/setPipeline)
     */
    fun setPipeline(pipeline: GPURenderPipeline)

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPURenderBundleEncoder/setVertexBuffer)
     */
    fun setVertexBuffer(
        slot: GPUIndex32,
        buffer: GPUBuffer?,
        offset: GPUSize64 = definedExternally,
        size: GPUSize64 = definedExternally,
    )
}
