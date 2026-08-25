import {
    Image,
    Text,
    View,
} from 'react-native';

import {
    styles,
} from '../../conversation.style';

import {
    CLARA_IMAGES,
} from './clara-image';

import {
    MessageFeedback,
} from './message-feedback';

import {LlmMessageConversation} from "@/core/dto/LlmMessageConversation";


type ClaraMessageProps = {

    conversation: LlmMessageConversation;

    index: number;

    onFeedback: (
        index: number,
        feedback: boolean
    ) => void;

};


export const ClaraMessage = (
    {
        conversation,
        index,
        onFeedback,
    }: ClaraMessageProps
) => {

    const claraImage =
        CLARA_IMAGES[
            conversation.intent as
            keyof typeof CLARA_IMAGES
        ]
        ??
        CLARA_IMAGES.UNKNOWN;


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


            {/* ================= MESSAGE ================= */}

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


                    {/* ================= FEEDBACK ================= */}

                    {
                        conversation.showFeedback && (

                            <MessageFeedback
                                feedback={
                                    conversation.feedback
                                }
                                onFeedback={(value:boolean) =>
                                    onFeedback(
                                        index,
                                        value
                                    )
                                }
                            />

                        )
                    }

                </View>


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
