package karakum.react

interface TypeConverter {
    fun convert(
        type: String,
        name: String,
    ): String
}

internal class SimpleTypeConverter(
    private val parentName: String,
) : TypeConverter {
    var unions: List<ConversionResult> = emptyList()
        private set

    override fun convert(
        type: String,
        name: String,
    ): String {
        if (type.endsWith(" | undefined"))
            return convert(type.removeSuffix(" | undefined"), name)

        if (type.startsWith("'") || type.startsWith("\"") || type.startsWith("boolean | '"))
            return unionType(type, name)

        return kotlinType(type, name)
    }

    private fun unionType(
        type: String,
        propertyName: String,
    ): String {
        val name = unionName(propertyName)

        when {
            propertyName == "crossOrigin" && type == """"anonymous" | "use-credentials" | """""
            -> return propertyName.capitalize()
        }

        val sourceType = if (name == "Capture") {
            type.replace("boolean", """"false" | "true"""")
        } else type

        val values = sourceType
            // WA for AlignmentBaseline
            .replace("\" |\"", "\" | \"")
            .splitToSequence(" | ")
            .filter { !it.startsWith("undefined; ") }
            .filter { it != "boolean" }
            .map { it.removeSurrounding("'") }
            .map { it.removeSurrounding("\"") }
            .toList()

        val pkg = if ("SVG" in parentName) Package.SVG else Package.HTML

        unions = unions + convertUnion(name, values).copy(pkg = pkg)

        return name
    }

    private fun unionName(
        propertyName: String,
    ): String =
        when {
            propertyName == "capture"
            -> propertyName.capitalize()

            propertyName == "enterKeyHint"
            -> propertyName.capitalize()

            propertyName.startsWith("aria-")
            -> propertyName.ariaPropertyName().capitalize()

            parentName.endsWith("HTMLAttributes") -> {
                val typeName = when (propertyName) {
                    "valign" -> "VAlign"
                    else -> propertyName.capitalize()
                }

                parentName.removeSuffix("HTMLAttributes") + typeName
            }

            else -> propertyName.capitalize()
        }
}
