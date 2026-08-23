import {
  StyleSheet,
  Text,
  TouchableOpacity,
  View
} from 'react-native';

import {
  router
} from 'expo-router';
import ConversationScreen from "@/features/chat/conversation.screen";


export default function HomeScreen() {

  const handleStartChat = () => {

    router.push(
        '/chat/page'
    );
  };


  return (
            <ConversationScreen />
        );
}