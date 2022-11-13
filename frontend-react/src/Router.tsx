import { ReactElement, useEffect } from "react";
import { useSelector } from "react-redux";
import { Route, Routes, useNavigate, useLocation } from "react-router-dom";
import { selectIsLoggedIn } from "./pages/login/store/login-selectors";
import UsersView from "./pages/users/users-view";
import LoginView from "./pages/login/login-page";

const RouterComponent = (): ReactElement => {
  const location = useLocation();
  const history = useNavigate();

  const isLoggedIn = useSelector(selectIsLoggedIn);

  useEffect(() => {
    if (isLoggedIn && location.pathname === "/") {
      history('/users');
    }

    if (!isLoggedIn && location.pathname !== "/") {
      history('/');
    }
  }, [location, isLoggedIn, history]);

  return (
    <Routes>
      <Route path="/" element={<LoginView />} />
      <Route path="/users" element={<UsersView />} />
    </Routes>
  );
};

export default RouterComponent;
