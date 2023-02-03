package actions.artifact

external interface ArtifactResponse {
    var containerId: String
    var size: Number
    var signedContent: String
    var fileContainerResourceUrl: String
    var type: String
    var name: String
    var url: String
}
