package karakum.csstype

import karakum.common.*
import karakum.common.Suppress
import karakum.common.Suppress.DECLARATION_CANT_BE_INLINED
import karakum.common.Suppress.NOTHING_TO_INLINE
import java.io.File

private val CSSTYPE_TYPES = setOf(
    "AdvancedPseudosRuleBuilder",
    "CssDsl",
    "Properties",
    "PseudosRuleBuilder",
    RULE_BUILDER,
    "Rules",
    "SimplePseudosRuleBuilder",
)

private val CSSTYPE_IMPORTS = """
import web.cssom.*
""".trimIndent()

private val MEDIA_IMPORTS = """
import web.cssom.Length
import web.cssom.MediaQuery
import web.cssom.SizeQuery
""".trimIndent()

private val SESKAR_IMPORTS = """
import seskar.js.JsIntValue
import seskar.js.JsNative
import seskar.js.JsValue
""".trimIndent()

fun generateKotlinDeclarations(
    definitionsFile: File,
    sourceDir: File,
) {
    writeDeclarations(
        declarations = convertDefinitions(definitionsFile),
        sourceDir = sourceDir,
        getPkg = { if (it in CSSTYPE_TYPES) "csstype" else "web.cssom" },
        getImports = { if (it in CSSTYPE_TYPES) CSSTYPE_IMPORTS else SESKAR_IMPORTS },
    )

    writeDeclarations(
        declarations = mediaTypes(),
        sourceDir = sourceDir,
        getPkg = { "web.cssom.atrule" },
        getImports = { MEDIA_IMPORTS + "\n" + SESKAR_IMPORTS },
    )

    writeDeclarations(
        declarations = sequenceOf(NonStandardPseudosRuleBuilder(), ExperimentalPseudosRuleBuilder()),
        sourceDir = sourceDir,
        getPkg = { "csstype" },
        getImports = { CSSTYPE_IMPORTS },
    )
}

private fun writeDeclarations(
    declarations: Sequence<ConversionResult>,
    sourceDir: File,
    getPkg: (name: String) -> String,
    getImports: (name: String) -> String,
) {
    for ((name, body) in declarations) {
        val suppresses = mutableSetOf<Suppress>().apply {
            if ("inline operator fun " in body)
                if (name == "$LENGTH.operators") {
                    add(NOTHING_TO_INLINE)
                } else {
                    add(DECLARATION_CANT_BE_INLINED)
                }

            if ("inline fun " in body)
                add(if (RULE_BUILDER in body) DECLARATION_CANT_BE_INLINED else NOTHING_TO_INLINE)
        }.toTypedArray()

        val annotations = if (suppresses.isNotEmpty()) {
            fileSuppress(*suppresses)
        } else ""

        val pkg = getPkg(name)
        val imports = getImports(name)
        val targetDir = sourceDir
            .resolve(pkg.replace(".", "/"))
            .also { it.mkdirs() }

        targetDir.resolve("$name.kt")
            .also { check(!it.exists()) { "Duplicated file: ${it.name}" } }
            .writeCode(
                fileContent(
                    annotations = annotations,
                    imports = imports,
                    body = body,
                    pkg = pkg
                )
            )
    }
}

private fun fileContent(
    annotations: String,
    imports: String,
    body: String,
    pkg: String,
): String {
    val result = sequenceOf(
        "// $GENERATOR_COMMENT",
        annotations,
        "package $pkg",
        imports,
        body,
    ).filter { it.isNotEmpty() }
        .joinToString("\n\n")

    return result
}
