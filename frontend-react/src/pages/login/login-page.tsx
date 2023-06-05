import { ChangeEvent, ReactElement, useEffect, useState } from "react";
import { Alert, Form } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../..";
import { setLoginError, setLoginLoading } from "./store/login-slice";
import LoginLoadingButton from "./components/login-loading-button-component";
import { loginUserRequest } from "./requests/login-requests";
import { selectIsLoggedIn } from "./store/login-selectors";
import "./login-page.css";
import { Link } from "react-router-dom";

const LoginView = (): ReactElement => {
  const dispatch: any = useDispatch();

  const showLoginError = useSelector((state: RootState) => state.loginReducer.showLoginError);

  const isUserLoggedIn = useSelector(selectIsLoggedIn);

  const [showEmailEmptyError, setShowEmailEmptyError] = useState(false);
  const [showPasswordEmptyError, setShowPasswordEmptyError] = useState(false);

  const [loginData, setLoginData] = useState({
    email: "",
    password: ""
  });

  const loginUser = (): void => {
    const email = loginData.email;
    const password = loginData.password;
    if (email && password && !showLoginError) {
      dispatch(setLoginLoading(true));
      dispatch(loginUserRequest(loginData));
    } else {
      setShowEmailEmptyError(!email);
      setShowPasswordEmptyError(!password);
    }
  };

  const onChangeLoginData = (event: ChangeEvent<HTMLInputElement>): void => {
    setLoginData({ ...loginData, [event.target.name]: event.target.value });
    if (showLoginError) {
      dispatch(setLoginError(false));
    }
  };

  useEffect(() => {
    setShowEmailEmptyError(false);
    setShowPasswordEmptyError(false);
  }, [loginData]);

  useEffect(
    () => () => {
      dispatch(setLoginError(false));
    },
    [dispatch]
  );

  if (isUserLoggedIn) {
    return <></>;
  }

  return (
    <>
      <div className="login-centered">
        <div className="login-container">
          <div className="login-heading">
            <span>Sign in to your account</span>
          </div>
          <Form.Group controlId="formBasicEmail">
            <Form.Label>Email address:</Form.Label>
            <Form.Control
              className="login-empty-email"
              isInvalid={showEmailEmptyError}
              name="email"
              type="email"
              placeholder="Enter email"
              onChange={onChangeLoginData}
            />

            {showEmailEmptyError && <div className="login-text-error">E-Mail can not be empty</div>}
          </Form.Group>

          <Form.Group controlId="formBasicPassword" className="mt-2">
            <Form.Label>Password:</Form.Label>
            <Form.Control
              className="login-empty-email"
              isInvalid={showPasswordEmptyError}
              name="password"
              type="password"
              placeholder="Password"
              onChange={onChangeLoginData}
            />

            {showPasswordEmptyError && <div className="login-text-error">Password can not be empty</div>}
          </Form.Group>

          {showLoginError && (
            <Alert variant={"danger"} className="mt-4 mb-3">
              Sorry, wrong e-mail or wrong password!
            </Alert>
          )}

          <LoginLoadingButton onClickLoginUser={loginUser} />

          <div className="login-register-link">
            No account yet? <Link to="/register">Click to register here</Link>
          </div>
        </div>
      </div>
    </>
  );
};

export default LoginView;
