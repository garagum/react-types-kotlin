package actions.tool.cache

external interface IToolReleaseFile {
    var filename: String
    var platform: String
    var platform_version: String?
    var arch: String
    var download_url: String
}
