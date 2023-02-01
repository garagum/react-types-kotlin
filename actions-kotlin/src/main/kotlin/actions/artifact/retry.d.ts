function retry(
    name: string,
    operation: () => Promise<HttpClientResponse>,
    customErrorMessages: Map<number,
        string>,
    maxAttempts: number,
): Promise<HttpClientResponse>;
