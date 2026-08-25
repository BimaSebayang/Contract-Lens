import {
    Image,
    Text,
    View,
} from 'react-native';





import {
    InteractionIntentProps,
} from '@/core/dto/InteractionIntentProps';

import {MessageFeedback} from "@/features/chat/components/message/message-feedback";
import {styles} from "@/features/chat/conversation.style";


export const UnknownIntent = (
    {
        conversation,
        index,
        onFeedback,
    }: InteractionIntentProps
) => {

    const claraImage =require(
        '@/assets/images/clara-ai/clara_confused.png'
    );


    return (

        <View
            style={
                styles.aiMessageRow
            }
        >

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