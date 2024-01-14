package karakum.typescript

internal val CONTRACT_WITH_ERASED_TYPE = setOf(
    "isAssertsKeyword",
    "isAsteriskToken",
    "isAwaitKeyword",
    "isBinaryOperatorToken",
    "isColonToken",
    "isDotDotDotToken",
    "isEqualsGreaterThanToken",
    "isExclamationToken",
    "isImportTypeAssertionContainer",
    "isMinusToken",
    "isPlusToken",
    "isQuestionDotToken",
    "isQuestionToken",
)

internal fun addContractSupport(
    result: ConversionResult,
): Sequence<ConversionResult> {
    val (name, body) = result

    if ("external fun " !in body)
        return sequenceOf(result)

    val type = body.substringAfter("): Boolean /* node is ", "")
        .substringBefore(" */")

    if (type.isEmpty() || " | " in type)
        return sequenceOf(result)

    val originalBody = "import typescript.Node\n" +
            "import typescript.BindingName\n" +
            "\n" +
            body.replace("external fun ", "internal external fun ")

    val original = ConversionResult(
        name = name,
        body = originalBody,
        pkg = Package.TYPESCRIPT_RAW,
    )

    val parameters = body.substringAfter("external fun")
        .substringAfter("(")
        .substringBefore("):")
        .split(",\n")
        .map { it.substringBefore(":") }
        .joinToString(", ")

    val functionBody = """{
        contract {
            returns(true) implies (node is $type)
        }
    
        return typescript.raw.$name($parameters)
    }"""

    val contractBody = "import kotlin.contracts.contract\n\n" +
            body.replace("external fun ", "fun ")
                .substringBefore(" /* node is ") + functionBody

    val contract = ConversionResult(
        name = name,
        body = contractBody,
    )

    return sequenceOf(original, contract)
}
