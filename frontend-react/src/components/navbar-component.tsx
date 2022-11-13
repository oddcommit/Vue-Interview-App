import { Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { logoutUser } from "../authentication/authentication-thunk";
import { selectIsLoggedIn } from "../pages/login/store/login-selectors";
import "./navbar-component.css";

const NavbarComponent = () => {
  const dispatch: any = useDispatch();
  const isUserLoggedIn = useSelector(selectIsLoggedIn);

  if (!isUserLoggedIn) {
    return <></>;
  }

  return (
    <nav>
      <span>This is just an example navbar</span>
      <Button
        variant="primary navbar-button"
        type="submit"
        onClick={() => dispatch(logoutUser())}
      >
        Logout
      </Button>
    </nav>
  );
};

export default NavbarComponent;
