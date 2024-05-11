// Automatically generated - do not modify!

@file:JsModule("@actions/cache")

package actions.cache

import js.core.Void
import js.promise.Promise

external suspend fun downloadCacheStorageSDK(
    archiveLocation: String,
    archivePath: String,
    options: DownloadOptions,
)

@JsName("downloadCacheStorageSDK")
external fun downloadCacheStorageSDKAsync(
    archiveLocation: String,
    archivePath: String,
    options: DownloadOptions,
): Promise<Void>
