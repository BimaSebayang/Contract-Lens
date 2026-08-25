import {
    Image,
    Text,
    View,
} from 'react-native';

import {
    styles,
} from '../../conversation.style';


export const InitialMessage = () => {

    return (

        <View
            style={
                styles.messageRow
            }
        >

            {/* ================= AVATAR ================= */}

            <Image
                source={require(
                    '@/assets/images/clara-ai/clara-avatar.png'
                )}
                style={
                    styles.messageAvatar
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
                        styles.botMessage
                    }
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


                {/* ================= TIME ================= */}

                <Text
                    style={
                        styles.messageTime
                    }
                >
                    09:41
                </Text>

            </View>

        </View>

    );

};