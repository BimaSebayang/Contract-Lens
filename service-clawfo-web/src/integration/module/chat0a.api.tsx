import {getDeviceId} from "../../utils/props/header-props";
import {getStorage, log, STORAGE_KEY} from "../../utils/props/async-storage";
import apiClient, {createWebSocketClient} from "../client";
import { Client } from "@stomp/stompjs";

export interface ClawfoChatRequest {
    chatId?: string|null|undefined;
    username?:  string|null|undefined;
}

export interface ClawfoMessageRequest {
    id: string;
    type: 'USER' | 'CLAWFO' | 'KARYAWAN';
    name: string;
    message: string;
    time: string;
}

export interface ClawfoMessageResponse extends ClawfoMessageRequest{
    messageId?: string | null | undefined;
}

export interface ClawfoChatDocument{
    chatId?:string|null|undefined;
    laundryCode?:string|null|undefined;
    messages?:ClawfoMessageResponse[]|null|undefined;
}


export const getChatApi = async (
    chatId: string,
    ticketId: string
): Promise<ClawfoChatDocument> => {

    const deviceId = getDeviceId();

    log("START: get chat");
    log("CHAT ID:", chatId);
    log("TICKET ID:", ticketId);
    log("DEVICE ID:", deviceId);

    const token = await getStorage(
        STORAGE_KEY.CHAT_ACCESS_TOKEN
    );


    const response = await apiClient.get<ClawfoChatDocument>(
        `/V.1.0.0/mobile/pesanan-chat/${chatId}`,
        {
            headers: {
                "Authorization": `Bearer ${token}`,
                "X-Device-Id": deviceId,
                "X-Ticket-Id": ticketId,
                "Content-Type": "application/json",
            },
        }
    );

    log("HTTP STATUS:", response.status);
    log("CHAT DATA:", response.data);

    log("FINISH: get chat");

    return response.data;
};




export const connectChat = (
    chatId: string,
    onMessage: (message: ClawfoMessageRequest) => void
): Client | null => {

    console.log(
        "[CONNECT-CHAT] MESSAGE CALLBACK",
        ""
    );

    if (!chatId) {
        console.error(
            "[CHAT][CONNECT][FAILED] Chat ID is missing"
        );

        return null;
    }

    console.log(
        "[CHAT][CONNECT] Chat ID validated",
        {
            chatId,
        }
    );

    const accessToken = getStorage(
        STORAGE_KEY.CHAT_ACCESS_TOKEN
    );

    if (!accessToken) {
        console.error(
            "[CHAT][CONNECT][FAILED] Chat access token not found",
            {
                chatId,
            }
        );

        return null;
    }

    console.log(
        "[CHAT][CONNECT] Access token found",
        {
            chatId,
            tokenAvailable: true,
        }
    );

    const deviceId = getDeviceId();

    console.log(
        "[CHAT][CONNECT] Device ID resolved",
        {
            chatId,
            deviceId,
        }
    );

    const client = createWebSocketClient(
        accessToken,
        deviceId
    );

    console.log(
        "[CHAT][CONNECT] STOMP client created",
        {
            chatId,
            connected: client.connected,
        }
    );

    client.onConnect = () => {

        console.log(
            "[CHAT][CONNECTED] WebSocket connected successfully",
            {
                chatId,
                connected: client.connected,
            }
        );

        const destination =
            `/clawfo/topic/V.1.0.0/chat.receive/${chatId}`;

        console.log(
            "[CHAT][SUBSCRIBE] Subscribing to chat destination",
            {
                chatId,
                destination,
            }
        );

        client.subscribe(
            destination,
            (message) => {

                console.log(
                    "[CHAT][RECEIVE] Message received",
                    {
                        chatId,
                        body: message.body,
                    }
                );

                try {

                    const data: ClawfoMessageRequest =
                        JSON.parse(message.body);

                    console.log(
                        "[CHAT][RECEIVE] Message parsed successfully",
                        {
                            chatId,
                            id: data.id,
                            type: data.type,
                            name: data.name,
                            message: data.message,
                            time: data.time,
                        }
                    );

                    onMessage(data);

                    console.log(
                        "[CHAT][RECEIVE] Message forwarded to UI",
                        {
                            chatId,
                            id: data.id,
                        }
                    );

                } catch (error) {

                    console.error(
                        "[CHAT][RECEIVE][FAILED] Failed to parse message",
                        {
                            chatId,
                            body: message.body,
                            error,
                        }
                    );
                }
            }
        );

        console.log(
            "[CHAT][SUBSCRIBE] Subscription registered",
            {
                chatId,
                destination,
            }
        );
    };

    client.onStompError = (frame) => {

        console.error(
            "[CHAT][STOMP_ERROR] Broker returned STOMP error",
            {
                chatId,
                message: frame.headers["message"],
                details: frame.headers["detail"],
                body: frame.body,
            }
        );
    };

    client.onWebSocketError = (error) => {

        console.error(
            "[CHAT][WEBSOCKET_ERROR] WebSocket connection error",
            {
                chatId,
                error,
            }
        );
    };

    client.onWebSocketClose = (event) => {

        console.warn(
            "[CHAT][CLOSED] WebSocket connection closed",
            {
                chatId,
                code: event.code,
                reason: event.reason,
                wasClean: event.wasClean,
            }
        );
    };

    client.onDisconnect = () => {

        console.log(
            "[CHAT][DISCONNECTED] STOMP disconnected",
            {
                chatId,
                connected: client.connected,
            }
        );
    };

    console.log(
        "[CHAT][CONNECT] Activating STOMP client",
        {
            chatId,
        }
    );

    client.activate();

    return client;
};


export const sendChat = (
    client: Client | null,
    chatId: string,
    message: ClawfoMessageRequest
): void => {

    console.log(
        "[CHAT][SEND] Preparing to send message",
        {
            chatId,
            messageId: message.id,
            type: message.type,
            name: message.name,
            message: message.message,
        }
    );

    if (!client) {

        console.error(
            "[CHAT][SEND][FAILED] STOMP client is null",
            {
                chatId,
            }
        );

        return;
    }

    if (!client.connected) {

        console.error(
            "[CHAT][SEND][FAILED] WebSocket is not connected",
            {
                chatId,
                connected: client.connected,
                active: client.active,
            }
        );

        return;
    }

    if (!chatId) {

        console.error(
            "[CHAT][SEND][FAILED] Chat ID is missing"
        );

        return;
    }

    const destination =
        `/clawfo/communication/V.1.0.0/chat.send/${chatId}`;

    console.log(
        "[CHAT][SEND] Publishing message",
        {
            chatId,
            destination,
            messageId: message.id,
        }
    );

    client.publish({
        destination,
        body: JSON.stringify(message),
    });

    console.log(
        "[CHAT][SEND] Message published successfully",
        {
            chatId,
            messageId: message.id,
        }
    );
};