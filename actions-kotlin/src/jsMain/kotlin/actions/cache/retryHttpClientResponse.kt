// Automatically generated - do not modify!

package actions.cache

import actions.http.client.HttpClientResponse
import js.promise.Promise

suspend fun retryHttpClientResponse(
    name: String,
    method: () -> Promise<HttpClientResponse>,
): HttpClientResponse =
    retryHttpClientResponseAsync(
        name = name,
        method = method,
    ).await()

suspend fun retryHttpClientResponse(
    name: String,
    method: () -> Promise<HttpClientResponse>,
    maxAttempts: Number,
): HttpClientResponse =
    retryHttpClientResponseAsync(
        name = name,
        method = method,
        maxAttempts = maxAttempts,
    ).await()

suspend fun retryHttpClientResponse(
    name: String,
    method: () -> Promise<HttpClientResponse>,
    maxAttempts: Number,
    delay: Number,
): HttpClientResponse =
    retryHttpClientResponseAsync(
        name = name,
        method = method,
        maxAttempts = maxAttempts,
        delay = delay,
    ).await()
