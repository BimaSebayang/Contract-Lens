import {
    RefObject,
} from 'react';

import {
    ScrollView,
} from 'react-native';

import {
    styles,
} from '../conversation.style';

import {
    ConversationDate,
} from './message/conversation-date';

import {
    InitialMessage,
} from './message/initial-message';

import {
    UserMessage,
} from './message/user-message';

import {
    ClaraMessage,
} from './message/clara-message';

import {
    ClaraLoading,
} from './message/clara-loading';

import {LlmMessageConversation} from "@/core/dto/LlmMessageConversation";


type ConversationListProps = {

    conversations: LlmMessageConversation[];

    isClaraLoading: boolean;

    scrollViewRef:
        RefObject<ScrollView | null>;

    keyboardHeight: number;

    onFeedback: (
        index: number,
        feedback: boolean
    ) => void;

    onScrollToBottom: (
        animated?: boolean
    ) => void;

};


export const ConversationList = (
    {
        conversations,

        isClaraLoading,

        scrollViewRef,

        keyboardHeight,

        onFeedback,

        onScrollToBottom,
    }: ConversationListProps
) => {

    return (

        <ScrollView
            ref={
                scrollViewRef
            }
            style={
                styles.scrollView
            }
            contentContainerStyle={[
                styles.scrollContent,
                {
                    paddingBottom:
                        keyboardHeight > 0
                            ? keyboardHeight + 24
                            : 24,
                },
            ]}
            showsVerticalScrollIndicator={
                false
            }
            keyboardShouldPersistTaps={
                'handled'
            }
            keyboardDismissMode={
                'interactive'
            }
            onContentSizeChange={() =>
                onScrollToBottom(
                    false
                )
            }
        >

            {/* ================= DATE ================= */}

            <ConversationDate date={'hari ini'}/>


            {/* ================= INITIAL MESSAGE ================= */}

            <InitialMessage />


            {/* ================= CONVERSATIONS ================= */}

            {
                conversations.map(
                    (
                        conversation,
                        index
                    ) => {

                        if (
                            conversation.role ===
                            'user'
                        ) {

                            return (

                                <UserMessage
                                    key={
                                        `${conversation.timestamp}-${index}`
                                    }
                                    conversation={
                                        conversation
                                    }
                                />

                            );

                        }


                        if (
                            conversation.role ===
                            'assistant'
                        ) {

                            return (

                                <ClaraMessage
                                    key={
                                        `${conversation.timestamp}-${index}`
                                    }
                                    conversation={
                                        conversation
                                    }
                                    index={
                                        index
                                    }
                                    onFeedback={
                                        onFeedback
                                    }
                                />

                            );

                        }


                        return null;

                    }
                )
            }


            {/* ================= CLARA LOADING ================= */}

            {
                isClaraLoading && (
                    <ClaraLoading />
                )
            }

        </ScrollView>

    );

};
