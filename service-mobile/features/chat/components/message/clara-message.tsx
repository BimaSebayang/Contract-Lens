import {LlmMessageConversation} from "@/core/dto/LlmMessageConversation";
import {GreetingUserIntent} from "@/features/chat/components/message/intents/greeting_user_intents";
import {UnknownIntent} from "@/features/chat/components/message/intents/unknown_intents";
import {InteractionIntentProps} from "@/core/dto/InteractionIntentProps";
import {GlossaryIntent} from "@/features/chat/components/message/intents/glossary_intent";

export const ClaraMessage = (
    {
        conversation,
        index,
        onFeedback,
        handleAction
    }: InteractionIntentProps
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
                                    handleAction={handleAction}
                                />
                            );

                            case "GLOSSARY_CONTRACTLENS":
                            return (
                                <GlossaryIntent
                                    conversation={conversation}
                                    index={index}
                                    onFeedback={onFeedback}
                                    handleAction={handleAction}
                                />
                            );

                        case "UNKNOWN":
                            return (
                                <UnknownIntent
                                    conversation={conversation}
                                    index={index}
                                    onFeedback={onFeedback}
                                    handleAction={handleAction}
                                />
                            );
                    }

                })()
            }
        </>

    );

};
