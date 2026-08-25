import {
    Animated,
    Pressable,
    TextInput,
    View,
} from 'react-native';

import {
    Paperclip,
    Send,
} from 'lucide-react-native';

import {
    styles,
} from '../conversation.style';


type ConversationInputProps = {

    value: string;

    keyboardOffset: Animated.Value;

    onChangeText: (
        value: string
    ) => void;

    onSend: () => void;

    onFocus: () => void;

};


export const ConversationInput = (
    {
        value,

        keyboardOffset,

        onChangeText,

        onSend,

        onFocus,
    }: ConversationInputProps
) => {

    const isDisabled =
        !value.trim();


    return (

        <Animated.View
            style={[
                styles.inputWrapper,
                {
                    transform: [
                        {
                            translateY: keyboardOffset,
                        },
                    ],
                },
            ]}
        >

            <View
                style={
                    styles.inputContainer
                }
            >

                {/* ================= ATTACH ================= */}

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


                {/* ================= INPUT ================= */}

                <TextInput
                    value={
                        value
                    }
                    onChangeText={
                        onChangeText
                    }
                    placeholder={
                        'Chat Di sini...'
                    }
                    placeholderTextColor={
                        '#94A3B8'
                    }
                    style={
                        styles.input
                    }
                    multiline
                    maxLength={
                        1000
                    }
                    onFocus={
                        onFocus
                    }
                />


                {/* ================= SEND ================= */}

                <Pressable
                    style={[
                        styles.sendButton,

                        isDisabled &&
                        styles.sendButtonDisabled,
                    ]}
                    onPress={
                        onSend
                    }
                    disabled={
                        isDisabled
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

    );

};