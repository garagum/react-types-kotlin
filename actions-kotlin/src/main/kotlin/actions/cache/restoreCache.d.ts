function restoreCache(
    paths: string[],
    primaryKey: string,
    restoreKeys?: string[],
    options?: DownloadOptions,
    enableCrossOsArchive?: boolean,
): Promise<string | undefined>;
