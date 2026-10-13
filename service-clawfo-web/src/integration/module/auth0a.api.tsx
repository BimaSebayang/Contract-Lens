import apiClient, {ClawfoMappingResponse} from "../client";
import {log} from "../../utils/props/async-storage";
import {getCurrentLocation, getDeviceId} from "../../utils/props/header-props";



export interface LoginResponseData {
    accessToken: string;
    refreshToken: string;
}


export interface GenerateTokenChatParams {
    codeLaundry: string;
    username: string;
    email?: string;
    handphone: string;
}

export const generateTokenChatApi = async ({
                                               codeLaundry,
                                               username,
                                               email,
                                               handphone,
                                           }: GenerateTokenChatParams): Promise<
    ClawfoMappingResponse<LoginResponseData>
> => {

    log("START: generate token chat");

    const location = await getCurrentLocation();
    const deviceId = getDeviceId();

    log("CODE LAUNDRY:", codeLaundry);
    log("USERNAME:", username);
    log("EMAIL:", email);
    log("HANDPHONE:", handphone);
    log("DEVICE ID:", deviceId);
    log("LONGITUDE:", location.longitude);
    log("LATITUDE:", location.latitude);
    log("LOCATION:", location.location);

    const response = await apiClient.post<
        ClawfoMappingResponse<LoginResponseData>
    >(
        "/V.1.0.0/mobile/pesanan-chat/generate-token-chat",
        null,
        {
            headers: {
                "X-Device-Id": deviceId,
                "X-Longitude": location.longitude,
                "X-Latitude": location.latitude,
                "X-Location": location.location,

                "C-Code-Laundry": codeLaundry,
                "C-Name-User": username,
                "C-Email-User": email,
                "C-Handphone-User": handphone,

                "Content-Type": "application/json",
            },
        }
    );

    log("HTTP STATUS:", response.status);
    log("CODE:", response.data.code);
    log("MESSAGE EN:", response.data.message.en);
    log("MESSAGE ID:", response.data.message.id);
    log("DATA:", response.data.data);

    log("FINISH: generate token chat");

    return response.data;
};

