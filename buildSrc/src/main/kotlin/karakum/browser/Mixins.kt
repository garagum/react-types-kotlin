package karakum.browser

internal object Mixins {
    private val SAFE: Set<String> = setOf(
        "LocaleOptions",
        "AbstractWorker",

        // DOM
        "ARIAMixin",
        "GlobalEventHandlers",
        "NonDocumentTypeChildNode",
        "Slottable",
    )

    val UNSAFE: Set<String> = setOf(
        "Animatable",
        "ElementCSSInlineStyle",
        "ChildNode",
        "HTMLHyperlinkElementUtils",
        "HTMLOrSVGElement",
        "InnerHTML",
        "ParentNode",
        "ElementContentEditable",
        "PopoverInvokerElement",

        "TextDecoderCommon",
        "TextEncoderCommon",

        "GenericTransformStream",
        "ReadableStreamGenericReader",
    )

    val ALL: Set<String> = SAFE + UNSAFE
}
