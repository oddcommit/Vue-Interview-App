import { ChangeEvent, ReactElement, useEffect, useState } from "react";
import { Alert, Form } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../..";
import { setLoginError, setLoginLoading } from "./store/login-slice";
import LoginLoadingButton from "./components/login-loading-button-component";
import { loginUserRequest } from "./requests/login-requests";
import { selectIsLoggedIn } from "./store/login-selectors";
import "./login-page.css";

const LoginView = (): ReactElement => {
  const dispatch: any = useDispatch();

  const showLoginError = useSelector(
    (state: RootState) => state.loginReducer.showLoginError
  );

  const isUserLoggedIn = useSelector(selectIsLoggedIn);

  const [showEmailEmptyError, setShowEmailEmptyError] = useState(false);
  const [showPasswordEmptyError, setShowPasswordEmptyError] = useState(false);

  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
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

  if (isUserLoggedIn) {
    return <></>;
  }

  return (
    <div className="login-centered">
      <div className="login-container">
        <Form.Group controlId="formBasicEmail">
          <Form.Label>Email address:</Form.Label>
          <Form.Control
            bsPrefix={`form-control ${
              showEmailEmptyError ? "login-form-error" : ""
            }`}
            name="email"
            type="email"
            placeholder="Enter email"
            onChange={onChangeLoginData}
          />

          {showEmailEmptyError && (
            <div className="login-text-error">E-Mail can not be empty</div>
          )}
        </Form.Group>

        <Form.Group controlId="formBasicPassword">
          <Form.Label>Password:</Form.Label>
          <Form.Control
            bsPrefix={`form-control ${
              showPasswordEmptyError ? "login-form-error" : ""
            }`}
            name="password"
            type="password"
            placeholder="Password"
            onChange={onChangeLoginData}
          />

          {showPasswordEmptyError && (
            <div className="login-text-error">Password can not be empty</div>
          )}
        </Form.Group>

        {showLoginError && (
          <Alert variant={"danger"}>
            Sorry, wrong e-mail or wrong password!
          </Alert>
        )}

        <LoginLoadingButton onClickLoginUser={loginUser} />
      </div>
    </div>
  );
};

export default LoginView;
