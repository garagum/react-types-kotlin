function getManifestFromRepo(
    owner: string,
    repo: string,
    auth?: string,
    branch?: string,
): Promise<IToolRelease[]>;
