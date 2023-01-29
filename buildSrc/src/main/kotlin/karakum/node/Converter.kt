package karakum.node

private val IGNORE_LIST = setOf(
    "Global",

    "Dict",
    "ReadOnlyDict",

    "Require",
    "RequireExtensions",

    "FSWatcher",
    "StatSyncFn",
    "StatWatcher",
    "WatchOptions",

    "UrlWithParsedQuery",
    "UrlWithStringQuery",

    "Blob",
    "BlobOptions",

    "URL",
    "URLSearchParams",

    // TEMP
    "ChildProcessByStdio",
    "ChildProcessWithoutNullStreams",
    "PromiseWithChild",

    "DiffieHellmanGroupConstructor",

    "ByteLengthQueuingStrategy",
    "CountQueuingStrategy",
)

internal data class ConversionResult(
    val name: String,
    val body: String,
)

internal fun convertDefinitions(
    source: String,
    pkg: Package,
): Sequence<ConversionResult> {
    val moduleName = when (pkg) {
        Package("test") -> pkg.id
        else -> pkg.name
    }

    val content = source
        .substringAfter("declare module '$moduleName' {\n", "")
        .substringBefore("\n}")
        .trimIndent()

    val namespaceStart = "namespace ${pkg.name} {\n"
    var mainContent = if (namespaceStart in content) {
        content
            .substringAfter(namespaceStart)
            .substringBefore("\n}")
            .trimIndent()
            .let { "\n$it" }
    } else content

    val globalsStart = "\nglobal {\n"
    if (globalsStart in content) {
        var globals = content
            .substringAfter(globalsStart)
            .substringBefore("\n}")
            .trimIndent()

        if (globals.startsWith("namespace NodeJS {\n") || globals.startsWith("var process: NodeJS.Process;"))
            globals = globals
                .substringAfter("namespace NodeJS {\n")
                .substringBefore("\n}")
                .trimIndent()

        mainContent += "\n\n$globals"
    } else if ("\nnamespace internal {\n" in content) {
        val internal = content
            .substringAfter("\nnamespace internal {\n")
            .substringBefore("\n}")
            .trimIndent()

        mainContent += "\n\n$internal"
    } else if ("\ndeclare namespace NodeJS {\n" in source) {
        val globals = source
            .substringAfter("\ndeclare namespace NodeJS {\n")
            .substringBefore("\n}")
            .trimIndent()

        mainContent += "\n\n$globals"
    }

    val interfaces = "\n$mainContent"
        .splitToSequence("\nexport interface ", "\ninterface ")
        .drop(1)
        .map { it.addClassPatch() }
        .map { convertInterface(it, false) }
        .filter { it.name !in IGNORE_LIST }

    val classes = "\n$mainContent"
        .splitToSequence("\nexport class ", "\nclass ")
        .drop(1)
        .map { it.addClassPatch() }
        .map { convertInterface(it, true) }
        .filter { it.name !in IGNORE_LIST }

    val types = if (pkg != Package("util")) {
        "\n$mainContent"
            .splitToSequence("\ntype ", "\nexport type ")
            .drop(1)
            .mapNotNull { convertType(it) }
            .filter { it.name != "BufferEncodingOption" }
            .filter { it.name != "WatchListener" }
    } else emptySequence()

    val constants = "\n$mainContent"
        .splitToSequence("\nconst ", "\nexport const ")
        .drop(1)
        .mapNotNull { convertConst(it) }

    return when (pkg) {
        Package("buffer") -> mergeBuffers(interfaces)

        Package("crypto") -> interfaces
            .plus(
                classes.filter {
                    it.name == "DiffieHellman"
                            || it.name == "Hash"
                            || it.name == "Hmac"
                            || it.name == "Verify"
                            || it.name == "ECDH"
                }
            )
            .plus(convertFunctions(content))
            .filter { it.name != "generateKeyPair" }
            .filter { it.name != "generateKeyPairSync" }
            // TEMP
            .plus(ConversionResult("KeyObject", "external class KeyObject"))
            .plus(ConversionResult("Cipher", "external interface Cipher"))
            .plus(ConversionResult("Decipher", "external interface Decipher"))

        Package("events") -> mergeEventEmitters(interfaces + classes)
            .plus(Abortable())

        Package("globals") -> interfaces
            .map {
                val newBody = it.body
                    .replace("web.streams.", "")
                    .replace("ReadableStream<*>", "ReadableStream")

                it.copy(body = newBody)
            }
            .plus(PipeOptions())
            .plus(ConversionResult("Dict", "typealias Dict<T> = Record<String, T>"))
            .plus(ConversionResult("ReadOnlyDict", "typealias ReadOnlyDict<T> = ReadonlyRecord<String, T>"))

        Package("fs") -> (interfaces + classes)
            .plus(convertFunctions(content, syncOnly = true))
            .plus(SymlinkType())
            .plus(BufferEncodingOption())
            .plus(ConversionResult("PathLike", "typealias PathLike = String"))
            .plus(ConversionResult("PathOrFileDescriptor", "typealias PathOrFileDescriptor = PathLike"))
            .plus(ConversionResult("TimeLike", "typealias TimeLike = kotlin.js.Date"))
            .plus(ConversionResult("EncodingOption", "typealias EncodingOption = ObjectEncodingOptions?"))
            .plus(
                ConversionResult(
                    "WriteFileOptions",
                    // language=kotlin
                    """
                sealed external interface WriteFileOptions:
                    ObjectEncodingOptions,
                    FlagAndOpenMode,
                    Abortable
                """.trimIndent()
                )
            )
            .plus(ConversionResult("Mode", "typealias Mode = Int"))
            .plus(ConversionResult("OpenMode", "typealias OpenMode = Int"))
            .plus(ConversionResult("ReadPosition", "typealias ReadPosition = Number"))

        Package("fs/promises") -> interfaces
            .plus(convertFunctions(content))

        Package("inspector") -> emptySequence()

        Package("net") -> (interfaces + classes)
            .plus(convertFunctions(content))
            .plus(
                ConversionResult(
                    "SocketConnectOpts",
                    "typealias SocketConnectOpts = ConnectOpts /* TcpSocketConnectOpts | IpcSocketConnectOpts */"
                )
            )
            .plus(
                ConversionResult(
                    "NetConnectOpts",
                    "typealias NetConnectOpts = SocketConstructorOpts /* TcpNetConnectOpts | IpcNetConnectOpts */"
                )
            )

        Package("os") -> interfaces
            .plus(convertFunctions(content))
            .plus(ConversionResult("NetworkInterfaceInfo", "typealias NetworkInterfaceInfo = NetworkInterfaceBase"))

        Package("path") -> interfaces
            .plus(rootVal("path", "PlatformPath"))

        Package("process") -> interfaces
            .plus(rootVal("process", "Process"))

        Package("querystring") -> interfaces
            .plus(convertFunctions(content))

        Package("stream") -> interfaces + classes

        Package("stream/promises") -> convertFunctions(content)
            .filter { it.name.startsWith("finished") }

        Package("stream/web") -> interfaces

        Package("test") -> interfaces
            .plus(convertFunctions(content))

        Package("tty") -> (interfaces + classes)
            .plus(convertFunctions(content))

        Package("url") -> interfaces
            .plus(convertFunctions(content))
            .plus(ConversionResult("URL.alias", "typealias URL = web.url.URL"))
            .plus(
                ConversionResult(
                    "URLSearchParams.alias",
                    "typealias URLSearchParams = web.url.URLSearchParams"
                )
            )

        Package("util") -> interfaces
            .filter { it.name == "InspectOptions" }

        Package("child_process") -> (interfaces + classes)
            .plus(convertFunctions(content))
            .filter { it.name != "spawn" }
            .filter { it.name != "exec" }
            .filter { it.name != "execFile" }

        Package("async_hooks"),
        Package("http"),
        Package("vm"),
        Package("worker_threads"),
        -> (interfaces + classes)
            .plus(convertFunctions(content))

        else -> interfaces
    } + types + constants
}

private fun convertConst(
    source: String,
): ConversionResult? {
    val name = source.substringBefore(": ")

    var sourceType = source
        .substringAfter(": ")
        .substringBefore(";")

    when {
        sourceType.startsWith("{") -> return null
        sourceType.startsWith("typeof ") -> return null
        sourceType.startsWith("webcrypto.") -> return null
        sourceType == "StatSyncFn" -> return null
        sourceType == "DiffieHellmanGroupConstructor" -> return null
        sourceType == "any" -> return null
    }

    val type = when (sourceType) {
        "string[]" -> "ReadonlyArray<String>"
        "null | MessagePort" -> "MessagePort?"
        "unique symbol" -> "Symbol"

        "boolean" -> "Boolean"
        "number" -> "Number"
        "string" -> "String"

        else -> sourceType
    }

    val finalName = if (name != name.toUpperCase()) {
        "$name.const"
    } else name

    return ConversionResult(
        name = finalName,
        body = "external val $name: $type",
    )
}

private fun convertType(
    source: String,
): ConversionResult? {
    val name = source.substringBefore(" =")
        .substringBefore("<")

    if (name == "ReadableStreamController") {
        val body = source.substringBefore(";")

        return ConversionResult(
            name = name,
            body = "typealias " + body
        )
    }

    if (name == "RequestListener") {
        val body = source
            .substringBefore(";")
            .addClassPatch()
            .replace("Request : IncomingMessage,", "Request /* : IncomingMessage */,")
            .replace("Response : ServerResponse<*>,", "Response /* : ServerResponse<*> */,")
            .replace(" => void", " -> $UNIT")

        return ConversionResult(
            name = name,
            body = "typealias " + body
        )
    }

    if (name.startsWith("Pipeline"))
        return null

    var bodySource = source.substringAfter(" =")
        .removePrefix(" ")
        .substringBefore(";")

    if ("> = " in bodySource)
        bodySource = bodySource.substringAfter("> = ")

    convertUnion(name, bodySource)?.let {
        return it
    }

    when (name) {
        "Serializable",
        "TransferListItem",
        "SendHandle",
        "StdioOptions",
        "LargeNumberLike",
        "KeyLike",
        "BinaryLike",
        "CipherKey",
        "Encoding",
        "DiffieHellmanGroup",
        -> return ConversionResult(
            name = name,
            body = "typealias $name = Any /* $bodySource */",
        )
    }

    if (bodySource == "ExecException & NodeJS.ErrnoException")
        return ConversionResult(
            name = name,
            body = "typealias $name = Throwable /* $bodySource */",
        )

    if (bodySource == "-1 | 0 | 1")
        return ConversionResult(
            name = name,
            body = "typealias $name = Int /* $bodySource */",
        )

    if (!bodySource.startsWith("("))
        return null

    var body = bodySource
        .replace("<unknown>", "<*>")
        .replace(": unknown", ": Any?")
        .replace("code: number", "code: Int")
        .replace(": string", ": String")
        .replace(": number", ": Number")
        .replace(" => void", " -> Unit")
        .replace(" => any", " -> Any?")
        .replace("?: Error | null", ": Error?")
        .replace("?: any", ": Any?")
        .replace(": NodeJS.ErrnoException | null", ": ErrnoException?")
        .replace(": void | Promise<void>", "-> Promise<Void>?")
        .replace(": void | PromiseLike<void>", "-> Promise<Void>?")
        .replace(": any", "-> Any?")
        .replace("?: T): Number", ": T?) -> Number")

        // TEMP
        .replace(": dns.LookupOneOptions", ": $DYNAMIC /* dns.LookupOneOptions */")

    if (!body.startsWith("()"))
        body = body
            .replaceFirst("(", "(\n")
            .replace(",", ",\n")

    val declaration = source.substringBefore(" = ")

    return ConversionResult(name, "typealias $declaration = $body")
}

private fun convertInterface(
    source: String,
    classMode: Boolean,
): ConversionResult {
    val name = source.substringBefore(" ")
        .substringBefore("<")
        .substringBefore("(")
        .substringBefore(":")

    if (name == "_DOMEventTarget") {
        return ConversionResult(
            name = name,
            body = "typealias $name = web.events.EventTarget",
        )
    }

    if (source.count { it == '\n' } == 2 && " {\n    (" in source) {
        val typeSource = source
            .substringBefore("\n}")
            .replace(" {\n    ", " = ")
            .replace(" = any>", ">")

        return convertType(typeSource)!!
    }

    if (!classMode && (name == "Stats" || name == "EventEmitter" || name == "BroadcastChannel"))
        return convertInterface("I$source", classMode)

    if (name == "internal")
        return convertInterface(source.replaceFirst("internal ", "LegacyStream "), classMode)
            .let { it.copy(body = rootModuleAnnotaion("stream") + "\n" + it.body) }

    if (" extends NodeJS.Dict<" in source) {
        val type = source
            .substringAfter(" extends NodeJS.Dict<")
            .substringBeforeLast("> {")

        return ConversionResult(
            name = name,
            body = "typealias $name = Dict<Any /* $type */>",
        )
    }

    var declaration = source
        .removeSuffix("{}")
        .substringBefore(" {}\n")
        .substringBefore(" { }\n")
        .substringBefore(" {\n")
        .replace(" extends ", " : ")
        .replace("<number>", "<Number>")
        .replace("<string>", "<String>")
        .replace("<bigint>", "<BigInt>")
        .replace("NodeJS.ArrayBufferView", "ArrayBufferView")
        .replace("NodeJS.RefCount", "RefCount")
        .replace(": NodeJS.ReadableStream", ": node.ReadableStream")
        .replace("implements NodeJS.ReadableStream", ", node.ReadableStream")
        .replace("implements NodeJS.WritableStream", ", node.WritableStream")
        .replace("implements Writable", "/* , Writable */")
        .replace("implements AsyncIterable<Dirent>", ": AsyncIterable<Dirent>")
        .replace("typeof IncomingMessage = typeof IncomingMessage", "IncomingMessage")
        .replace("typeof ServerResponse = typeof ServerResponse", "ServerResponse<*>")
        .replace(": stream.Duplex", ": Duplex")
        .replace(": stream.TransformOptions", ": TransformOptions")
        .replace(": stream.Transform", ": Transform")
        .replace(": net.Socket", ": node.net.Socket")
        .replace(": stream.Readable", ": Readable")
        .replace(": stream.Writable", ": Writable")
        .replace("null | Readable", "Readable?")
        .replace("null | Writable", "Writable?")
        .replace("StdioNull | StdioPipe", "Any /* StdioNull | StdioPipe */")
        .replace(": NetServer", ": node.net.Server")
        .replace(": internal", ": LegacyStream")
        .replace(" = any", "")
        .replace(" = Buffer", "")
        .replace(" = IncomingMessage", "")
        .replace("string | Buffer", "Any /* string | Buffer */")
        .replace(": EventEmitter", if (classMode) ": node.events.EventEmitter" else ": node.events.IEventEmitter")
        .replace(": Error", "/* : Error */")
        .replace("Partial<TcpSocketConnectOpts>", "node.net.TcpSocketConnectOpts /* Partial */")
        // TEMP???
        .replace(": StreamOptions<Readable>", ": StreamOptions<Stream /* Readable */>")
        .replace(": StreamOptions<Writable>", ": StreamOptions<Stream /* Writable */>")
        // TEMP
        .replace(": tty.ReadStream", "/* : tty.ReadStream */")
        .replace(": tty.WriteStream", "/* : tty.WriteStream */")
        .replace(": ReadWriteStream", ": node.ReadWriteStream")

    if (declaration.endsWith(": OutgoingMessage"))
        declaration += "<IncomingMessage>"

    if (name == "Stats" || name == "EventEmitter" || name == "BroadcastChannel")
        declaration += " : I$name"

    val bodySource = if (!source.substringBefore("\n").let { it.endsWith("{}") || it.endsWith("{ }") }) {
        source.substringAfter(" {\n")
            .let { if (it.startsWith("}")) "" else it }
            .substringBefore("\n}")
            .trimIndent()
            .replace("toJSON(): {\n    type: 'Buffer';\n    data: number[];\n};", "toJSON(): any;")
            .replace(";\n *", ";--\n *")
    } else ""

    val body = convertMembers(bodySource)
        .replace(";--\n *", ";\n *")
        .let { addOverrides(name, declaration, it, classMode) }

    val type = when (name) {
        "Buffer",
        "BufferConstructor",
        -> "class"

        "IEventEmitter",
        "RefCounted",
        "ReadableStream",
        "WritableStream",
        "ReadWriteStream",
        "TransformOptions",

        "TcpSocketConnectOpts",
        -> "interface"

        else -> if (classMode) {
            when {
                bodySource.startsWith("private constructor(")
                -> "sealed class"

                // TEMP
                name == "Stats" -> "abstract class"
                name in OPEN_CLASSES -> "open class"
                else -> "class"
            }
        } else "sealed interface"
    }

    return ConversionResult(
        name = name,
        body = "external $type $declaration {\n$body\n}",
    )
}

private fun convertFunctions(
    source: String,
    syncOnly: Boolean = false,
): Sequence<ConversionResult> =
    source
        .splitToSequence("\nexport function ", "\nfunction ")
        .drop(1)
        .map { it.substringBefore(";\nexport ") }
        .map { it.substringBefore(";\ninterface ") }
        .map { it.substringBefore("\n/**") }
        // WA for `http.get`
        .map { it.substringBefore(";\nlet ") }
        .map { it.substringBefore(";\n\nlet ") }
        .map { it.removeSuffix(";") }
        .flatMap { functionSource ->
            val commentSource = ("\n" + source.substringBefore(functionSource))
                .substringAfterLast("\n/**\n", "")
                .substringBeforeLast("\n */\n", "")

            val comment = if (commentSource.isNotEmpty()) {
                "/**\n$commentSource\n */"
                    .replace("* /*\n", "* ---\n")
            } else ""

            convertFunction(
                source = functionSource.addClassPatch(),
                comment = comment,
                syncOnly = syncOnly,
            )
        }

private val WRITE_OPTIONS = """
        | (ObjectEncodingOptions & {
              mode?: Mode | undefined;
              flag?: OpenMode | undefined;
          } & Abortable)
        | BufferEncoding
        | null"""

private fun convertFunction(
    source: String,
    comment: String,
    syncOnly: Boolean,
): Sequence<ConversionResult> {
    val name = source.substringBefore("(")
        .substringBefore("<")

    if (WRITE_OPTIONS in source) {
        return sequenceOf("WriteFileOptions", "BufferEncoding")
            .flatMapIndexed { index, replacement ->
                var newSource = source.replace(WRITE_OPTIONS, " $replacement")
                if (index == 1)
                    newSource = newSource.replace("options?:", "options:")

                convertFunction(
                    source = newSource,
                    comment = if (index == 0) comment else "",
                    syncOnly = syncOnly,
                )
            }
    }

    if ("{" in source && !(name == "readFile" || name == "writeFile" || name == "readdir"))
        return emptySequence()

    if (syncOnly && !(name.endsWith("Sync") || name.endsWith("Stream")))
        return emptySequence()

    if ("parseQueryString: false" in source || "parseQueryString: true" in source)
        return emptySequence()

    val typeParameters = source
        .substringBefore("(")
        .removePrefix(name)
        .replace("extends NodeJS.ArrayBufferView", ": ArrayBufferView")
        .replace("extends webcrypto.BufferSource", ": Any /* ArrayBufferView | ArrayBuffer */")
        .replace("extends typeof IncomingMessage = typeof IncomingMessage", ": IncomingMessage")
        .replace("extends typeof ServerResponse = typeof ServerResponse", ": ServerResponse<*>")

    val parametersSource = source
        .substringAfter("(")
        .replaceFunctionType()
        .substringBeforeLast(")")

    val parameters = when {
        "requestListener?: " in parametersSource -> {
            val (pname, ptype) = parametersSource.split("?: ")
            listOf(Parameter(pname, ptype, true))
        }

        else -> parametersSource
            .splitToSequence(", ", ",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { convertParameter(it) }
            .toList()
    }

    val returnType = kotlinType(
        source
            .substringAfter("): ")
            .substringBefore(";\n//")
            .removeSuffix(";\n"),
        name,
    )

    // ignore fallbacks
    if ("/* string | Buffer */" in returnType || "/* string[] | Buffer[] */" in returnType)
        return emptySequence()

    val returnDeclaration = when (returnType) {
        "Unit" -> ""
        else -> ": $returnType"
    }

    val finalName = if (returnType.startsWith("Promise<")) name + "Async" else name
    val params = parameters
        .takeIf { it.isNotEmpty() }
        ?.joinToString(",\n", "\n", ",\n")
        ?: ""

    var body = "external fun $typeParameters $finalName(" +
            params +
            ")$returnDeclaration"

    // TODO: remove hot fix
    if (" -> void " in body)
        body = body.replace(" -> void ", " -> $UNIT ")

    if (name != finalName)
        body = "@JsName(\"$name\")\n$body"

    if (comment.isNotEmpty())
        body = "$comment\n$body"

    return sequenceOf(
        ConversionResult(finalName, body),
        suspendFunctions(name, parameters, returnType)
    ).filterNotNull()
}

private fun convertParameter(
    source: String,
): Parameter {
    val name = source
        .substringBefore("?:")
        .substringBefore(":")

    val typeSource = source
        .substringAfter(":")
        .removePrefix(" ")
        .removePrefix("\n")

    val finalName = when (name) {
        "object" -> "o"
        else -> name
    }

    return Parameter(
        name = finalName,
        type = kotlinType(typeSource, name),
        optional = source.startsWith("$name?"),
    )
}

private fun suspendFunctions(
    name: String,
    parameters: List<Parameter>,
    returnType: String,
): ConversionResult? {
    val promiseResult = returnType.removeSurrounding("Promise<", ">")
    if (promiseResult == returnType)
        return null

    val endIndex = parameters.lastIndex + 1
    val startIndex = parameters.indexOfFirst { it.optional }
        .takeIf { it != -1 }
        ?: endIndex

    val body = (startIndex..endIndex)
        .map { parameters.subList(0, it) }
        .map { it.map { it.copy(optional = false) } }
        .map { params -> suspendFunction(name, params, promiseResult) }
        .joinToString("\n\n")

    return ConversionResult(name, body)
}

private fun suspendFunction(
    name: String,
    parameters: List<Parameter>,
    returnType: String,
): String {
    val params = parameters
        .takeIf { it.isNotEmpty() }
        ?.joinToString(",\n", "\n", ",\n")
        ?: ""

    val declaration = "suspend fun $name($params)"

    val callParams = parameters
        .takeIf { it.isNotEmpty() }
        ?.joinToString(",\n", "\n", ",\n") {
            "${it.name} = ${it.name}"
        }
        ?: ""

    val call = "${name}Async($callParams).await()"

    return if (returnType != "Void") {
        "$declaration : $returnType =\n $call"
    } else {
        "$declaration {\n $call \n}"
    }
}
