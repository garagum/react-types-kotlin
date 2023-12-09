package karakum.browser

import karakum.common.objectUnionBody
import karakum.common.sealedUnionBody
import karakum.common.unionConstant

private val PKG_MAP = mapOf(
    "NavigationTimingType" to "web.performance",

    "ColorSpaceConversion" to "web.canvas",
    "GlobalCompositeOperation" to "web.canvas",
    "ImageOrientation" to "web.canvas",
    "ImageSmoothingQuality" to "web.canvas",
    "PredefinedColorSpace" to "web.canvas",
    "PremultiplyAlpha" to "web.canvas",
    "ResizeQuality" to "web.canvas",

    "CSSMathOperator" to "web.cssom",
    "CSSNumericBaseType" to "web.cssom",
    "HighlightType" to "web.highlight",

    "EndingType" to "web.buffer",

    "InsertPosition" to "web.dom",
    "MutationRecordType" to "web.dom.observers",
    "ResizeObserverBoxOptions" to "web.dom.observers",

    "AutoFillAddressKind" to "web.html",
    "AutoFillBase" to "web.html",
    "AutoFillContactField" to "web.html",
    "AutoFillContactKind" to "web.html",
    "AutoFillCredentialField" to "web.html",
    "AutoFillNormalField" to "web.html",

    "CanPlayTypeResult" to "web.html",
    "SelectionMode" to "web.html",
    "ShadowRootMode" to "web.html",
    "SlotAssignmentMode" to "web.html",
    "TouchType" to "web.uievents",
    "DOMParserSupportedType" to "web.dom.parsing",

    "WriteCommandType" to "web.filesystem",

    "ColorGamut" to "web.media.capabilities",
    "HdrMetadataType" to "web.media.capabilities",
    "MediaDecodingType" to "web.media.capabilities",
    "MediaEncodingType" to "web.media.capabilities",
    "TransferFunction" to "web.media.capabilities",

    "RecordingState" to "web.media.recorder",

    "AppendMode" to "web.media.source",
    "EndOfStreamError" to "web.media.source",
    "ReadyState" to "web.media.source",

    "RemotePlaybackState" to "web.remoteplayback",
    "ClientTypes" to "web.serviceworker",

    "AttestationConveyancePreference" to "web.authn",
    "AuthenticatorAttachment" to "web.authn",
    "AuthenticatorTransport" to "web.authn",
    "PublicKeyCredentialType" to "web.authn",
    "ResidentKeyRequirement" to "web.authn",
    "UserVerificationRequirement" to "web.authn",

    "CompositeOperation" to "web.animations",
    "CompositeOperationOrAuto" to "web.animations",
    "FillMode" to "web.animations",
    "IterationCompositeOperation" to "web.animations",
    "PlaybackDirection" to "web.animations",

    "AutomationRate" to "web.audio",
    "BiquadFilterType" to "web.audio",
    "DistanceModelType" to "web.audio",
    "OscillatorType" to "web.audio",
    "OverSampleType" to "web.audio",
    "PanningModelType" to "web.audio",

    "PresentationStyle" to "web.clipboard",

    "AlphaOption" to "web.codecs",
    "AvcBitstreamFormat" to "web.codecs",
    "CodecState" to "web.codecs",
    "EncodedVideoChunkType" to "web.codecs",
    "HardwareAcceleration" to "web.codecs",
    "LatencyMode" to "web.codecs",
    "VideoMatrixCoefficients" to "web.codecs",
    "VideoColorPrimaries" to "web.codecs",
    "VideoEncoderBitrateMode" to "web.codecs",
    "VideoTransferCharacteristics" to "web.codecs",
    "VideoPixelFormat" to "web.codecs",

    "ScrollRestoration" to "web.history",

    "PushEncryptionKeyName" to "web.push",

    "SpeechSynthesisErrorCode" to "web.speech",

    "CredentialMediationRequirement" to "web.credentials",
    "SecurityPolicyViolationEventDisposition" to "web.csp",

    "WakeLockType" to "web.wakelock",

    "BinaryType" to "web.sockets",

    "AutoKeyword" to "web.vtt",

    "KeyFormat" to "web.crypto",
    "KeyType" to "web.crypto",
    "KeyUsage" to "web.crypto",
    "NamedCurve" to "web.crypto",

    "ReadableStreamReaderMode" to "web.streams",
    "ReadableStreamType" to "web.streams",
    "CompressionFormat" to "web.compression",

    "WebTransportCongestionControl" to "web.transport",
    "WebTransportErrorSource" to "web.transport",
)

private val EXCLUDED_TYPES = setOf(
    "OffscreenRenderingContextId",

    "DisplayCaptureSurfaceType",
    "VideoFacingModeEnum",
)

internal fun browserTypes(
    content: String,
): Sequence<ConversionResult> =
    convertTypes(content, ::getTypePkg)

internal fun convertTypes(
    content: String,
    getPkg: (name: String) -> String?,
): Sequence<ConversionResult> =
    content
        .splitToSequence("\ntype ")
        .drop(1)
        .map { it.substringBefore(";\n") }
        .mapNotNull { convertType(it, getPkg) }

private fun convertType(
    source: String,
    getPkg: (name: String) -> String?,
): ConversionResult? {
    if (" = " !in source)
        return null

    val (declaration, bodySource) = source
        .substringBefore(";")
        .split(" = ")

    val name = declaration.substringBefore("<")

    if (bodySource == "string") {
        val pkg = getPkg(name)
            ?: return null

        return ConversionResult(
            name = name,
            body = "typealias $name = String",
            pkg = pkg
        )
    }

    if (!bodySource.startsWith("\"")) {
        val pkg = when (name) {
            "Transferable" -> "js.core"

            "TimerHandler" -> "web.timers"

            "MessageEventSource" -> "web.messaging"

            "PerformanceEntryList" -> "web.performance"

            "CanvasImageSource" -> "web.canvas"
            "ImageBitmapSource" -> "web.canvas"

            "BlobPart" -> "web.buffer"

            "HTMLOrSVGImageElement" -> "web.dom"
            "HTMLOrSVGScriptElement" -> "web.dom"

            "OffscreenRenderingContext" -> "web.rendering"
            "RenderingContext" -> "web.rendering"

            "AutoFill" -> "web.html"
            "MediaProvider" -> "web.html"
            "WindowProxy" -> "web.window"

            "COSEAlgorithmIdentifier" -> "web.authn"

            "VibratePattern" -> "web.vibration"

            "ClipboardItems" -> "web.clipboard"

            "FileSystemWriteChunkType" -> "web.filesystem"

            "IDBValidKey" -> "web.idb"

            "BodyInit" -> "web.http"
            "HeadersInit" -> "web.http"
            "FormDataEntryValue" -> "web.http"

            "TexImageSource" -> "web.gl"

            "BigInteger" -> "web.crypto"
            "HashAlgorithmIdentifier" -> "web.crypto"

            "ReadableStreamController" -> "web.streams"
            "ReadableStreamReadResult" -> "web.streams"

            "ReportList" -> "web.reporting"
            "LineAndPositionSetting" -> "web.vtt"

            "RTCRtpTransform",

            "ExportValue",
            "ImportValue",

            "PushMessageDataInit",
            -> getPkg(name)!!

            else -> when {
                name.startsWith("CSS")
                -> "web.cssom"

                name.startsWith("Constrain")
                -> "web.media.streams"

                bodySource.startsWith("Record<")
                -> getPkg(name)!!

                else -> return null
            }
        }

        val body = when {
            bodySource.startsWith("Record<")
            -> bodySource
                .replace("Record<", "ReadonlyRecord<")
                .replace("string", "String")

            bodySource == "ClipboardItem[]"
            -> "ReadonlyArray<ClipboardItem>"

            bodySource == "PerformanceEntry[]"
            -> "ReadonlyArray<PerformanceEntry>"

            bodySource == "Report[]"
            -> "ReadonlyArray<Report>"

            name == "AutoFill"
            -> "String /* $bodySource */"

            name == "VibratePattern" && bodySource == "number | number[]"
            -> "ReadonlyArray<Int> /* | Int */"

            name == "COSEAlgorithmIdentifier" && bodySource == "number"
            -> "Int"

            bodySource == "string | Function"
            -> "() -> Unit"

            " | " in bodySource || bodySource == "AlgorithmIdentifier"
            -> "Any /* $bodySource */"

            else -> bodySource
        }

        val finalBody = if (declaration in MARKER_DECLARATIONS) {
            markerInterface(
                declaration = declaration,
                types = bodySource,
            )
        } else {
            "typealias $declaration = $body"
        }

        return ConversionResult(
            name = name,
            body = finalBody,
            pkg = pkg
        )
    }

    val pkg = getPkg(name)
        ?: return null

    val values = bodySource
        .splitToSequence(" | ")
        .map { it.removeSurrounding("\"") }
        .toList()

    val body = when (name) {
        "KeyFormat",
        -> objectUnionBody(
            name = name,
            constants = values.map(::unionConstant),
        )

        else -> sealedUnionBody(name, values)
    }

    return ConversionResult(
        name = name,
        body = body,
        pkg = pkg
    )
}

private fun getTypePkg(
    name: String,
): String? =
    when {
        name in EXCLUDED_TYPES -> null

        PKG_MAP.containsKey(name) -> PKG_MAP.getValue(name)

        name.endsWith("Setting") -> "web.vtt"

        name.startsWith("Document") -> "web.dom"
        name.startsWith("Fullscreen") -> "web.fullscreen"
        name.startsWith("Scroll") -> "web.scroll"

        name == "FontDisplay" -> "web.fonts"
        name.startsWith("FontFace") -> "web.fonts"

        name.startsWith("Animation") -> "web.animations"
        name.startsWith("Audio") -> "web.audio"
        name.startsWith("Channel") -> "web.audio"

        name.startsWith("Canvas") -> "web.canvas"

        name.startsWith("Gamepad") -> "web.gamepad"
        name.startsWith("IDB") -> "web.idb"

        name.startsWith("MIDI") -> "web.midi"

        name.startsWith("FileSystem") -> "web.filesystem"
        name.startsWith("Lock") -> "web.locks"

        name.startsWith("MediaDevice") -> "web.media.devices"
        name.startsWith("MediaKey") -> "web.media.key"
        name.startsWith("MediaSession") -> "web.media.session"
        name.startsWith("MediaStream") -> "web.media.streams"

        name.startsWith("Notification") -> "web.notifications"
        name.startsWith("Orientation") -> "web.screen"
        name.startsWith("Payment") -> "web.payment"
        name.startsWith("Permission") -> "web.permissions"

        name.startsWith("Referrer") -> "web.http"
        name.startsWith("Request") -> "web.http"
        name.startsWith("Response") -> "web.http"

        name.startsWith("RTC") -> "web.rtc"
        name.startsWith("ServiceWorker") -> "web.serviceworker"
        name.startsWith("TextTrack") -> "web.vtt"
        name.startsWith("Worker") -> "web.workers"

        name.startsWith("WebGL") -> "web.gl"
        name.startsWith("XMLHttp") -> "web.xhr"

        else -> TODO("Unable to find package for `$name` union")
    }

private fun markerInterface(
    declaration: String,
    types: String,
): String {
    val name = declaration.substringBefore("<")
    val modifiers = if (name.startsWith("ReadableStream")) "sealed" else ""

    val additionalChildTypes = MarkerRegistry.nonProcessedChildTypes(name)
    val extensions = additionalChildTypes.flatMap { childType ->
        sequenceOf(
            """
            inline fun ${childType}.as${name}(): $name =
                unsafeCast<$name>()
                
            inline fun $name.as${childType}OrNull(): ${childType}? =
                asDynamic() as? $childType    
            """.trimIndent()
        )
    }

    val parentTypes = MarkerRegistry.additionalParents(name)
    val parentDeclaration = if (parentTypes != null) {
        ":\n${parentTypes.joinToString(",\n")}"
    } else ""


    val comment = types
        .splitToSequence(" | ")
        .map { it.substringBefore("<") }
        .joinToString(
            separator = "\n",
            prefix = "/**\n * Union of:\n",
            postfix = "\n */",
            transform = { " * - `$it`" },
        )

    val type = """
        $comment    
        $modifiers external interface $declaration$parentDeclaration
        """.trimIndent()

    return listOf(type)
        .plus(extensions)
        .joinToString("\n\n")
}
