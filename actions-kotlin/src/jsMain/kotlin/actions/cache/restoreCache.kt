// Automatically generated - do not modify!

package actions.cache

import js.array.ReadonlyArray

suspend fun restoreCache(
    paths: ReadonlyArray<String>,
    primaryKey: String,
): String? =
    restoreCacheAsync(
        paths = paths,
        primaryKey = primaryKey,
    ).await()

suspend fun restoreCache(
    paths: ReadonlyArray<String>,
    primaryKey: String,
    restoreKeys: ReadonlyArray<String>,
): String? =
    restoreCacheAsync(
        paths = paths,
        primaryKey = primaryKey,
        restoreKeys = restoreKeys,
    ).await()

suspend fun restoreCache(
    paths: ReadonlyArray<String>,
    primaryKey: String,
    restoreKeys: ReadonlyArray<String>,
    options: DownloadOptions,
): String? =
    restoreCacheAsync(
        paths = paths,
        primaryKey = primaryKey,
        restoreKeys = restoreKeys,
        options = options,
    ).await()

suspend fun restoreCache(
    paths: ReadonlyArray<String>,
    primaryKey: String,
    restoreKeys: ReadonlyArray<String>,
    options: DownloadOptions,
    enableCrossOsArchive: Boolean,
): String? =
    restoreCacheAsync(
        paths = paths,
        primaryKey = primaryKey,
        restoreKeys = restoreKeys,
        options = options,
        enableCrossOsArchive = enableCrossOsArchive,
    ).await()
