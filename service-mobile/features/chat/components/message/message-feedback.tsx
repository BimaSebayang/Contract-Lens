import {
    Pressable,
    Text,
    View,
} from 'react-native';

import {
    ThumbsDown,
    ThumbsUp,
} from 'lucide-react-native';

import {
    styles,
} from '../../conversation.style';


type MessageFeedbackProps = {

    feedback?: boolean|null;

    onFeedback: (
        value: boolean
    ) => void;

};


export const MessageFeedback = (
    {
        feedback,

        onFeedback,
    }: MessageFeedbackProps
) => {

    return (

        <>

            {/* ================= DIVIDER ================= */}

            <View
                style={
                    styles.divider
                }
            />


            {/* ================= FEEDBACK ================= */}

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
                    Jawaban memuaskan?
                </Text>


                {/* ================= LIKE ================= */}

                <Pressable
                    style={
                        styles.feedbackButton
                    }
                    onPress={() =>
                        onFeedback(
                            true
                        )
                    }
                >

                    <ThumbsUp
                        size={20}
                        color={
                            feedback === true
                                ? '#4F46E5'
                                : '#64748B'
                        }
                        fill={
                            feedback === true
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
                        onFeedback(
                            false
                        )
                    }
                >

                    <ThumbsDown
                        size={20}
                        color={
                            feedback === false
                                ? '#4F46E5'
                                : '#64748B'
                        }
                        fill={
                            feedback === false
                                ? '#E0E7FF'
                                : 'transparent'
                        }
                    />

                </Pressable>

            </View>

        </>

    );

};
