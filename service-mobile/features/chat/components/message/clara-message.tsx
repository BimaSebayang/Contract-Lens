import {LlmMessageConversation} from "@/core/dto/LlmMessageConversation";
import {GreetingUserIntent} from "@/features/chat/components/message/intents/greeting_user_intents";
import {UnknownIntent} from "@/features/chat/components/message/intents/unknown_intents";


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


    return (

        <>
            {
                (() => {

                    switch (conversation.intent)
                    {
                        case "GREETING_USER":
                            return (
                                <GreetingUserIntent
                                    conversation={conversation}
                                    index={index}
                                    onFeedback={onFeedback}
                                />
                            );

                        case "UNKNOWN":
                            return (
                                <UnknownIntent
                                    conversation={conversation}
                                    index={index}
                                    onFeedback={onFeedback}
                                />
                            );
                    }

                })()
            }
        </>

    );

};
