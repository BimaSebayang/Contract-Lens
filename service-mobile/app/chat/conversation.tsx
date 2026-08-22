import { useEffect, useRef, useState } from 'react';
import {
    Animated,
    Image,
    Keyboard,
    Platform,
    Pressable,
    ScrollView,
    StyleSheet,
    Text,
    TextInput,
    View,
} from 'react-native';

import { router } from 'expo-router';

import {
    ArrowLeft,
    Bot,
    Paperclip,
    Send,
    ThumbsDown,
    ThumbsUp,
} from 'lucide-react-native';

type FeedbackType = 'like' | 'dislike' | null;

type ChatMessage = {
    id: string;
    type: 'user' | 'assistant';
    message: string;
};

export default function ConversationScreen() {
    const [message, setMessage] = useState('');

    const [feedback, setFeedback] =
        useState<FeedbackType>(null);

    const [messages, setMessages] =
        useState<ChatMessage[]>([]);

    const [keyboardHeight, setKeyboardHeight] =
        useState(0);

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

        /*
         * Nanti API CLAra masuk di sini.
         *
         * Contoh:
         *
         * const response =
         *     await chatService.send(
         *         trimmedMessage
         *     );
         *
         * setMessages(previousMessages => [
         *     ...previousMessages,
         *     {
         *         id: Date.now().toString(),
         *         type: 'assistant',
         *         message: response.message,
         *     },
         * ]);
         */
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

    return (
        <View style={styles.container}>

            {/* ================= HEADER ================= */}

            <View style={styles.header}>
                <View
                    style={styles.headerContent}
                >
                    <Pressable
                        style={styles.backButton}
                        onPress={() =>
                            router.back()
                        }
                    >
                        <ArrowLeft
                            size={16}
                            color="#4F46E5"
                            strokeWidth={3.5}
                        />
                    </Pressable>

                    <Image
                        source={require(
                            '@/assets/images/clara-ai/clara-avatar.png'
                        )}
                        style={styles.headerAvatar}
                    />

                    <View
                        style={
                            styles.profileContainer
                        }
                    >
                        <View
                            style={
                                styles.nameContainer
                            }
                        >
                            <Text
                                style={
                                    styles.profileName
                                }
                            >
                                CLAra
                            </Text>

                            <View
                                style={
                                    styles.verifiedBadge
                                }
                            >
                                <Bot
                                    size={12}
                                    color="#FFFFFF"
                                    strokeWidth={3}
                                />
                            </View>
                        </View>

                        <Text
                            style={
                                styles.profileSubtitle
                            }
                        >
                            ContractLens AI
                        </Text>

                        <View
                            style={
                                styles.onlineContainer
                            }
                        >
                            <View
                                style={
                                    styles.onlineDot
                                }
                            />

                            <Text
                                style={
                                    styles.onlineText
                                }
                            >
                                Online
                            </Text>
                        </View>
                    </View>
                </View>
            </View>

            {/* ================= CHAT ================= */}

            <ScrollView
                ref={scrollViewRef}
                style={styles.scrollView}
                contentContainerStyle={[
                    styles.scrollContent,
                    {
                        paddingBottom:
                            keyboardHeight > 0
                                ? keyboardHeight + 24
                                : 24,
                    },
                ]}
                showsVerticalScrollIndicator={false}
                keyboardShouldPersistTaps="handled"
                keyboardDismissMode="interactive"
                onContentSizeChange={() =>
                    scrollToBottom(false)
                }
            >
                {/* ================= DATE ================= */}

                <View
                    style={styles.dateContainer}
                >
                    <View
                        style={styles.dateLine}
                    />

                    <Text
                        style={styles.dateText}
                    >
                        Hari ini
                    </Text>

                    <View
                        style={styles.dateLine}
                    />
                </View>

                {/* ================= INITIAL MESSAGE ================= */}

                <View
                    style={styles.messageRow}
                >
                    <Image
                        source={require(
                            '@/assets/images/clara-ai/clara-avatar.png'
                        )}
                        style={
                            styles.messageAvatar
                        }
                    />

                    <View
                        style={
                            styles.messageContent
                        }
                    >
                        <View
                            style={styles.botMessage}
                        >
                            <Text
                                style={
                                    styles.botMessageTitle
                                }
                            >
                                SobatCLAra bisa memulai
                                percakapan kapan saja
                            </Text>

                            <Text
                                style={
                                    styles.botMessageText
                                }
                            >
                                Tanyakan apa saja untuk
                                memulai percakapan.
                            </Text>

                            <View
                                style={styles.divider}
                            />

                            <View
                                style={
                                    styles.feedbackContainer
                                }
                            >
                                <Text
                                    style={
                                        styles.rightanswer
                                    }
                                >
                                    jawaban memuaskan?
                                </Text>

                                <Pressable
                                    style={
                                        styles.feedbackButton
                                    }
                                    onPress={() =>
                                        handleFeedback(
                                            'like'
                                        )
                                    }
                                >
                                    <ThumbsUp
                                        size={20}
                                        color={
                                            feedback ===
                                            'like'
                                                ? '#4F46E5'
                                                : '#64748B'
                                        }
                                        fill={
                                            feedback ===
                                            'like'
                                                ? '#E0E7FF'
                                                : 'transparent'
                                        }
                                    />
                                </Pressable>

                                <Pressable
                                    style={
                                        styles.feedbackButton
                                    }
                                    onPress={() =>
                                        handleFeedback(
                                            'dislike'
                                        )
                                    }
                                >
                                    <ThumbsDown
                                        size={20}
                                        color={
                                            feedback ===
                                            'dislike'
                                                ? '#4F46E5'
                                                : '#64748B'
                                        }
                                        fill={
                                            feedback ===
                                            'dislike'
                                                ? '#E0E7FF'
                                                : 'transparent'
                                        }
                                    />
                                </Pressable>
                            </View>
                        </View>

                        <Text
                            style={
                                styles.messageTime
                            }
                        >
                            09:41
                        </Text>
                    </View>
                </View>

                {/* ================= CONVERSATION ================= */}

                {messages.map(
                    (item, index) => {
                        if (
                            item.type === 'user'
                        ) {
                            return (
                                <View
                                    key={item.id}
                                    style={
                                        styles.userMessageRow
                                    }
                                >
                                    <View
                                        style={
                                            styles.userMessage
                                        }
                                    >
                                        <Text
                                            style={
                                                styles.userMessageText
                                            }
                                        >
                                            {item.message}
                                        </Text>
                                    </View>
                                </View>
                            );
                        }

                        return (
                            <View
                                key={item.id}
                                style={
                                    styles.aiMessageRow
                                }
                            >
                                <Image
                                    source={require(
                                        '@/assets/images/clara-ai/clara-avatar.png'
                                    )}
                                    style={
                                        styles.aiMessageAvatar
                                    }
                                />

                                <View
                                    style={
                                        styles.aiMessage
                                    }
                                >
                                    <Text
                                        style={
                                            styles.aiMessageText
                                        }
                                    >
                                        {item.message}
                                    </Text>
                                </View>
                            </View>
                        );
                    }
                )}
            </ScrollView>

            {/* ================= INPUT ================= */}

            <Animated.View
                style={[
                    styles.inputWrapper,
                    {
                        transform: [
                            {
                                translateY:
                                keyboardOffset,
                            },
                        ],
                    },
                ]}
            >
                <View
                    style={styles.inputContainer}
                >
                    <Pressable
                        style={
                            styles.attachButton
                        }
                        onPress={() => {
                            console.log(
                                'Open attachment picker'
                            );
                        }}
                    >
                        <Paperclip
                            size={20}
                            color="#6366F1"
                        />
                    </Pressable>

                    <TextInput
                        value={message}
                        onChangeText={setMessage}
                        placeholder="Chat Di sini..."
                        placeholderTextColor="#94A3B8"
                        style={styles.input}
                        multiline
                        maxLength={1000}
                        onFocus={() =>
                            scrollToBottom()
                        }
                    />

                    <Pressable
                        style={[
                            styles.sendButton,
                            !message.trim() &&
                            styles.sendButtonDisabled,
                        ]}
                        onPress={
                            handleSendMessage
                        }
                    >
                        <Send
                            size={20}
                            color="#FFFFFF"
                            strokeWidth={2.5}
                        />
                    </Pressable>
                </View>
            </Animated.View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#F8FAFC',
    },

    /* ================= HEADER ================= */

    header: {
        backgroundColor: '#3531C9',
        paddingTop: 40,
        paddingBottom: 15,
        borderBottomLeftRadius: 28,
        borderBottomRightRadius: 28,
    },

    headerContent: {
        flexDirection: 'row',
        alignItems: 'center',
        paddingHorizontal: 24,
    },

    backButton: {
        width: 40,
        height: 40,
        backgroundColor: '#FFFFFF',
        borderRadius: 16,
        justifyContent: 'center',
        alignItems: 'center',
        marginRight: 25,
    },

    headerAvatar: {
        width: 60,
        height: 60,
        borderRadius: 20,
        marginRight: 16,
    },

    profileContainer: {
        justifyContent: 'center',
    },

    nameContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 8,
    },

    profileName: {
        color: '#FFFFFF',
        fontSize: 18,
        fontWeight: '800',
    },

    verifiedBadge: {
        width: 22,
        height: 22,
        borderRadius: 11,
        backgroundColor: '#7C7CFF',
        justifyContent: 'center',
        alignItems: 'center',
    },

    profileSubtitle: {
        color: '#E0E7FF',
        fontSize: 11,
        marginTop: 4,
    },

    onlineContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginTop: 8,
        gap: 8,
    },

    onlineDot: {
        width: 8,
        height: 8,
        borderRadius: 10,
        backgroundColor: '#22C55E',
    },

    onlineText: {
        color: '#FFFFFF',
        fontSize: 9,
    },

    /* ================= CONTENT ================= */

    scrollView: {
        flex: 1,
    },

    scrollContent: {
        paddingHorizontal: 20,
        paddingTop: 20,
    },

    /* ================= DATE ================= */

    dateContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 12,
        marginTop: 8,
        marginBottom: 28,
    },

    dateLine: {
        flex: 1,
        height: 1,
        backgroundColor: '#E2E8F0',
    },

    dateText: {
        color: '#4338CA',
        backgroundColor: '#EEF2FF',
        paddingHorizontal: 18,
        paddingVertical: 9,
        borderRadius: 18,
        fontSize: 9,
        fontWeight: '600',
    },

    /* ================= INITIAL MESSAGE ================= */

    messageRow: {
        flexDirection: 'row',
        alignItems: 'flex-start',
    },

    messageAvatar: {
        width: 58,
        height: 58,
        borderRadius: 16,
        marginRight: 12,
    },

    messageContent: {
        flex: 1,
    },

    botMessage: {
        backgroundColor: '#FFFFFF',
        borderRadius: 24,
        borderTopLeftRadius: 6,
        padding: 24,
        shadowColor: '#64748B',
        shadowOpacity: 0.08,
        shadowRadius: 12,
        elevation: 3,
    },

    botMessageTitle: {
        color: '#17203A',
        fontWeight: '800',
        marginBottom: 18,
        fontSize: 14,
        lineHeight: 18,
    },

    botMessageText: {
        color: '#334155',
        fontSize: 12,
        lineHeight: 18,
    },

    divider: {
        height: 1,
        backgroundColor: '#E2E8F0',
        marginTop: 20,
        marginBottom: 12,
    },

    feedbackContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 20,
    },

    rightanswer: {
        marginTop: 5,
        color: '#64748B',
        fontSize: 8,
    },

    feedbackButton: {
        padding: 4,
    },

    messageTime: {
        marginTop: 12,
        color: '#64748B',
        fontSize: 10,
    },

    /* ================= USER MESSAGE ================= */

    userMessageRow: {
        alignItems: 'flex-end',
        marginTop: 20,
    },

    userMessage: {
        maxWidth: '80%',
        backgroundColor: '#4F46E5',
        paddingHorizontal: 20,
        paddingVertical: 14,
        borderRadius: 22,
        borderBottomRightRadius: 6,
    },

    userMessageText: {
        color: '#FFFFFF',
        fontSize: 12,
        lineHeight: 18,
    },

    /* ================= AI MESSAGE ================= */

    aiMessageRow: {
        flexDirection: 'row',
        alignItems: 'flex-start',
        marginTop: 20,
    },

    aiMessageAvatar: {
        width: 42,
        height: 42,
        borderRadius: 14,
        marginRight: 12,
    },

    aiMessage: {
        flex: 1,
        backgroundColor: '#FFFFFF',
        borderRadius: 20,
        borderTopLeftRadius: 6,
        paddingHorizontal: 18,
        paddingVertical: 14,
        shadowColor: '#64748B',
        shadowOpacity: 0.06,
        shadowRadius: 10,
        elevation: 2,
    },

    aiMessageText: {
        color: '#334155',
        fontSize: 13,
        lineHeight: 20,
    },

    /* ================= INPUT ================= */

    inputWrapper: {
        paddingTop: 12,
        paddingBottom: 60,
        paddingHorizontal:10,
        backgroundColor: '#F8FAFC',
        flexShrink: 0,
    },

    inputContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        minHeight: 72,
        backgroundColor: '#FFFFFF',
        borderRadius: 36,
        paddingLeft: 8,
        paddingRight: 8,
        shadowColor: '#64748B',
        shadowOpacity: 0.12,
        shadowRadius: 18,
        elevation: 6,
    },

    attachButton: {
        width: 50,
        height: 50,
        borderRadius: 28,
        backgroundColor: '#EEF2FF',
        justifyContent: 'center',
        alignItems: 'center',
    },

    input: {
        flex: 1,
        fontSize: 15,
        lineHeight: 20,
        color: '#17203A',
        paddingHorizontal: 20,
        textAlignVertical: 'center',
        maxHeight: 100,
    },

    sendButton: {
        width: 50,
        height: 50,
        borderRadius: 25,
        backgroundColor: '#4338CA',
        justifyContent: 'center',
        alignItems: 'center',
    },

    sendButtonDisabled: {
        opacity: 0.5,
    },
});