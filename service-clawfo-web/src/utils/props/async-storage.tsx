export const STORAGE_KEY = {
    CHAT_ACCESS_TOKEN: "clawfo_access_token",
    CHAT_REFRESH_TOKEN: "clawfo_refresh_token",
};

export function setStorage(
    key: string,
    value: string,
    secure: boolean = false
): void {

    localStorage.setItem(
        key,
        value
    );
}

export function getStorage(
    key: string,
    secure: boolean = false
): string | null {

    return localStorage.getItem(
        key
    );
}

export function removeStorage(
    key: string,
    secure: boolean = false
): void {

    localStorage.removeItem(
        key
    );
}

export const log = (
    label: string,
    value?: unknown
) => {

    const sanitize = (
        data: unknown
    ): unknown => {

        if (Array.isArray(data)) {
            return data.map(sanitize);
        }

        if (
            data &&
            typeof data === "object"
        ) {
            return Object.fromEntries(
                Object.entries(data).map(
                    ([key, val]) => {

                        const normalizedKey =
                            key.toLowerCase();

                        const hiddenKeys = [
                            "url",
                            "image",
                            "foto",
                            "gambarProduk"
                        ];

                        if (
                            hiddenKeys.some(
                                key =>
                                    normalizedKey.includes(key)
                            )
                        ) {
                            return [
                                key,
                                "[HIDDEN]"
                            ];
                        }

                        return [
                            key,
                            sanitize(val)
                        ];
                    }
                )
            );
        }

        return data;
    };

    console.log(
        `[${new Date().toISOString()}] ${label}`,
        value != null
            ? sanitize(value)
            : ""
    );
};