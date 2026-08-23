import { Stack } from 'expo-router';
import { Text, TextInput } from 'react-native';

import {
    DMSans_400Regular,
    DMSans_500Medium,
    DMSans_600SemiBold,
    DMSans_700Bold,
    useFonts,
} from '@expo-google-fonts/dm-sans';


const AppText = Text as typeof Text & {
    defaultProps?: {
        style?: unknown;
    };
};

AppText.defaultProps = AppText.defaultProps ?? {};

AppText.defaultProps.style = {
    fontFamily: 'DMSans_400Regular',
};


const AppTextInput = TextInput as typeof TextInput & {
    defaultProps?: {
        style?: unknown;
    };
};

AppTextInput.defaultProps =
    AppTextInput.defaultProps ?? {};

AppTextInput.defaultProps.style = {
    fontFamily: 'DMSans_400Regular',
};


export default function RootLayout() {

    const [fontsLoaded] = useFonts({
        DMSans_400Regular,
        DMSans_500Medium,
        DMSans_600SemiBold,
        DMSans_700Bold,
    });

    if (!fontsLoaded) {
        return null;
    }

    return (
        <Stack
            screenOptions={{
                headerShown: false,
            }}
        />
    );
}