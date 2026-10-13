export interface DeviceLocation {
    longitude: string;
    latitude: string;
    location: string;
}

export const generateTicketId = (): string => {
    return [
        Date.now().toString(36),
        Math.random().toString(36).substring(2, 10),
        Math.random().toString(36).substring(2, 10),
    ].join("-");
};

export const getDeviceId = (): string => {

    const DEVICE_ID_KEY = "clawfo_device_id";

    let deviceId =
        localStorage.getItem(DEVICE_ID_KEY);

    if (!deviceId) {

        deviceId = crypto.randomUUID();

        localStorage.setItem(
            DEVICE_ID_KEY,
            deviceId
        );
    }

    return deviceId;
};

export const getCurrentLocation =
    async (): Promise<DeviceLocation> => {
        if (!navigator.geolocation) {
            throw new Error(
                "Geolocation is not supported"
            );
        }



        const position = await new Promise<GeolocationPosition>(
            (resolve, reject) => {

                navigator.geolocation.getCurrentPosition(
                    (position) => {
                        resolve(position);
                    },
                    (error) => {
                        reject(error);
                    },
                    {
                        enableHighAccuracy: true,
                        timeout: 10000,
                        maximumAge: 0,
                    }
                );
            }
        );



        const latitude =
            position.coords.latitude;

        const longitude =
            position.coords.longitude;

        let location = "";



        try {

            const response =
                await fetch(
                    `https://nominatim.openstreetmap.org/reverse?format=json&lat=${latitude}&lon=${longitude}`
                );

            if (response.ok) {

                const data =
                    await response.json();

                const address =
                    data?.address;

                location = [
                    address?.road,
                    address?.suburb,
                    address?.city ||
                    address?.town ||
                    address?.village,
                    address?.state,
                    address?.postcode,
                ]
                    .filter(Boolean)
                    .join(", ");
            }

        } catch (error) {

            console.warn(
                "Failed to reverse geocode location",
                error
            );
        }

        return {
            latitude: latitude.toString(),
            longitude: longitude.toString(),
            location,
        };
    };