// Automatically generated - do not modify!

package web.streams

import js.buffer.ArrayBufferView
import js.promise.Promise

/**
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/ReadableStreamBYOBReader)
 */
external class ReadableStreamBYOBReader(
    stream: ReadableStream<*>,
) : ReadableStreamGenericReader {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/ReadableStreamBYOBReader/read)
     */
    fun <T : ArrayBufferView> read(view: T): Promise<ReadableStreamReadResult<T>>

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/ReadableStreamBYOBReader/releaseLock)
     */
    fun releaseLock()
}
