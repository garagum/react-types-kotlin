package karakum.table

import karakum.common.GENERATOR_COMMENT
import karakum.common.Suppress.NOTHING_TO_INLINE
import karakum.common.fileSuppress
import java.io.File

private val DEFAULT_IMPORTS = listOf(
    "Promise" to "kotlin.js.Promise",
    "RegExp" to "kotlin.js.RegExp",

    "JsMap" to "js.collections.JsMap",
    "ReadonlyArray" to "js.core.ReadonlyArray",
    "ReadonlyRecord" to "js.core.ReadonlyRecord",
    "Symbol" to "js.core.Symbol",
    "JsTuple2" to "js.core.JsTuple2",
    "Void" to "js.core.Void",
)

fun generateKotlinDeclarations(
    coreDefinitionsFile: File,
    sourceDir: File,
) {
    val targetDir = sourceDir.resolve("tanstack/table/core")
        .also { it.mkdirs() }

    for ((name, body) in convertDefinitions(coreDefinitionsFile)) {
        val annotations = when {
            "external val " in body || "external object " in body || "external fun " in body
            -> "@file:JsModule(\"${Package.TABLE_CORE.moduleName}\")"

            "inline fun " in body
            -> fileSuppress(NOTHING_TO_INLINE)

            else -> ""
        }

        val fileName = if (name.endsWith("Fns") && "interface" in body) {
            name + ".type"
        } else name

        targetDir.resolve("$fileName.kt")
            .writeText(fileContent(Package.TABLE_CORE, annotations, body))
    }
}

private fun fileContent(
    pkg: Package,
    annotations: String,
    body: String,
): String {
    val defaultImports = DEFAULT_IMPORTS
        .filter { it.first in body }
        .map { "import ${it.second}" }
        .joinToString("\n")

    return sequenceOf(
        "// $GENERATOR_COMMENT",
        annotations,
        pkg.pkg,
        defaultImports,
        body,
    ).filter { it.isNotEmpty() }
        .joinToString("\n\n")
}
