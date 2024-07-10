package karakum.react

import java.io.File

internal data class ConversionResult(
    val name: String,
    val body: String,
    val pkg: Package = Package.HTML,
)

internal fun convertDefinitions(
    definitionFile: File,
): Sequence<ConversionResult> {
    val content = definitionFile.readText()
        .removeDeprecatedMembers()
        .applyFocusEventPatch()
        .applyNormalizeUnionsPatch()
        .replace("HTMLTableHeaderCellElement", "HTMLTableCellElement")
        .replace("HTMLTableDataCellElement", "HTMLTableCellElement")
        .replace("HTMLWebViewElement", "HTMLElement")
        .replace(": HTMLAttributeAnchorTarget", ": WindowTarget")
        .replace(": HTMLAttributeReferrerPolicy", ": ReferrerPolicy")
        .replace(": HTMLInputTypeAttribute", ": InputType")
        .replace("    target?: string | undefined;", "    target?: WindowTarget | undefined;")
        .replace("    formTarget?: string | undefined;", "    formTarget?: WindowTarget | undefined;")
        .replace("    autoComplete?: string | undefined;", "    autoComplete?: AutoComplete | undefined;")
        .replace(""": boolean | "false"""", """: "false"""")
        .replace("""fetchPriority?: "high" | "low" | "auto";""", """fetchPriority?: FetchPriority;""")
        .replace(" |  undefined", " | undefined")
        .replace("\r\n", "\n")

    val svgTypes = content.substringAfter("    interface IntrinsicElements {\n")
        .substringAfter("// SVG\n")
        .substringBefore("\n        }")
        .replace("\n\n", "\n")
        .replaceIndent("        ")

    val reactContent = content
        .substringAfter("declare namespace React {\n")
        .substringBefore("\n}\n")
        .replace(Regex("""( ReactSVG \{\n).+?(\n\s+})""", RegexOption.DOT_MATCHES_ALL)) {
            "${it.groupValues[1]}$svgTypes${it.groupValues[2]}"
        }
        .trimIndent()
        .plus(AUTO_COMPLETE)
        .plus(ADDITIONAL_TYPES)

    return convertInterfaces(reactContent)
        .plus(convertUnions(reactContent))
        .plus(convertNativeEvents(content))
        .plus(convertEventHandlers(reactContent))
}

private val EXCLUDED_UNIONS = setOf(
    "HTMLAttributeAnchorTarget",
    "HTMLAttributeReferrerPolicy",
    "HTMLInputTypeAttribute",
    "ProfilerOnRenderCallback",
)

private fun convertUnions(
    content: String,
): Sequence<ConversionResult> =
    content.splitToSequence("\ntype ", "\nexport type ")
        .drop(1)
        .map { it.substringBefore(";") }
        .mapNotNull {
            convertUnion(
                name = it.substringBefore(" ="),
                source = it.substringAfter(" =")
            )
        }
        .filter { it.name !in EXCLUDED_UNIONS }

private fun convertInterfaces(
    content: String,
): Sequence<ConversionResult> =
    content.splitToSequence("\ninterface ")
        .drop(1)
        .map { it.substringBefore("\n}\n") }
        .flatMap {
            convertInterface(
                name = it.substringBefore(" ")
                    .substringBefore("<"),
                source = it,
            )
        }
