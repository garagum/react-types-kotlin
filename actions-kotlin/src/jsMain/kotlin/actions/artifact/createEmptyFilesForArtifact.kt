// Automatically generated - do not modify!

package actions.artifact

import js.array.ReadonlyArray
import js.promise.await

suspend fun createEmptyFilesForArtifact(
    emptyFilesToCreate: ReadonlyArray<String>,
) {
    createEmptyFilesForArtifactAsync(
        emptyFilesToCreate = emptyFilesToCreate,
    ).await()
}
