import { ReactElement, useEffect } from "react";
import { useSelector } from "react-redux";
import { Route, Routes, useNavigate, useLocation } from "react-router-dom";
import { selectIsLoggedIn } from "./pages/login/store/login-selectors";
import UsersView from "./pages/users/users-view";
import LoginView from "./pages/login/login-page";
import RegisterView from "./pages/register/register-view";
import NavbarComponent from "./components/navbar-component";

const RouterComponent = (): ReactElement => {
  const location = useLocation();
  const navigation = useNavigate();

  const isLoggedIn = useSelector(selectIsLoggedIn);

  useEffect(() => {
    if (isLoggedIn && location.pathname === "/") {
      navigation("/users");
    }

    if (
      !isLoggedIn &&
      location.pathname !== "/" &&
      location.pathname !== "/register"
    ) {
      navigation("/");
    }
  }, [location, isLoggedIn, navigation]);

  return (
    <>
      <NavbarComponent />
      <div className="container-placeholder"></div>
      <Routes>
        <Route path="/users" element={<UsersView />} />
        <Route path="/register" element={<RegisterView />} />
        <Route path="/" element={<LoginView />} />
      </Routes>
    </>
  );
};

export default RouterComponent;
