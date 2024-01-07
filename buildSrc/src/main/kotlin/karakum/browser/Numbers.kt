package karakum.browser

private val FLAG_NAMES = setOf(
    // sockets
    "code",
)

private val LONG_NAMES = setOf(
    "at",
    "quota",
    "usage",

    "version",
    "newVersion",
    "oldVersion",

    // ProgressEvent
    "loaded",
    "total",

    "whatToShow",

    // Codecs
    "decodeQueueSize",
    "encodeQueueSize",
    "timestamp",
    "stride",

    // Audio
    "currentFrame",
)

private val INT_NAMES = setOf(
    "cellIndex",
    "colSpan",
    "cols",
    "hardwareConcurrency",
    "index",
    "length",
    "snapshotLength",
    "maxLength",
    "minLength",
    "naturalHeight",
    "naturalWidth",
    "rowIndex",
    "rowSpan",
    "rows",
    "sectionRowIndex",
    "selectedIndex",
    "selectionEnd",
    "selectionStart",
    "size",
    "span",
    "start",
    "textLength",

    "videoHeight",
    "videoWidth",

    "numberOfItems",
    "childElementCount",

    "maxTouchPoints",

    // window
    "innerWidth",
    "innerHeight",
    "screenLeft",
    "screenTop",
    "screenX",
    "screenY",
    "outerWidth",
    "outerHeight",

    "clientX",
    "clientY",

    "layerX",
    "layerY",

    "tiltX",
    "tiltY",

    "detail",
    "twist",
    "pointerId",

    "endOffset",
    "startOffset",

    "charIndex",
    "charLength",

    // RTC
    "errorCode",
    "port",

    // error
    "colno",
    "lineno",

    // Element
    "tabIndex",

    // HTMLElement
    "offsetWidth",
    "offsetHeight",
    "offsetTop",
    "offsetLeft",

    // Element
    "clientHeight",
    "clientLeft",
    "clientTop",
    "clientWidth",

    "scrollHeight",
    "scrollWidth",

    "columnNumber",
    "lineNumber",
    "statusCode",

    // Selection
    "anchorOffset",
    "focusOffset",
    "rangeCount",

    // ImageBitmapOptions
    "resizeWidth",
    "resizeHeight",

    // XMLHttpRequest
    "status",
    "timeout",

    // MediaTrackSettings
    "sampleRate",
    "sampleSize",

    // AudioConfiguration
    "bitrate",
    "samplerate",

    // MediaRecorderOptions
    "audioBitsPerSecond",
    "bitsPerSecond",
    "videoBitsPerSecond",

    // Audio
    "fftSize",
    "offset",

    // Animation
    "computedOffset",

    // Crypto
    "tagLength",
    "modulusLength",
    "saltLength",

    // PerformanceResourceTiming
    "decodedBodySize",
    "encodedBodySize",
    "transferSize",

    // WebSocket
    "bufferedAmount",

    // Video
    "codedHeight",
    "codedWidth",
    "displayAspectHeight",
    "displayAspectWidth",
    "displayHeight",
    "displayWidth",

    "byteLength",

    // Streams
    "highWaterMark",
    "autoAllocateChunkSize",
    "desiredSize",

    // Wasm
    "initial",
    "maximum",

    // WebTransport
    "closeCode",
    "streamErrorCode",
    "sendOrder",

    "incomingHighWaterMark",
    "incomingMaxAge",
    "maxDatagramSize",
    "outgoingHighWaterMark",
    "outgoingMaxAge",

    // Highlight
    "priority",
)

private val DOUBLE_NAMES = setOf(
    // DOMMatrix2DInit
    "a",
    "b",
    "c",
    "d",
    "e",
    "f",
    "m11",
    "m12",
    "m21",
    "m22",
    "m41",
    "m42",

    "height",
    "high",
    "low",
    "max",
    "min",
    "optimum",
    "position",
    "quality",
    "value",
    "valueAsNumber",
    "numberValue",
    "width",
    "x",
    "y",
    "z",

    // Media elements
    "mediaTime",
    "processingDuration",
    "rtpTimestamp",
    "currentTime",
    "defaultPlaybackRate",
    "duration",
    "playbackRate",
    "volume",

    "angle",
    "currentScale",
    "valueInSpecifiedUnits",

    // animation
    "currentIteration",
    "delay",
    "duration",
    "endDelay",
    "iterationStart",
    "iterations",
    "progress",

    // speech
    "pitch",
    "rate",
    "confidence",

    // window
    "scrollX",
    "scrollY",
    "pageXOffset",
    "pageYOffset",

    // event
    "elapsedTime",
    "interval",

    "alpha",
    "beta",
    "gamma",

    "pageX",
    "pageY",
    "offsetX",
    "offsetY",
    "movementX",
    "movementY",

    "deltaX",
    "deltaY",
    "deltaZ",

    "pressure",
    "tangentialPressure",

    // Element
    "scrollLeft",
    "scrollTop",

    // Scroll
    "left",
    "top",

    // TextTrackCue
    "endTime",
    "startTime",

    // MediaTrackSettings
    "aspectRatio",
    "frameRate",

    // MediaSessionActionDetails
    "seekOffset",
    "seekTime",

    // observers
    "intersectionRatio",
    "blockSize",
    "inlineSize",

    // VideoConfiguration
    "framerate",

    // SourceBuffer
    "appendWindowEnd",
    "appendWindowStart",
    "timestampOffset",

    // Audio
    "attack",
    "baseLatency",
    "coneInnerAngle",
    "coneOuterAngle",
    "coneOuterGain",
    "contextTime",
    "defaultValue",
    "delayTime",
    "detune",
    "frequency",
    "gain",
    "knee",
    "loopEnd",
    "loopStart",
    "maxDecibels",
    "maxDelayTime",
    "maxDistance",
    "maxValue",
    "minDecibels",
    "minValue",
    "outputLatency",
    "pan",
    "ratio",
    "reduction",
    "refDistance",
    "release",
    "rolloffFactor",
    "smoothingTimeConstant",
    "threshold",

    // VisualViewport
    "pageLeft",
    "pageTop",
    "scale",
)

private val WINDOW_EXCLUDED = setOf(
    // properties
    "customElements",
    "document",
    "devicePixelRatio",
    "history",
    "navigator",
    "screen",
    "self",
    "speechSynthesis",
    "visualViewport",
    "window",

    // methods
    "alert",
    "cancelIdleCallback",
    "confirm",
    "getComputedStyle",
    "matchMedia",
    "prompt",
    "requestIdleCallback",
)

internal val LENGTH_REQUIRED = setOf(
    "CSSTransformValue",
    "CSSUnparsedValue",
    "HTMLFormElement",
)

internal class TypeProvider(
    private val parentType: String,
    private val arrayType: String? = null,
    private val hideForEach: Boolean = false,
) {
    fun numberType(
        propertyName: String,
    ): String {
        when (propertyName) {
            "button" -> return MOUSE_BUTTON
            "buttons" -> return MOUSE_BUTTONS
        }

        when {
            propertyName == "lastModified" -> return "EpochTimeStamp"
            propertyName == "expiration" -> return "EpochTimeStamp"
            propertyName.endsWith("Digits") -> return "Int"
        }

        val type = IDLRegistry.getPropertyType(parentType, propertyName)
        if (type != "Number")
            return type

        // flags
        if (propertyName in FLAG_NAMES)
            return "Short"

        if (parentType == "HTMLCanvasElement" || parentType == "ImageBitmap" || parentType == "ImageData") {
            when (propertyName) {
                "width",
                "height",
                -> return "Int"
            }
        }

        if (parentType == "Blob") {
            return "JsLong"
        }

        return when {
            parentType.endsWith("DoubleRange") -> "Double"
            parentType.endsWith("ULongRange") -> "Int"

            parentType in DOM_GEOMETRY_TYPES -> "Double"
            parentType.startsWith("Touch") -> "Double"

            parentType == "CSSNumericType" -> "Int"
            parentType == "GamepadEffectParameters" -> "Double"

            parentType == "MediaError" -> "Short"
            parentType == "GeolocationPositionError" -> "Short"
            parentType == "GeolocationCoordinates" -> "Double"
            parentType == "PositionOptions" -> "JsLong"

            parentType == "PannerOptions" -> "Double"

            parentType == "Screen" -> "Int"
            parentType == "TextEncoderEncodeIntoResult" -> "Int"

            parentType == "SVGAnimatedInteger" -> "Int"
            parentType == "SVGAnimatedNumber" -> "Double"

            parentType == "VTTRegion" -> "Double"

            propertyName.startsWith("numberOf") -> "Int"
            propertyName.endsWith("Count") -> "Int"
            propertyName.endsWith("Frames") -> "Int"

            propertyName in LONG_NAMES -> "JsLong"
            propertyName in INT_NAMES -> "Int"
            propertyName in DOUBLE_NAMES -> "Double"

            parentType == "TextMetrics" -> "Double"
            parentType.startsWith("Canvas") -> "Double"

            else -> TODO("No numberability configuration for property '$propertyName'")
        }
    }

    fun isDefined(): Boolean =
        parentType in Mixins.ALL

    fun isArrayLike(): Boolean =
        arrayType != null

    fun accepted(
        name: String,
    ): Boolean {
        if (name == "length" && isArrayLike())
            return parentType in LENGTH_REQUIRED

        if (name == "forEach" && hideForEach)
            return false

        if (parentType == "Window")
            return name !in WINDOW_EXCLUDED

        if (parentType == "WorkerGlobalScope")
            return name != "self"


        return true
    }

    fun getParameterType(name: String): String =
        IDLRegistry.getParameterType(parentType, name)

    fun getReturnType(name: String): String =
        IDLRegistry.getReturnType(parentType, name)
}
