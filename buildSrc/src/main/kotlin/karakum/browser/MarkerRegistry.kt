package karakum.browser

import java.io.File

internal val MARKER_INTERFACES = listOf(
    "Transferable",

    "HTMLOrSVGImageElement",
    "HTMLOrSVGScriptElement",
)

private val BASE_TYPES = listOf(
    "ArrayBuffer",
)

internal object MarkerRegistry {
    private lateinit var map: Map<String, List<String>>

    fun fill(
        definitionFile: File,
    ) {
        val content = definitionFile.readText()
        map = MARKER_INTERFACES.asSequence()
            .flatMap { name -> findParentTypes(content = content, interfaceName = name) }
            .groupBy(keySelector = { it.first }, valueTransform = { it.second })
    }

    fun additionalParents(
        type: String,
    ): List<String>? =
        map[type]

    fun nonProcessedChildTypes(
        type: String,
    ): List<String> =
        BASE_TYPES.filter {
            val parentTypes = map[it]
            parentTypes != null && type in parentTypes
        }
}

private fun findParentTypes(
    content: String,
    interfaceName: String,
): Sequence<Pair<String, String>> =
    content.substringAfter("type $interfaceName = ", "")
        .substringBefore(";\n")
        .splitToSequence(" | ")
        .map { it to interfaceName }
