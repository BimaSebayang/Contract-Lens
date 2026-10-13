import {
  BrowserRouter,
  Routes,
  Route,
  Link
} from "react-router-dom";
import ChatPage from "./module/chat/ChatPage";
import HomePage from "./module/homepage/HomePage";


// function HomePage() {
//   return (
//       <div>
//         <h1>CLAwfo</h1>
//
//           <Link
//               to={`/chat/${crypto.randomUUID()}?laundryCode=3dc71afcfaa130d3b463376f2298acf846c53bb0b3e550c1043bf31eedd2e233`}
//           >
//               Chat
//           </Link>
//       </div>
//   );
// }

export default function App() {
  return (
      <BrowserRouter>
        <Routes>
            <Route
                path="/"
                element={<HomePage />}
            />

            <Route
                path="/chat/:chatId"
                element={<ChatPage />}
            />
        </Routes>
      </BrowserRouter>
  );
}