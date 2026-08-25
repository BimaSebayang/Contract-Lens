import {
    useEffect,
    useRef,
    useState,
} from 'react';

import {
    Animated,
    Keyboard,
    Platform,
    ScrollView,
} from 'react-native';



import {
    styles,
} from './conversation.style';

import {LlmMessageConversation, LlmMessageMap, LlmMessageRole} from "@/core/dto/LlmMessageConversation";
import chatService from "@/integration/chat/service/chat.service";


export function useConversationScript() {

    /* ================= STATE ================= */

    const getConversationId = (): string => {

        if (conversationId) {
            return conversationId;
        }


        const newConversationId =
            Array.from(
                { length: 5 },
                () =>
                    Math.random()
                        .toString(36)
                        .substring(2)
            ).join('');


        setConversationId(
            newConversationId
        );


        return newConversationId;

    };

    const [
        isClaraLoading,
        setIsClaraLoading,
    ] = useState(false);

    const [
        conversationId,
        setConversationId,
    ] = useState('');

    const [
        message,
        setMessage,
    ] = useState<LlmMessageMap>({
        message:'',
        styleView:'',
        styleText:'',
        styleTime:''
    });


    const [
        conversations,
        setConversations,
    ] = useState<
        LlmMessageConversation[]
    >([]);

    const [
        keyboardHeight,
        setKeyboardHeight,
    ] = useState<number>(0);


    /* ================= REF ================= */

    const scrollViewRef =
        useRef<ScrollView>(null);

    const keyboardOffset = useRef(
        new Animated.Value(0)
    ).current;


    /* ================= SCROLL ================= */

    const scrollToBottom = (
        animated = true
    ) => {

        requestAnimationFrame(() => {

            setTimeout(() => {

                scrollViewRef.current
                    ?.scrollToEnd({
                        animated,
                    });

            }, 50);

        });

    };


    /* ================= KEYBOARD ================= */

    useEffect(() => {

        const keyboardShowEvent =
            Platform.OS === 'ios'
                ? 'keyboardWillShow'
                : 'keyboardDidShow';

        const keyboardHideEvent =
            Platform.OS === 'ios'
                ? 'keyboardWillHide'
                : 'keyboardDidHide';


        const keyboardShowListener =
            Keyboard.addListener(
                keyboardShowEvent,
                (event) => {

                    const height =
                        event.endCoordinates.height;

                    setKeyboardHeight(height);

                    Animated.timing(
                        keyboardOffset,
                        {
                            toValue: -height,

                            duration:
                                Platform.OS === 'ios'
                                    ? event.duration ?? 250
                                    : 250,

                            useNativeDriver: true,
                        }
                    ).start();

                    setTimeout(() => {

                        scrollToBottom();

                    }, 100);

                }
            );


        const keyboardHideListener =
            Keyboard.addListener(
                keyboardHideEvent,
                (event) => {

                    setKeyboardHeight(0);

                    Animated.timing(
                        keyboardOffset,
                        {
                            toValue: 0,

                            duration:
                                Platform.OS === 'ios'
                                    ? event.duration ?? 250
                                    : 250,

                            useNativeDriver: true,
                        }
                    ).start();

                }
            );


        return () => {

            keyboardShowListener.remove();

            keyboardHideListener.remove();

        };

    }, []);


    /* ================= AUTO SCROLL ================= */

    useEffect(() => {

        if (
            conversations.length > 0
        ) {
            scrollToBottom();
        }

    }, [conversations]);

    /* ================= LLM MESSAGE ================= */
    const chatClaraSendCallback = async () => {

        const result =
            await chatService.sendMessage(
                getConversationId(),
                message.message
            );

        const aiConversation: LlmMessageConversation = {
            role: 'assistant',
            content: result.content[0],
            feedback: null,
            timestamp:result.timestamp,
            showFeedback:true,
            intent:result.intent,
            actions:result.actions
        };


        setConversations(
            (previousConversations) => [
                ...previousConversations,
                aiConversation,
            ]
        );

        setIsClaraLoading(false)
    }

    /* ================= SEND MESSAGE ================= */

    const handleChatClara = async () => {
        if(message.message && !isClaraLoading){
            const send_message : LlmMessageMap = {
                message:message.message,
                styleView:styles.userMessage,
                styleText:styles.userMessageText,
                styleTime:styles.messageTime
            }

            const userConversation: LlmMessageConversation = {
                role: 'user',
                content: send_message,
                feedback: false,
                timestamp:
                    new Date()
                        .toLocaleTimeString(
                            [],
                            {
                                hour: '2-digit',
                                minute: '2-digit',
                            }
                        ),
                showFeedback:true
            };


            setConversations(
                (previousConversations) => [
                    ...previousConversations,
                    userConversation,
                ]
            );

            setMessage({
                message:'',
                styleView:'',
                styleText:'',
                styleTime:''
            });

            setIsClaraLoading(true)
            await chatClaraSendCallback();
        }

    };


    const handleMessageChange = (
        text: string
    ) => {
        setMessage(
            (previousMessage) => ({
                ...previousMessage,
                message: text,
            })
        );

    };

    /* ================= FEEDBACK ================= */

    const handleFeedback = (
        index: number,
        feedback: boolean
    ) => {

        setConversations(
            (previousConversations) =>
                previousConversations.map(
                    (
                        conversation,
                        conversationIndex
                    ) => {

                        if (
                            conversationIndex !== index
                        ) {
                            return conversation;
                        }


                        return {
                            ...conversation,
                            feedback,
                        };

                    }
                )
        );

    };


    /* ================= RETURN ================= */

    return {

        conversations,

        message,

        keyboardHeight,


        scrollViewRef,

        keyboardOffset,


        setMessage,


        scrollToBottom,

        handleChatClara,

        handleFeedback,
        handleMessageChange,
        isClaraLoading
    };

}