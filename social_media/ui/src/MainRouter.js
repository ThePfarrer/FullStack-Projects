import { Route, Routes } from "react-router-dom";
import Home from "./core/Home";
import SignUp from "./user/Signup";
import Users from "./user/Users";

const MainRouter = () => {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/users" element={<Users />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes>
    </div>
  );
};

export default MainRouter;
