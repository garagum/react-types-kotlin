function findFromManifest(
    versionSpec: string,
    stable: boolean,
    manifest: IToolRelease[],
    archFilter?: string,
): Promise<IToolRelease | undefined>;
