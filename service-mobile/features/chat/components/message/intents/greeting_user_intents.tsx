import {
    Image,
    Pressable,
    Text,
    View,
} from 'react-native';

import {
    Ionicons,
    MaterialCommunityIcons,
} from '@expo/vector-icons';

import {
    InteractionIntentProps,
} from '@/core/dto/InteractionIntentProps';

import {
    MessageFeedback,
} from '@/features/chat/components/message/message-feedback';

import {
    styles,
} from '@/features/chat/conversation.style';

import {
    LlmOrchestrationAction,
} from '@/core/dto/ChatAiMessageResponse';


export const GreetingUserIntent = (
    {
        conversation,
        index,
        onFeedback,
    }: InteractionIntentProps
) => {

    /* ================= CLARA IMAGE ================= */

    const claraImage = require(
        '@/assets/images/clara-ai/clara_hi.png'
    );


    /* ================= ACTION CLICK ================= */

    const handleAction = (
        action: LlmOrchestrationAction
    ) => {

        switch (
            action.intent
            ) {

            case 'GLOSSARY_CONTRACTLENS':

                console.log(
                    'OPEN GLOSSARY'
                );

                break;


            case 'ANALYZE_API_CONTRACT':

                console.log(
                    'OPEN ANALYZE CONTRACT'
                );

                break;


            case 'LOGIN_CONTRACTLENS':

                console.log(
                    'OPEN LOGIN'
                );

                break;


            default:

                console.log(
                    'UNKNOWN ACTION:',
                    action.intent
                );

        }

    };


    /* ================= ACTION ICON ================= */

    const renderActionIcon = (
        intent?: string|null
    ) => {

        switch (
            intent
            ) {

            case 'GLOSSARY_CONTRACTLENS':

                return (

                    <Ionicons
                        name={
                            'search-outline'
                        }
                        size={
                            16
                        }
                        color={
                            '#4338CA'
                        }
                    />

                );


            case 'ANALYZE_API_CONTRACT':

                return (

                    <MaterialCommunityIcons
                        name={
                            'rocket-launch-outline'
                        }
                        size={
                            15
                        }
                        color={
                            '#4338CA'
                        }
                    />

                );


            case 'LOGIN_CONTRACTLENS':

                return (

                    <Ionicons
                        name={
                            'lock-closed-outline'
                        }
                        size={
                            16
                        }
                        color={
                            '#4338CA'
                        }
                    />

                );


            default:

                return (

                    <Ionicons
                        name={
                            'chatbubble-outline'
                        }
                        size={
                            28
                        }
                        color={
                            '#4338CA'
                        }
                    />

                );

        }

    };


    return (

        <View
            style={
                styles.aiMessageRow
            }
        >

            {/* ================= CLARA IMAGE ================= */}

            <Image
                source={
                    claraImage
                }
                style={
                    styles.aiMessageAvatar
                }
            />


            <View
                style={
                    styles.messageContent
                }
            >

                {/* ================= MESSAGE ================= */}

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


                    {/* ================= FEEDBACK ================= */}

                    {
                        conversation.showFeedback && (

                            <MessageFeedback
                                feedback={
                                    conversation.feedback
                                }
                                onFeedback={(
                                    value: boolean
                                ) =>
                                    onFeedback(
                                        index,
                                        value
                                    )
                                }
                            />

                        )
                    }

                </View>




                {/* ================= ACTIONS ================= */}

                {
                    conversation.actions?.map(
                        (
                            action:
                            LlmOrchestrationAction,
                            actionIndex:
                            number
                        ) => (

                            <Pressable
                                key={
                                    actionIndex
                                }
                                style={
                                    styles.greetingAction
                                }
                                onPress={() =>
                                    handleAction(
                                        action
                                    )
                                }
                            >

                                {/* ================= ICON ================= */}

                                <View
                                    style={
                                        styles.greetingActionIcon
                                    }
                                >

                                    {
                                        renderActionIcon(
                                            action.intent
                                        )
                                    }

                                </View>


                                {/* ================= CONTENT ================= */}

                                <View
                                    style={
                                        styles.greetingActionContent
                                    }
                                >

                                    <Text
                                        style={
                                            styles.greetingActionHeader
                                        }
                                    >
                                        {
                                            action.ai_header
                                        }
                                    </Text>


                                    <Text
                                        style={
                                            styles.greetingActionDetail
                                        }
                                    >
                                        {
                                            action.ai_detail
                                        }
                                    </Text>

                                </View>


                                {/* ================= ARROW ================= */}

                                <Ionicons
                                    name={
                                        'chevron-forward'
                                    }
                                    size={
                                        14
                                    }
                                    color={
                                        '#4338CA'
                                    }
                                />

                            </Pressable>


                        )
                    )
                }
                {/* ================= TIME ================= */}
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

};