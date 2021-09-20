package com.github.turansky.react

import com.github.turansky.common.Suppress.VAR_TYPE_MISMATCH_ON_OVERRIDE
import com.github.turansky.common.suppress

private val TYPE_CONTAINER_IMPORTS = """
import org.w3c.dom.*    
import react.IntrinsicType    
""".trimIndent()

private const val SVG_TYPE = "DefaultSvgType"
private const val SVG_TYPE_DECLARATION = "typealias $SVG_TYPE = IntrinsicType<SVGAttributes<org.w3c.dom.svg.SVGElement>>"

internal fun convertInterface(
    name: String,
    source: String,
): Sequence<ConversionResult> {
    val typeConverter = SimpleTypeConverter(name)
    val result = when {
        name.endsWith("Event") -> convertEventInterface(name, source, typeConverter)
        name.endsWith("Attributes") -> convertAttributesInterface(name, source, typeConverter)

        name == "ReactHTML" -> convertIntrinsicTypes(name, source, ::convertHtmlType)
        name == "ReactSVG" -> convertIntrinsicTypes(name, source, ::convertSvgType, SVG_TYPE_DECLARATION)

        name == "AbstractView" -> convertInterface(name, source, typeConverter)
        name == "StyleMedia" -> convertInterface(name, source, typeConverter)
        name == "DangerouslySetInnerHTML" -> convertInterface(name, source, typeConverter)

        else -> null
    }

    result ?: return emptySequence()

    return sequenceOf(result) + typeConverter.unions
}

private fun convertInterface(
    name: String,
    source: String,
    typeConverter: TypeConverter,
): ConversionResult {
    val members = convertMembers(source, !name.endsWith("HTML"), typeConverter)
    val body = "external interface $name {\n$members\n}"
    return ConversionResult(name, body)
}

private fun convertAttributesInterface(
    name: String,
    source: String,
    typeConverter: TypeConverter,
): ConversionResult? {
    when (name) {
        "Attributes",
        "RefAttributes",
        "ClassAttributes",
        -> return null
    }

    var declaration = source.substringBefore(" {")
        .replace("<T> extends HTMLAttributes<T>", "<T : Element> : HTMLAttributes<T>")
        .replace("<T> extends MediaHTMLAttributes<T>", "<T : Element> : MediaHTMLAttributes<T>")
        .replace(" extends ", " : ")

    when (name) {
        "DOMAttributes",
        "HTMLAttributes",
        "SVGAttributes",
        -> declaration = declaration.replaceFirst("<T>", "<T: Element>")
    }

    if (name == "DOMAttributes")
        declaration += ": react.PropsWithChildren, react.PropsWithClassName"

    val content = when (name) {
        "DOMAttributes" -> source
            .replaceFirst("children?: ReactNode | undefined;\n", "")
            .replaceFirst("{\n        __html: string;\n    }", "DangerouslySetInnerHTML")
        else -> source
    }

    var members = convertMembers(content, false, typeConverter)

    when (name) {
        "InputHTMLAttributes",
        "SelectHTMLAttributes",
        "TextareaHTMLAttributes",
        -> members = members.replace(
            "var onChange: ",
            suppress(VAR_TYPE_MISMATCH_ON_OVERRIDE) + "\noverride var onChange: ",
        )
    }

    when (name) {
        "AllHTMLAttributes",
        "InputHTMLAttributes",
        "TextareaHTMLAttributes",
        -> members = members.replaceFirst("var placeholder: ", "override var placeholder: ")

        "VideoHTMLAttributes",
        -> members = members.replaceFirst("var playsInline: ", "override var playsInline: ")
    }

    val body = if (name == "AriaAttributes") {
        "external interface $name\n\n" +
                members.replace("var ", "var $name.")
    } else {
        "import org.w3c.dom.Element\n\n" +
                "external interface $declaration {\n" +
                members +
                "\n}\n"
    }

    return ConversionResult(name, body)
}

private fun convertIntrinsicTypes(
    name: String,
    source: String,
    convert: (String) -> String,
    vararg aliases: String,
): ConversionResult {
    val content = source.substringAfter("{\n")
        .trimIndent()
        .removeSuffix(";")
        .splitToSequence(";\n")
        .map(convert)
        .joinToString(
            separator = "\n\n",
            prefix = "object $name {\n",
            postfix = "\n}\n"
        )

    val body = sequenceOf(TYPE_CONTAINER_IMPORTS)
        .plus(aliases)
        .plus(content)
        .joinToString("\n\n")

    return ConversionResult(name, body)
}

private fun convertHtmlType(
    source: String,
): String {
    val name = source.substringBefore(": ")
        .removeSurrounding("\"")

    val propsType = source.substringAfter(": DetailedHTMLFactory<")
        .substringBefore(",")
        .replaceFirst("<", "<")
    val type = "IntrinsicType<$propsType>"

    val id = when (name) {
        "object", "var" -> "`$name`"
        else -> name
    }

    return """
        inline val $id: $type
            get() = "$name".unsafeCast<$type>()
    """.trimIndent()
}

private fun convertSvgType(
    source: String,
): String {
    val name = source.substringBefore(": ")
        .removeSurrounding("\"")

    val type = SVG_TYPE

    return """
        inline val $name: $type
            get() = "$name".unsafeCast<$type>()
    """.trimIndent()
}
