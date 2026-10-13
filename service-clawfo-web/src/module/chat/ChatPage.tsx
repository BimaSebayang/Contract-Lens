import {
    useEffect,
    useRef,
    useState,
} from 'react';

import { Client } from "@stomp/stompjs";

import {
    useParams,
    useSearchParams
} from "react-router-dom";

import {
    ClawfoChatRequest,
    ClawfoMessageRequest,
    ClawfoMessageResponse,
    connectChat,
    getChatApi,
    sendChat,
} from '../../integration/module/chat0a.api';

import {
    styles,
} from '../../component/chat/chat.style';

import {
    generateTokenChatApi
} from "../../integration/module/auth0a.api";

import {
    log,
    removeStorage,
    setStorage,
    STORAGE_KEY
} from "../../utils/props/async-storage";

import {
    generateTicketId
} from "../../utils/props/header-props";


/* =========================================================
   ICON
   ========================================================= */


//TODO : typing indicator need to enhance and Message for Click File And Photo
function Icon({
                  name,
                  size = 20,
                  color = 'currentColor',
              }: {
    name: 'back' | 'sparkles' | 'chat' | 'attach' | 'send';
    size?: number;
    color?: string;
}) {

    if (name === 'back') {
        return (
            <svg
                width={size}
                height={size}
                viewBox="0 0 24 24"
                fill="none"
                stroke={color}
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
            >
                <path d="M19 12H5" />
                <path d="M12 19l-7-7 7-7" />
            </svg>
        );
    }

    if (name === 'sparkles') {
        return (
            <svg
                width={size}
                height={size}
                viewBox="0 0 24 24"
                fill="none"
                stroke={color}
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
            >
                <path d="m12 3-1.5 4.5L6 9l4.5 1.5L12 15l1.5-4.5L18 9l-4.5-1.5L12 3Z" />
                <path d="m19 15-.75 2.25L16 18l2.25.75L19 21l.75-2.25L22 18l-2.25-.75L19 15Z" />
                <path d="m5 14-.75 2.25L2 17l2.25.75L5 20l.75-2.25L8 17l-2.25-.75L5 14Z" />
            </svg>
        );
    }

    if (name === 'chat') {
        return (
            <svg
                width={size}
                height={size}
                viewBox="0 0 24 24"
                fill="none"
                stroke={color}
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
            >
                <path d="M21 11.5a8.38 8.38 0 0 1-9 8.5 9.1 9.1 0 0 1-4-.9L3 21l1.9-4.4A8.5 8.5 0 1 1 21 11.5Z" />
            </svg>
        );
    }

    if (name === 'attach') {
        return (
            <svg
                width={size}
                height={size}
                viewBox="0 0 24 24"
                fill="none"
                stroke={color}
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
            >
                <path d="m21.44 11.05-9.19 9.19a6 6 0 0 1-8.49-8.49l9.19-9.19a4 4 0 0 1 5.66 5.66l-9.2 9.19a2 2 0 0 1-2.83-2.83l8.49-8.48" />
            </svg>
        );
    }

    return (
        <svg
            width={size}
            height={size}
            viewBox="0 0 24 24"
            fill="none"
            stroke={color}
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
        >
            <path d="m22 2-7 20-4-9-9-4Z" />
            <path d="M22 2 11 13" />
        </svg>
    );
}


/* =========================================================
   COLORS
   ========================================================= */

const COLORS = {
    background: '#FFFFFF',
    white: '#FFFFFF',
    navy: '#07152F',
    secondary: '#405477',
    blue: '#168BFF',
    blueSoft: '#EAF4FF',
    border: '#E5EAF1',
    borderSoft: '#EEF2F7',
    gray: '#8290AA',
    grayLight: '#F5F7FA',
    green: '#18B86A',
};


/* =========================================================
   PROPS
   ========================================================= */

interface ChatPageProps {
    onBack: () => void;
    chatDetail?: ClawfoChatRequest | null;
}


/* =========================================================
   INITIAL MESSAGE
   ========================================================= */

const INITIAL_MESSAGES: ClawfoMessageResponse[] = [
    {
        id: '1',
        type: 'CLAWFO',
        name: 'CLAwfo Asisten',
        message: 'Halo! 👋 Ada yang bisa CLAwfo bantu hari ini?',
        time: '10:24',
    },
];


/* =========================================================
   COMPONENT
   ========================================================= */

export default function ChatPage({
                                     onBack,
                                     chatDetail,
                                 }: ChatPageProps) {

    const scrollContainerRef =
        useRef<HTMLDivElement | null>(null);

    const fileInputRef =
        useRef<HTMLInputElement | null>(null);

    const stompClientRef =
        useRef<Client | null>(null);

    const cameraInputRef =
        useRef<HTMLInputElement | null>(null);

    const initializedRef =
        useRef(false);

    const pendingMessageIdRef =
        useRef<string | null>(null);

    const [message, setMessage] =
        useState('');

    const [sending, setSending] =
        useState(false);

    const [messages, setMessages] =
        useState<ClawfoMessageResponse[]>(
            INITIAL_MESSAGES
        );

    const { chatId } =
        useParams<{
            chatId: string;
        }>();

    const [searchParams] =
        useSearchParams();

    const laundryCode =
        searchParams.get("laundryCode");


    /* =====================================================
       AUTO SCROLL
       ===================================================== */

    const scrollToBottom = (
        smooth = true,
    ) => {

        const container =
            scrollContainerRef.current;

        if (!container) {
            return;
        }

        container.scrollTo({
            top: container.scrollHeight,
            behavior: smooth
                ? 'smooth'
                : 'auto',
        });
    };


    /* =====================================================
       INITIALIZE
       ===================================================== */

    useEffect(() => {

        if (initializedRef.current) {
            return;
        }

        initializedRef.current = true;

        initialize();

    }, []);


    /* =====================================================
       AUTO SCROLL ON MESSAGE CHANGE
       ===================================================== */

    useEffect(() => {
        scrollToBottom(false);
    }, [messages]);


    /* =====================================================
       INITIALIZE FLOW
       ===================================================== */

    const initialize = async () => {

        const initialized =
            await initializeChat();

        await getChat(
            initialized
        );
    };


    /* =====================================================
       GET CHAT
       ===================================================== */

    const getChat = async (
        initialized: boolean
    ) => {

        if (!initialized) {

            log(
                "Initialized issued",
                "Intialized cannot be generate"
            );

            return;
        }

        try {

            const ticketId =
                generateTicketId();

            if (!chatId) {

                log(
                    "Logger Chat Id not Found Weh"
                );

                return;
            }

            const response =
                await getChatApi(
                    chatId,
                    ticketId
                );

            if (
                Array.isArray(
                    response.messages
                ) &&
                response.messages.length > 0
            ) {

                setMessages(
                    response.messages
                );
            }


            /* =================================================
               CONNECT WEBSOCKET
               ================================================= */

            stompClientRef.current =
                connectChat(
                    chatId,
                    (
                        message: ClawfoMessageRequest
                    ) => {

                        console.log(
                            "[CONNECT-CHAT] MESSAGE CALLBACK",
                            message
                        );


                        /* =====================================
                           ADD MESSAGE
                           ===================================== */

                        setMessages(
                            (current) => [
                                ...current,
                                message,
                            ]
                        );
                        setSending(false)
                        setMessage('');
                    }
                );

        } catch (error) {

            console.error(
                "Failed to get chat:",
                error
            );
        }
    };


    /* =====================================================
       INITIALIZE CHAT TOKEN
       ===================================================== */

    const initializeChat =
        async (): Promise<boolean> => {

            try {

                if (!laundryCode) {
                    return false;
                }

                const response =
                    await generateTokenChatApi({
                        codeLaundry:
                        laundryCode,

                        username:
                            "Bima",

                        email:
                            "bima@clawfo.com",

                        handphone:
                            "081234567890",
                    });


                if (
                    response.code ===
                    "auth.login.success"
                ) {

                    setStorage(
                        STORAGE_KEY.CHAT_ACCESS_TOKEN,
                        response.data.accessToken
                    );

                    setStorage(
                        STORAGE_KEY.CHAT_REFRESH_TOKEN,
                        response.data.refreshToken
                    );

                    return true;
                }


                /* =============================================
                   CLEAR TOKEN
                   ============================================= */

                removeStorage(
                    STORAGE_KEY.CHAT_ACCESS_TOKEN
                );

                removeStorage(
                    STORAGE_KEY.CHAT_REFRESH_TOKEN
                );

                return false;

            } catch (error) {

                console.error(
                    "Failed to initialize chat:",
                    error
                );

                return false;
            }
        };


    /* =====================================================
       SEND MESSAGE
       ===================================================== */

    const handleSend = () => {

        const value =
            message.trim();


        /* =============================================
           VALIDATION
           ============================================= */

        if (!value) {
            return;
        }

        if (sending) {
            return;
        }

        if (!chatId) {

            log(
                "CHAT ID",
                "chat id cannot be found"
            );

            return;
        }

        if (
            !stompClientRef.current ||
            !stompClientRef.current.connected
        ) {

            log(
                "CHAT",
                "websocket is not connected"
            );

            return;
        }


        /* =============================================
           CREATE MESSAGE
           ============================================= */

        const newMessage:
            ClawfoMessageRequest = {

            id:
                Date.now().toString(),

            type:
                'USER',

            message:
            value,

            name:
                chatDetail?.username ??
                'anon',

            time:
                new Date().toLocaleTimeString(
                    'id-ID',
                    {
                        hour: '2-digit',
                        minute: '2-digit',
                    },
                ),
        };


        /* =============================================
           SET PENDING MESSAGE
           ============================================= */

        pendingMessageIdRef.current =
            newMessage.id;


        /* =============================================
           START LOADING
           ============================================= */

        setSending(true);


        /* =============================================
           SEND
           ============================================= */

        sendChat(
            stompClientRef.current,
            chatId,
            newMessage
        );
    };


    /* =====================================================
       KEYBOARD
       ===================================================== */

    const handleKeyDown = (
        event: React.KeyboardEvent<HTMLTextAreaElement>,
    ) => {

        if (
            event.key === 'Enter' &&
            !event.shiftKey
        ) {

            event.preventDefault();

            handleSend();
        }
    };


    /* =====================================================
       ATTACHMENT
       ===================================================== */

    const handleAttachmentClick = () => {
        fileInputRef.current?.click();
    };


    const handleCameraClick = () => {
        cameraInputRef.current?.click();
    };


    const handleFileChange = (
        event: React.ChangeEvent<HTMLInputElement>,
    ) => {

        const file =
            event.target.files?.[0];

        if (!file) {
            return;
        }

        console.log(
            'Selected attachment:',
            file,
        );

        event.target.value = '';
    };


    const handleAttachmentMenu = () => {
        handleAttachmentClick();
    };


    /* =====================================================
       RENDER
       ===================================================== */

    return (
        <div style={styles.container}>

            {/* =================================================
                HEADER
                ================================================= */}

            <div style={styles.header}>

                <div style={styles.headerTop}>

                    <button
                        type="button"
                        style={styles.backButton}
                        onClick={onBack}
                    >
                        <Icon
                            name="back"
                            size={20}
                            color={COLORS.navy}
                        />
                    </button>


                    <div
                        style={
                            styles.headerTitleContainer
                        }
                    >

                        <div
                            style={
                                styles.headerTitle
                            }
                        >
                            {
                                chatDetail?.username ??
                                'CLAwfo Asisten'
                            }
                        </div>


                        <div
                            style={
                                styles.headerSubtitle
                            }
                        >
                            Asisten operasional laundry
                        </div>

                    </div>


                    <div
                        style={
                            styles.onlineContainer
                        }
                    >

                        <div
                            style={
                                styles.onlineDot
                            }
                        />

                        <span
                            style={
                                styles.onlineText
                            }
                        >
                            Online
                        </span>

                    </div>

                </div>

            </div>


            {/* =================================================
                CHAT
                ================================================= */}

            <div
                ref={scrollContainerRef}
                style={styles.chatScroll}
            >

                <div
                    style={styles.chatContent}
                >

                    {/* =================================================
                        DATE
                        ================================================= */}

                    <div
                        style={
                            styles.dateContainer
                        }
                    >

                        <div
                            style={
                                styles.dateLine
                            }
                        />

                        <span
                            style={
                                styles.dateText
                            }
                        >
                            Hari ini
                        </span>

                        <div
                            style={
                                styles.dateLine
                            }
                        />

                    </div>


                    {/* =================================================
                        CHAT DETAIL
                        ================================================= */}

                    {chatDetail?.chatId && (

                        <div
                            style={
                                styles.chatDetail
                            }
                        >

                            <Icon
                                name="chat"
                                size={14}
                                color={COLORS.gray}
                            />

                            <span
                                style={
                                    styles.chatDetailText
                                }
                                title={
                                    `Chat #${chatDetail.chatId}`
                                }
                            >
                                Chat #{chatDetail.chatId}
                            </span>

                        </div>

                    )}


                    {/* =================================================
                        MESSAGES
                        ================================================= */}

                    {messages.map((item) => {

                        const isUser =
                            item.type === 'USER';

                        return (

                            <div
                                key={item.id}
                                style={
                                    isUser
                                        ? styles.userMessageRow
                                        : styles.clawfoMessageRow
                                }
                            >

                                {!isUser && (

                                    <div
                                        style={
                                            styles.clawfoAvatar
                                        }
                                    >

                                        <Icon
                                            name="sparkles"
                                            size={18}
                                            color={
                                                COLORS.blue
                                            }
                                        />

                                    </div>

                                )}


                                <div
                                    style={
                                        isUser
                                            ? styles.userMessageContainer
                                            : styles.clawfoMessageContainer
                                    }
                                >

                                    {!isUser && (

                                        <div
                                            style={
                                                styles.messageSender
                                            }
                                        >
                                            {item.name}
                                        </div>

                                    )}


                                    <div
                                        style={
                                            isUser
                                                ? styles.userMessage
                                                : styles.clawfoMessage
                                        }
                                    >

                                        <div
                                            style={
                                                isUser
                                                    ? styles.userMessageText
                                                    : styles.clawfoMessageText
                                            }
                                        >
                                            {item.message}
                                        </div>

                                    </div>


                                    <div
                                        style={
                                            isUser
                                                ? styles.userMessageTime
                                                : styles.clawfoMessageTime
                                        }
                                    >
                                        {item.time}
                                    </div>

                                </div>

                            </div>

                        );
                    })}

                </div>

            </div>


            {/* =================================================
                INPUT
                ================================================= */}

            <div
                style={styles.inputWrapper}
            >

                <div
                    style={styles.inputContainer}
                >

                    <button
                        type="button"
                        style={styles.attachButton}
                        onClick={
                            handleAttachmentMenu
                        }
                        title="Tambah lampiran"
                        disabled={sending}
                    >
                        <Icon
                            name="attach"
                            size={22}
                            color={COLORS.blue}
                        />
                    </button>


                    {/* =================================================
                        TEXTAREA
                        ================================================= */}

                    <textarea
                        value={message}
                        onChange={(event) =>
                            setMessage(
                                event.target.value,
                            )
                        }
                        onKeyDown={
                            handleKeyDown
                        }
                        placeholder="Tulis pesan..."
                        maxLength={1000}
                        rows={1}
                        style={styles.input}
                        disabled={sending}
                    />


                    {/* =================================================
                        SEND BUTTON
                        ================================================= */}

                    <button
                        type="button"
                        style={{
                            ...styles.sendButton,

                            ...(
                                !message.trim() ||
                                sending
                                    ? styles.sendButtonDisabled
                                    : {}
                            ),
                        }}
                        onClick={
                            handleSend
                        }
                        disabled={
                            !message.trim() ||
                            sending
                        }
                    >

                        {sending ? (

                            <div
                                style={
                                    styles.sendingSpinner
                                }
                            />

                        ) : (

                            <Icon
                                name="send"
                                size={20}
                                color={COLORS.white}
                            />

                        )}

                    </button>

                </div>

            </div>


            {/* =================================================
                HIDDEN FILE INPUTS
                ================================================= */}

            <input
                ref={fileInputRef}
                type="file"
                accept="image/*,.pdf,.doc,.docx,.xls,.xlsx"
                style={{
                    display: 'none',
                }}
                onChange={
                    handleFileChange
                }
            />


            <input
                ref={cameraInputRef}
                type="file"
                accept="image/*"
                capture="environment"
                style={{
                    display: 'none',
                }}
                onChange={
                    handleFileChange
                }
            />

        </div>
    );
}