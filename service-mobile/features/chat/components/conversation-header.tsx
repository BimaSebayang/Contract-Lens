import {
    Image,
    Pressable,
    Text,
    View,
} from 'react-native';

import {
    router,
} from 'expo-router';

import {
    ArrowLeft,
    Bot,
} from 'lucide-react-native';

import {
    styles,
} from '../conversation.style';


export const ConversationHeader = () => {

    return (

        <View
            style={
                styles.header
            }
        >

            <View
                style={
                    styles.headerContent
                }
            >

                {/* ================= BACK ================= */}

                <Pressable
                    style={
                        styles.backButton
                    }
                    onPress={() =>
                        router.push(
                            '/chat/page'
                        )
                    }
                >

                    <ArrowLeft
                        size={16}
                        color="#4F46E5"
                        strokeWidth={3.5}
                    />

                </Pressable>


                {/* ================= AVATAR ================= */}

                <Image
                    source={require(
                        '@/assets/images/clara-ai/clara-avatar.png'
                    )}
                    style={
                        styles.headerAvatar
                    }
                />


                {/* ================= PROFILE ================= */}

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
                        ContractLens AI Robot Assistant
                    </Text>


                    {/* ================= STATUS ================= */}

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

    );

};