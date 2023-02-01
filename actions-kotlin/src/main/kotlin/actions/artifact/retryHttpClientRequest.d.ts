function retryHttpClientRequest(
    name: string,
    method: () => Promise<HttpClientResponse>,
    customErrorMessages?: Map<number,
        string>,
    maxAttempts?: number,
): Promise<HttpClientResponse>;
