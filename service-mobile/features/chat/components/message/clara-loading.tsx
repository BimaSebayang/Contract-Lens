import {
    useEffect,
    useRef,
} from 'react';

import {
    Animated,
    Image,
    Text,
    View,
} from 'react-native';

import {
    styles,
} from '../../conversation.style';


export const ClaraLoading = () => {

    const dotOne =
        useRef(
            new Animated.Value(0)
        ).current;

    const dotTwo =
        useRef(
            new Animated.Value(0)
        ).current;

    const dotThree =
        useRef(
            new Animated.Value(0)
        ).current;


    useEffect(
        () => {

            const createAnimation = (
                animation: Animated.Value,
                delay: number
            ) => {

                return Animated.loop(
                    Animated.sequence([

                        Animated.delay(
                            delay
                        ),

                        Animated.timing(
                            animation,
                            {
                                toValue: -6,

                                duration: 250,

                                useNativeDriver:
                                    true,
                            }
                        ),

                        Animated.timing(
                            animation,
                            {
                                toValue: 0,

                                duration: 250,

                                useNativeDriver:
                                    true,
                            }
                        ),

                    ])
                );

            };


            const animationOne =
                createAnimation(
                    dotOne,
                    0
                );

            const animationTwo =
                createAnimation(
                    dotTwo,
                    150
                );

            const animationThree =
                createAnimation(
                    dotThree,
                    300
                );


            animationOne.start();

            animationTwo.start();

            animationThree.start();


            return () => {

                animationOne.stop();

                animationTwo.stop();

                animationThree.stop();

            };

        },
        [
            dotOne,
            dotTwo,
            dotThree,
        ]
    );


    return (

        <View
            style={
                styles.aiMessageRow
            }
        >

            {/* ================= CLARA IMAGE ================= */}

            <Image
                source={require(
                    '@/assets/images/clara-ai/clara_typing.png'
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

                    <View
                        style={{
                            flexDirection:
                                'row',

                            alignItems:
                                'center',

                            gap:
                                4,
                        }}
                    >

                        <Text
                            style={
                                styles.claraThinkingText
                            }
                        >
                            CLAra sedang berpikir
                        </Text>


                        <Animated.View
                            style={[
                                {
                                    width: 6,
                                    height: 6,

                                    borderRadius:
                                        999,

                                    backgroundColor:
                                        '#6366F1',
                                },
                                {
                                    transform: [
                                        {
                                            translateY:
                                                dotOne,
                                        },
                                    ],
                                },
                            ]}
                        />


                        <Animated.View
                            style={[
                                {
                                    width: 6,
                                    height: 6,

                                    borderRadius:
                                        999,

                                    backgroundColor:
                                        '#6366F1',
                                },
                                {
                                    transform: [
                                        {
                                            translateY:
                                                dotTwo,
                                        },
                                    ],
                                },
                            ]}
                        />


                        <Animated.View
                            style={[
                                {
                                    width: 6,
                                    height: 6,

                                    borderRadius:
                                        999,

                                    backgroundColor:
                                        '#6366F1',
                                },
                                {
                                    transform: [
                                        {
                                            translateY:
                                                dotThree,
                                        },
                                    ],
                                },
                            ]}
                        />

                    </View>

                </View>

            </View>

        </View>

    );

};
