import {
    Text,
    View,
} from 'react-native';

import {
    styles,
} from '../../conversation.style';


type UserMessageProps = {

    conversation: any;

};


export const UserMessage = (
    {
        conversation,
    }: UserMessageProps
) => {

    return (

        <View
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


            {/* ================= TIME ================= */}

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

};