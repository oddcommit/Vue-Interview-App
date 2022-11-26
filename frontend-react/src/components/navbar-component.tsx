import { Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { logoutUser } from "../authentication/authentication-thunk";
import { selectIsLoggedIn } from "../pages/login/store/login-selectors";
import "./navbar-component.css";

const NavbarComponent = () => {
  const dispatch: any = useDispatch();
  const isUserLoggedIn = useSelector(selectIsLoggedIn);

  return (
    <nav>
      <ul className="nav-heading">
        <li>
          <h2 className="heading">Interview example project</h2>
        </li>
        <li>
          <div className="nav-center-button">
            {isUserLoggedIn ? (
              <Button
                variant="primary"
                type="submit"
                onClick={() => dispatch(logoutUser())}
              >
                Logout
              </Button>
            ) : (
              <Link to="/register">
                <Button variant="primary">Register</Button>
              </Link>
            )}
          </div>
        </li>
      </ul>
    </nav>
  );
};

export default NavbarComponent;
