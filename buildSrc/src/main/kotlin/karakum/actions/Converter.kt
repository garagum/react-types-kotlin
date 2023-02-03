package karakum.actions

internal fun convert(
    content: String,
): Sequence<ConversionResult> {
    val body = cleanup(content)

    return ("\n" + body).splitToSequence("\nexport declare ", "\nexport ", "\n declare")
        .drop(1)
        .map { it.substringBefore("\n/**") }
        .mapNotNull { convertItem(it) }
}

private fun cleanup(
    content: String,
): String =
    content.splitToSequence("\n")
        .filter { line -> !line.startsWith("/// ") }
        .filter { line -> !line.startsWith("import ") }
        .filter { line -> !line.startsWith("    private _") }
        .joinToString("\n")
        .trim()

private fun convertItem(
    source: String,
): ConversionResult? {
    if (source.startsWith("{"))
        return null

    if (source.startsWith("default "))
        return null

    val type = source.substringBefore(" ")
    when (type) {
        "interface" ->
            return convertInterface(
                source = source.substringAfter(" ")
            )

        "function" ->
            return convertFunction(
                source = source.substringAfter(" ")
            )
    }

    val name = source.substringAfter(" ")
        .substringBefore("<")
        .substringBefore(" ")
        .substringBefore("(")
        .substringBefore(":")

    var body = source
    if (body.startsWith("function ") && "():" !in body)
        body = body
            .replaceFirst("(", "(\n")
            .replace(", ", ",\n")
            .replaceFirst("):", ",\n):")

    return ConversionResult(
        name = name,
        body = body,
    )
}

private fun convertInterface(
    source: String,
): ConversionResult {
    val name = source.substringBefore(" ")
        .substringBefore("<")

    val declaration = source.substringBefore(" {\n")

    val memberSource = source.substringAfter(" {\n")
        .substringBefore("\n}")
        .replace("/**`", "/ **`")
        .replace("/*`", "/ *`")
        .trimIndent()

    if (memberSource == "[key: string]: any;")
        return ConversionResult(
            name = name,
            body = "typealias $name = Record<String, Any>"
        )

    var members = memberSource
        .replace("env?: {\n    [key: string]: string;\n};", "env?: Record<string, string>;")
        .splitToSequence("\n")
        .joinToString("\n") { line ->
            if (line.endsWith(";")) {
                convertMember(line.removeSuffix(";"))
            } else {
                line
            }
        }
        .prependIndent("    ")

    val body = "external interface $declaration {\n$members\n}"

    return ConversionResult(
        name = name,
        body = body,
    )
}

private fun convertFunction(
    source: String,
): ConversionResult {
    val name = source
        .substringBefore("(")
        .substringBefore("<")

    val bodies = convertMember(source.substringBefore(";\n").removeSuffix(";"))
    val body = ("\n" + bodies)
        .replace("\nfun ", "\nexternal fun ")
        .removePrefix("\n")

    return ConversionResult(
        name = name,
        body = body,
    )
}

private fun convertMember(
    source: String,
): String =
    if ("(" in source.substringBefore(":")) {
        convertMethod(source)
    } else {
        convertProperty(source)
    }

private fun convertProperty(
    source: String,
): String {
    val nameSource = source.substringBefore(": ")
    val typeSource = source.substringAfter(": ")

    val name = nameSource.removeSuffix("?")
    var type = kotlinType(typeSource)

    if (nameSource.endsWith("?")) {
        if (type.startsWith("("))
            type = "($type)"

        type += "?"
    }

    return "var $name: $type"
}

private fun convertMethod(
    source: String,
): String {
    // TODO: move to patches
    sequenceOf(
        "string | NodeJS.ReadableStream",
        "string | Error",
        "string | string[]",
    ).forEach { unionType ->
        if (": $unionType" in source) {
            val (t1, t2) = unionType.split(" | ")

            return sequenceOf(
                source.replace(": $unionType", ": $t1"),
                source.replace(": $unionType", ": $t2"),
            ).map { convertMethod(it) }
                .joinToString("\n\n")
        }
    }

    val declaration = source.substringBefore("(")

    val parametersSource = source
        .substringAfter("(")
        .substringBeforeLast("): ")

    val parameters = if (parametersSource.isNotEmpty()) {
        val params = if ("onResult: (err?: Error, res?: HttpClientResponse) => void" in parametersSource) {
            parametersSource
                .substringBefore(", onResult: ")
                .split(", ")
                .map { convertParameter(it) }
                .plus("onResult: (err: JsError?, res: HttpClientResponse?) -> Unit")
        } else {
            parametersSource
                .split(", ")
                .map { convertParameter(it) }
        }

        if (params.size > 1) {
            params.joinToString(",\n")
        } else params.joinToString(", ")
    } else ""

    val returnType = kotlinType(source.substringAfter("): "))
    val returns = when (returnType) {
        "void" -> ""
        else -> ": $returnType"
    }

    return "fun $declaration($parameters)$returns"
}

private fun convertParameter(
    source: String,
): String {
    val nameSource = source.substringBefore(": ")
    val typeSource = source.substringAfter(": ")

    val name = nameSource.removeSuffix("?")
    var type = kotlinType(typeSource)

    if (nameSource.endsWith("?")) {
        type += " = definedExternally"
    }

    return "$name: $type"
}
