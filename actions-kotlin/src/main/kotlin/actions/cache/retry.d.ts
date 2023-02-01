function retry<T>(
    name: string,
    method: () => Promise<T>,
    getStatusCode: (arg0: T) => number | undefined,
    maxAttempts?: number,
    delay?: number,
    onError?: ((arg0: Error) => T | undefined) | undefined,
): Promise<T>;
