package karakum.browser

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
    "CSSKeyframesRule",
    "CSSNumericArray",
    "CSSRuleList",
    "CSSStyleDeclaration",
    "CSSTransformValue",
    "CSSUnparsedValue",

    "DataTransferItemList",

    "DOMRectList",
    "DOMStringList",
    "DOMTokenList",

    "HTMLFormElement",

    "ImageTrackList",

    "MediaList",

    "SVGLengthList",
    "SVGNumberList",
    "SVGPointList",
    "SVGStringList",
    "SVGTransformList",

    "SpeechRecognitionResult",
    "SpeechRecognitionResultList",

    "TextTrackList",
    "TextTrackCueList",
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

        return when {
            propertyName == "lastModified" -> "EpochTimeStamp"
            propertyName == "expiration" -> "EpochTimeStamp"
            propertyName.endsWith("Digits") -> "Int"
            else -> IDLRegistry.getPropertyType(parentType, propertyName)
        }
    }

    val readonlyMode: Boolean =
        IDLRegistry.isPlainObjectInterface(parentType)

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
