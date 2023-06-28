package karakum.popper

import karakum.common.GENERATOR_COMMENT
import karakum.common.Suppress
import karakum.common.fileSuppress
import java.io.File
import java.io.FileFilter

private val DEFAULT_IMPORTS = listOf(
    "Promise" to "js.promise.Promise",

    "ReadonlyArray" to "js.core.ReadonlyArray",
    "Record" to "js.core.Record",
    "Void" to "js.core.Void",
)

fun generateKotlinDeclarations(
    definitionsDir: File,
    sourceDir: File,
) {
    generateCoreDeclarations(
        definitionsDir = definitionsDir,
        sourceDir = sourceDir,
    )

    generateModifiersDeclarations(
        definitionsDir = definitionsDir,
        sourceDir = sourceDir,
    )
}

private fun generateCoreDeclarations(
    definitionsDir: File,
    sourceDir: File,
) {
    val targetDir = sourceDir
        .resolve(Package.CORE.path)
        .also { it.mkdirs() }

    val types = convertDefinitions(definitionsDir.resolve("types.d.ts").readText())
        .plus(nameTypes())
        .plus(enums())

    for ((name, body) in types) {
        val suppresses = mutableListOf<Suppress>().apply {
            if ("JsName(\"\"\"(" in body || "JsName(\"'" in body)
                add(Suppress.NAME_CONTAINS_ILLEGAL_CHARS)

            if ("JsName(\"\"\"(" in body)
                add(Suppress.NESTED_CLASS_IN_EXTERNAL_INTERFACE)
        }.toTypedArray()

        val annotations = when {
            suppresses.isNotEmpty()
            -> fileSuppress(*suppresses)

            else -> ""
        }

        targetDir.resolve("$name.kt")
            .writeText(fileContent(Package.CORE, annotations, body))
    }
}

private fun generateModifiersDeclarations(
    definitionsDir: File,
    sourceDir: File,
) {
    val targetDir = sourceDir
        .resolve(Package.MODIFIERS.path)
        .also { it.mkdirs() }

    val modifierFiles = definitionsDir
        .resolve("modifiers")
        .listFiles(FileFilter { it.name.endsWith(".d.ts") && it.name != "index.d.ts" })
        ?: return

    val modifiers = modifierFiles
        .map { convertModifier(it.readText()) }

    for ((name, body) in modifiers) {
        val suppresses = mutableListOf<Suppress>().apply {
            if ("JsName(\"\"\"(" in body || "JsName(\"'" in body)
                add(Suppress.NAME_CONTAINS_ILLEGAL_CHARS)
        }.toTypedArray()

        val annotations = when {
            suppresses.isNotEmpty()
            -> fileSuppress(*suppresses)

            else -> ""
        }

        targetDir.resolve("$name.kt")
            .writeText(fileContent(Package.MODIFIERS, annotations, body))
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
