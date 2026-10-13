import {
    BrowserRouter,
    Routes,
    Route,
    Navigate,
} from 'react-router-dom';

import type { ReactNode } from 'react';

import ChatPage from './module/chat/ChatPage';
import HomePage from './module/homepage/HomePage';
import MobileOnlyNotice from "./component/notif/MobileOnlyNotice";

type MobileOnlyGuardProps = {
    children: ReactNode;
};

export const isMobileScreen = (): boolean => {
    return window.matchMedia('(max-width: 991px)').matches;
};

function MobileOnlyGuard({
                             children,
                         }: MobileOnlyGuardProps) {

    if (!isMobileScreen()) {
        return <MobileOnlyNotice></MobileOnlyNotice>;
    }

    return <>{children}</>;
}

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route
                    path="/"
                    element={
                        <MobileOnlyGuard>
                            <HomePage />
                        </MobileOnlyGuard>
                    }
                />

                <Route
                    path="/chat/:chatId"
                    element={
                        <MobileOnlyGuard>
                            <ChatPage />
                        </MobileOnlyGuard>
                    }
                />
            </Routes>
        </BrowserRouter>
    );
}