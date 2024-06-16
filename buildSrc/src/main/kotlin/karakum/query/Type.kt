package karakum.query

import karakum.common.sealedUnionBody

private val SPECIAL_TYPES = setOf(
    "boolean | number | ShouldRetryFunction<TError>",
    "number | RetryDelayFunction<TError>",
    "TOutput | DataUpdateFunction<TInput, TOutput>",
)

class Type(
    override val source: String,
    fixAction: Boolean,
) : Declaration() {
    private val originalTypeParameters: List<String> by lazy {
        if ("> = " !in source) {
            return@lazy emptyList<String>()
        }

        parseTypeParameters(source.substringBefore("> = ") + ">")
    }

    private val typeParameters: List<String> by lazy {
        originalTypeParameters.map { it.substringBefore(": ") }
    }

    override val name: String =
        getTypeName(source, JsTypeKeyword.TYPE, fixAction)

    val enumMode: Boolean by lazy {
        " = '" in source
    }

    private val originalBody: String by lazy {
        val result = source
            .substringAfterLast(" = ")
            .removeSuffix(";")
            .replace(" | undefined", "?")

        if (result.startsWith("Override<")) {
            result.removePrefix("Override<")
                .substringBefore(", {")
        } else result
    }

    private val body: String by lazy {
        val body = originalBody
            .replace(" => T | Promise<T>", " => PromiseResult<T>")
            .replace("QueryFunctionContext<TQueryKey>", "QueryFunctionContext<TQueryKey, *>")
            .replace(": Array<QueryObserverResult>", ": ReadonlyArray<QueryObserverResult<*, *>>")
            .replace(": undefined", ": Void")

        when {
            body in SPECIAL_TYPES -> body.substringAfterLast(" | ")

            body.toIntOrNull() != null -> body

            name == QUERY_KEY -> body

            name == "UseErrorBoundary" -> body
                .removeSurrounding("boolean | (", ")")
                .replace(" => boolean", " -> Boolean")

            "|" in body -> "Union /* $body */"

            body.startsWith("Omit<") -> body.removePrefix("Omit<").substringBefore(", '")
            body.startsWith("OmitKeyof<") -> body.removePrefix("OmitKeyof<").substringBefore(", '")
            name.endsWith("Result") -> body

            body == "Record<string, unknown>" -> "Record<String, *>"
            body.startsWith("MutateFunction<") -> body

            body == "(...args: any[]) => void" -> "Function<Unit>"

            body.startsWith("(") && "..." in body -> "Function<Unit> /* $body */"

            body.startsWith("(") -> {
                kotlinFunctionType(body)
                    .replace("QueryObserverResult[]", "ReadonlyArray<QueryObserverResult<*, *>>")
                    .replace("TQueryFnData[]", "ReadonlyArray<TQueryFnData>")

                    .replace("mutation: Mutation", "mutation: Mutation<*, *, *, *>")
                    .replace("mutation?: Mutation", "mutation: Mutation<*, *, *, *>?")
                    .replace("event?: QueryCacheNotifyEvent", "event: QueryCacheNotifyEvent?")
                    .replace(
                        "options?: MutateOptions<TData, TError, TVariables, TContext>",
                        "options: MutateOptions<TData, TError, TVariables, TContext>?"
                    )
            }

            else -> "Any"
        }
    }

    override fun toCode(): String {
        if (name == "Override" || name == "NoInfer" || name == "NonFunctionGuard" || name == "SkipToken" || name == "OmitKeyof")
            return ""

        if (name == "QueryClientProviderProps")
            return """
                external interface QueryClientProviderProps: react.PropsWithChildren {
                    var client: QueryClient
                }
            """.trimIndent()

        if (originalBody.startsWith("'")) {
            val values = originalBody.splitToSequence(" | ")
                .map { it.removePrefix("'") }
                .map { it.removeSuffix("'") }
                .toList()

            return sealedUnionBody(
                name = name,
                values = values,
            )
        }

        val declaration = "$name${formatParameters(typeParameters)}"
        if (name.endsWith("Result") && " | " in source) {
            val parentDeclaration = declaration
                .removePrefix("Defined")
                .replace("Result<", "BaseResult<")

            val modifiers = when (name) {
                "MutationObserverResult",
                "QueryObserverResult",
                -> ""

                else -> "sealed"
            }
            return "external $modifiers interface $declaration\n: $parentDeclaration"
        }

        if (body.toIntOrNull() != null)
            return "const val $name = $body"

        if (name == QUERY_KEY)
            return """
                // $body 
                external interface $name
                """.trimIndent()

        if (name == "UseBaseMutationResult") {
            return sequenceOf(
                "external interface $name${formatParameters(typeParameters)} : $body {",
                "    // override val mutate: UseMutateFunction<TData, TError, TVariables, TContext>",
                "    val mutateAsync: UseMutateAsyncFunction<TData, TError, TVariables, TContext>",
                "}",
            ).joinToString("\n")
        }

        if (name == "QueryFunction") {
            val interfaceTypeParameters = formatParameters(originalTypeParameters.map { "out $it" })
            val adapterTypeParameters = formatParameters(originalTypeParameters)
            val simpleTypeParameters = formatParameters(typeParameters)
            val adapterType = name + simpleTypeParameters
            return """
            @JsExternalInheritorsOnly
            sealed external interface ${name}OrSkipToken$interfaceTypeParameters    
                
            sealed external interface $name$interfaceTypeParameters :
                ${name}OrSkipToken$simpleTypeParameters
            
            inline fun $adapterTypeParameters $name(
                noinline value: $body,
            ): $adapterType =
                value.unsafeCast<$adapterType>()
            """.trimIndent()
        }

        return "typealias $name${formatParameters(typeParameters)} = $body"
    }
}
