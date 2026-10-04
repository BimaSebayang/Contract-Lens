import {
  BrowserRouter,
  Routes,
  Route,
  Link
} from "react-router-dom";
import ChatPage from "./module/chat/ChatPage";


function HomePage() {
  return (
      <div>
        <h1>CLAwfo</h1>

        <Link to="/chat">
          Chat
        </Link>
      </div>
  );
}

export default function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route
              path="/"
              element={<HomePage />}
          />

          <Route
              path="/chat"
              element={<ChatPage />}
          />
        </Routes>
      </BrowserRouter>
  );
}