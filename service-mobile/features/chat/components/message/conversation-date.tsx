import {
    Text,
    View,
} from 'react-native';

import {
    styles,
} from '../../conversation.style';

type ConversationDateProps = { date: string; };

export const ConversationDate = ( { date, }: ConversationDateProps ) => {

    return (

        <View
            style={
                styles.dateContainer
            }
        >

            <View
                style={
                    styles.dateLine
                }
            />


            <Text
                style={
                    styles.dateText
                }
            >
                {
                    date
                }
            </Text>


            <View
                style={
                    styles.dateLine
                }
            />

        </View>

    );

};