import {
    View,
} from 'react-native';

import {
    styles,
} from './conversation.style';

import {
    useConversationScript,
} from './conversation.script';

import {
    ConversationHeader,
} from './components/conversation-header';

import {
    ConversationList,
} from './components/conversation-list';

import {
    ConversationInput,
} from './components/conversation-input';


export default function ConversationScreen() {

    const {
        conversations,
        message,
        keyboardHeight,

        scrollViewRef,
        keyboardOffset,

        handleChatClara,
        handleFeedback,
        handleMessageChange,

        scrollToBottom,

        isClaraLoading,
    } = useConversationScript();


    return (

        <View
            style={
                styles.container
            }
        >

            {/* ================= HEADER ================= */}

            <ConversationHeader />


            {/* ================= CONVERSATION ================= */}

            <ConversationList
                conversations={
                    conversations
                }
                isClaraLoading={
                    isClaraLoading
                }
                scrollViewRef={
                    scrollViewRef
                }
                keyboardHeight={
                    keyboardHeight
                }
                onFeedback={
                    handleFeedback
                }
                onScrollToBottom={
                    scrollToBottom
                }
            />


            {/* ================= INPUT ================= */}

            <ConversationInput
                value={
                    message.message
                }
                keyboardOffset={
                    keyboardOffset
                }
                onChangeText={
                    handleMessageChange
                }
                onSend={
                    handleChatClara
                }
                onFocus={
                    scrollToBottom
                }
            />

        </View>

    );

}