function group<T>(
    name: string,
    fn: () => Promise<T>,
): Promise<T>;
