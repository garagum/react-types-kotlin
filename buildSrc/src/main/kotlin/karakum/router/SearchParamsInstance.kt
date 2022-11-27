package karakum.router

internal const val SEARCH_PARAMS_INSTANCE = "SearchParamsInstance"

// language=Kotlin
private val BODY = """
import js.core.jso
import web.url.URLSearchParams
import react.router.NavigateOptions
import kotlin.reflect.KProperty

// TODO: make external in IR
class SearchParamsSetter
private constructor() {
    inline operator fun invoke(
        value: URLSearchParams,
    ) {
        asDynamic()(value)
    }

    inline operator fun invoke(
        value: URLSearchParams,
        options: NavigateOptions,
    ) {
        asDynamic()(value, options)
    }

    inline operator fun invoke(
        value: URLSearchParams,
        block: NavigateOptions.() -> Unit,
    ) {
        invoke(value, options = jso(block))
    }
}

// TODO: make external in IR
class SearchParamsInstance
private constructor() {
    inline operator fun component1(): URLSearchParams = asDynamic()[0]
    inline operator fun component2(): SearchParamsSetter = asDynamic()[1]

    inline operator fun getValue(
        thisRef: Nothing?,
        property: KProperty<*>,
    ): URLSearchParams =
        asDynamic()[0]

    inline operator fun setValue(
        thisRef: Nothing?,
        property: KProperty<*>,
        value: URLSearchParams,
    ) {
        asDynamic()[1](value)
    }
}
""".trimIndent()

internal fun SearchParamsInstance(): ConversionResult =
    ConversionResult(SEARCH_PARAMS_INSTANCE, BODY)
