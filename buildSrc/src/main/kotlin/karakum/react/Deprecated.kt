package karakum.react

internal fun String.removeDeprecatedMembers(): String =
    replace(Regex("""        \/\*\* @deprecated \*\/\n        .+?\n"""), "")
        .replace("        param: DetailedHTMLFactory<ParamHTMLAttributes<HTMLParamElement>, HTMLParamElement>;\n", "")
