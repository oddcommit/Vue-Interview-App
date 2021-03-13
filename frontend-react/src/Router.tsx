import { ReactElement, useEffect } from "react";
import { useSelector } from "react-redux";
import { Route, Switch, useHistory, useLocation } from "react-router-dom";
import { selectIsLoggedIn } from "./pages/login/store/login-selectors";
import UsersView from "./pages/users/users-view";
import LoginView from "./pages/login/login-page";

const RouterComponent = (): ReactElement => {
  const location = useLocation();
  const history = useHistory();

  const isLoggedIn = useSelector(selectIsLoggedIn);

  useEffect(() => {
    if (isLoggedIn && location.pathname === "/") {
      history.push("/users");
    }

    if (!isLoggedIn && location.pathname !== "/") {
      history.push("/");
    }
  }, [location, isLoggedIn, history]);

  return (
    <Switch>
      <Route exact path="/" component={LoginView} />
      <Route exact path="/users" component={UsersView} />
    </Switch>
  );
};

export default RouterComponent;
