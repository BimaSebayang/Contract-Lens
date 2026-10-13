import axios from "axios";
import {Client} from "@stomp/stompjs";


export const API_BASE_URL:string = "http://192.168.8.248:9002/clawfo-dev";

const WS_BASE_URL: string =
    API_BASE_URL.startsWith("https://")
        ? API_BASE_URL.replace(/^https/, "wss")
        : API_BASE_URL.replace(/^http/, "ws");


export const createWebSocketClient = (
    accessToken: string,
    deviceId: string
): Client => {

    return new Client({
        brokerURL:
            `${WS_BASE_URL}/ws`,

        connectHeaders: {
            Authorization:
                `Bearer ${accessToken}`,

            "X-Device-Id":
            deviceId,
        },

        reconnectDelay: 5000,
    });
};

// eslint-disable-next-line import/no-named-as-default-member
const apiClient = axios.create({
    baseURL: API_BASE_URL,
    timeout: 15000,
    headers: {
        "Content-Type": "application/json",
    },
});

export interface MessageDto {
    en: string;
    id: string;
    [language: string]: string;
}

export interface ClawfoMappingResponse<T> {
    code: string;
    message: MessageDto;
    data: T;
}

export default apiClient;