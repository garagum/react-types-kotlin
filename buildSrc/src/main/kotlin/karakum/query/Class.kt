package karakum.query

class Class(
    override val source: String,
) : TypeBase() {
    override val name: String =
        getTypeName(source, JsTypeKeyword.CLASS)

    override val openByDefault: Boolean = true

    private val abstract: Boolean = " abstract " in source.substringBefore("{")

    override fun toCode(): String {
        val extends = parentType?.let {
            val parent = if (it == "Error") "JsError" else it

            (if (typeParameters.isNotEmpty()) "\n" else "") + ": $parent"
        } ?: ""

        val constructor = members.asSequence()
            .filterIsInstance<Constructor>()
            .firstOrNull()

        val modifier = if (abstract) "abstract" else "open"
        val result = "$modifier external class $name ${formatParameters(typeParameters)}" +
                (constructor?.toCode() ?: "") +
                "$extends {\n$content\n}"

        return if ("QueriesObserverOptimisticResult<TCombinedResult>" in result) {
            result + "\n\n" + QUERIES_OBSERVER_OPTIMISTIC_RESULT_CODE
        } else result
    }
}
