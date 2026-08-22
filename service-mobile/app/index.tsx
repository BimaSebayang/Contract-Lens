import {
  StyleSheet,
  Text,
  TouchableOpacity,
  View
} from 'react-native';

import {
  router
} from 'expo-router';


export default function HomeScreen() {

  const handleStartChat = () => {

    router.push(
        '/chat/conversation'
    );
  };


  return (
      <View
          style={styles.container}
      >

        <View
            style={styles.content}
        >

          <View
              style={styles.logoContainer}
          >
            <Text
                style={styles.logoText}
            >
              C
            </Text>
          </View>

          <Text
              style={styles.title}
          >
            ContractLens
          </Text>

          <Text
              style={styles.subtitle}
          >
            Understand your contracts.
          </Text>

          <Text
              style={styles.description}
          >
            CLAra helps you understand,
            analyze, and explore your contracts
            through natural conversation.
          </Text>

        </View>

        <TouchableOpacity
            style={styles.startButton}
            onPress={handleStartChat}
        >

          <Text
              style={styles.startButtonText}
          >
            Start Conversation
          </Text>

        </TouchableOpacity>

      </View>
  );
}


const styles = StyleSheet.create({

  container: {
    flex: 1,
    backgroundColor: '#FFFFFF',
    paddingHorizontal: 24,
    paddingVertical: 48,
    justifyContent: 'space-between'
  },

  content: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center'
  },

  logoContainer: {
    width: 96,
    height: 96,
    borderRadius: 28,
    backgroundColor: '#0F172A',
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 24
  },

  logoText: {
    color: '#22D3EE',
    fontSize: 48,
    fontWeight: 'bold'
  },

  title: {
    fontSize: 32,
    fontWeight: '700',
    color: '#0F172A',
    marginBottom: 8
  },

  subtitle: {
    fontSize: 18,
    color: '#0891B2',
    marginBottom: 24
  },

  description: {
    fontSize: 16,
    color: '#64748B',
    textAlign: 'center',
    lineHeight: 24,
    paddingHorizontal: 16
  },

  startButton: {
    height: 56,
    backgroundColor: '#0F172A',
    borderRadius: 16,
    justifyContent: 'center',
    alignItems: 'center'
  },

  startButtonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: '600'
  }

});