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


export type FeedbackType =
    | 'like'
    | 'dislike'
    | null;


export type ChatMessage = {
    id: string;

    type:
        | 'user'
        | 'assistant';

    message: string;
};


export function useConversationScript() {

    const [
        message,
        setMessage,
    ] = useState('');

    const [
        feedback,
        setFeedback,
    ] = useState<FeedbackType>(null);

    const [
        messages,
        setMessages,
    ] = useState<ChatMessage[]>([]);

    const [
        keyboardHeight,
        setKeyboardHeight,
    ] = useState(0);


    const scrollViewRef =
        useRef<ScrollView>(null);


    const keyboardOffset = useRef(
        new Animated.Value(0)
    ).current;


    const scrollToBottom = (
        animated = true
    ) => {
        requestAnimationFrame(() => {
            setTimeout(() => {
                scrollViewRef.current?.scrollToEnd({
                    animated,
                });
            }, 50);
        });
    };


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


    useEffect(() => {

        if (messages.length > 0) {
            scrollToBottom();
        }

    }, [messages]);


    const handleSendMessage = () => {

        const trimmedMessage =
            message.trim();


        if (!trimmedMessage) {
            return;
        }


        const userMessage: ChatMessage = {
            id: Date.now().toString(),

            type: 'user',

            message: trimmedMessage,
        };


        setMessages(
            (previousMessages) => [
                ...previousMessages,
                userMessage,
            ]
        );


        setMessage('');

    };


    const handleFeedback = (
        type: FeedbackType
    ) => {

        setFeedback(
            (currentFeedback) => {

                if (
                    currentFeedback === type
                ) {
                    return null;
                }

                return type;

            }
        );

    };


    return {

        /* ================= STATE ================= */

        message,

        feedback,

        messages,

        keyboardHeight,


        /* ================= REF ================= */

        scrollViewRef,

        keyboardOffset,


        /* ================= SETTER ================= */

        setMessage,


        /* ================= FUNCTION ================= */

        scrollToBottom,

        handleSendMessage,

        handleFeedback,

    };

}