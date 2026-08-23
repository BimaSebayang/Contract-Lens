import {
    Animated,
    Image,
    Pressable,
    ScrollView,
    Text,
    TextInput,
    View,
} from 'react-native';

import {
    router,
} from 'expo-router';

import {
    ArrowLeft,
    Bot,
    Paperclip,
    Send,
    ThumbsDown,
    ThumbsUp,
} from 'lucide-react-native';

import {
    styles,
} from './conversation.style';

import {
    useConversationScript,
} from './conversation.script';


export default function ConversationScreen() {

    const {
        conversations,
        message,
        keyboardHeight,

        scrollViewRef,
        keyboardOffset,

        setMessage,

        scrollToBottom,
        handleChatClara,
        handleFeedback,
        handleMessageChange

    } = useConversationScript();


    return (

        <View
            style={styles.container}
        >

            {/* ================= HEADER ================= */}

            <View
                style={styles.header}
            >
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
                        style={styles.profileContainer}
                    >

                        <View
                            style={styles.nameContainer}
                        >
                            <Text
                                style={styles.profileName}
                            >
                                CLAra
                            </Text>


                            <View
                                style={styles.verifiedBadge}
                            >
                                <Bot
                                    size={12}
                                    color="#FFFFFF"
                                    strokeWidth={3}
                                />
                            </View>

                        </View>


                        <Text
                            style={styles.profileSubtitle}
                        >
                            ContractLens AI
                        </Text>


                        <View
                            style={styles.onlineContainer}
                        >
                            <View
                                style={styles.onlineDot}
                            />

                            <Text
                                style={styles.onlineText}
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
                        style={styles.messageAvatar}
                    />


                    <View
                        style={styles.messageContent}
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

                        </View>


                        <Text
                            style={styles.messageTime}
                        >
                            09:41
                        </Text>

                    </View>

                </View>


                {/* ================= CONVERSATIONS ================= */}

                {conversations.map(
                    (
                        conversation,
                        index
                    ) => {

                        /* ================= USER ================= */

                        if (
                            conversation.role ===
                            'user'
                        ) {

                            return (

                                <View
                                    key={
                                        `${conversation.timestamp}-${index}`
                                    }
                                    style={
                                        styles.userMessageRow
                                    }
                                >

                                    <View
                                        style={
                                            conversation.content.styleView
                                        }
                                    >

                                        <Text
                                            style={
                                                conversation.content.styleText
                                            }
                                        >
                                            {
                                                conversation.content.message
                                            }
                                        </Text>

                                    </View>


                                    <Text
                                        style={
                                            conversation.content.styleTime
                                        }
                                    >
                                        {
                                            conversation.timestamp
                                        }
                                    </Text>

                                </View>

                            );

                        }


                        /* ================= ASSISTANT ================= */

                        if (
                            conversation.role ===
                            'assistant'
                        ) {
                            return (

                                <View
                                    key={
                                        `${conversation.timestamp}-${index}`
                                    }
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
                                            styles.messageContent
                                        }
                                    >

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
                                                {
                                                    conversation.content.message
                                                }
                                            </Text>

                                            {
                                                conversation.showFeedback && (
                                                   <>
                                                    <View
                                                        style={
                                                            styles.divider
                                                        }
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


                                                           {/* ================= LIKE ================= */}

                                                           <Pressable
                                                               style={
                                                                   styles.feedbackButton
                                                               }
                                                               onPress={() =>
                                                                   handleFeedback(
                                                                       index,
                                                                       true
                                                                   )
                                                               }
                                                           >

                                                               <ThumbsUp
                                                                   size={20}
                                                                   color={
                                                                       conversation.feedback ===true
                                                                           ? '#4F46E5'
                                                                           : '#64748B'
                                                                   }
                                                                   fill={
                                                                       conversation.feedback === true
                                                                           ? '#E0E7FF'
                                                                           : 'transparent'
                                                                   }
                                                               />

                                                           </Pressable>


                                                           {/* ================= DISLIKE ================= */}

                                                           <Pressable
                                                               style={
                                                                   styles.feedbackButton
                                                               }
                                                               onPress={() =>
                                                                   handleFeedback(
                                                                       index,
                                                                       false
                                                                   )
                                                               }
                                                           >

                                                               <ThumbsDown
                                                                   size={20}
                                                                   color={
                                                                       conversation.feedback === false
                                                                           ? '#4F46E5':'#64748B'

                                                                   }
                                                                   fill={
                                                                       conversation.feedback === false
                                                                           ? '#E0E7FF':'transparent'
                                                                   }
                                                               />

                                                           </Pressable>

                                                       </View>
                                                   </>

                                                )
                                            }



                                        </View>


                                        <Text
                                            style={
                                                styles.messageTime
                                            }
                                        >
                                            {
                                                conversation.timestamp
                                            }
                                        </Text>

                                    </View>

                                </View>

                            );
                        }
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
                        style={styles.attachButton}
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
                        value={message.message}
                        onChangeText={handleMessageChange}
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

                            !message.message.trim() &&
                            styles.sendButtonDisabled,
                        ]}
                        onPress={
                            handleChatClara
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