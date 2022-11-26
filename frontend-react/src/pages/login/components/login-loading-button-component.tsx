import React, { useEffect } from "react";
import { Button, Spinner } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../..";
import { setLoginLoading } from "../store/login-slice";
import { selectShowLoginError } from "../store/login-selectors";
import "./login-loading-button-component.css";

interface LoginLoadingButtonProps {
  onClickLoginUser: () => void;
}

const LoginLoadingButton: React.FC<LoginLoadingButtonProps> = ({
  onClickLoginUser,
}: LoginLoadingButtonProps) => {
  const dispatch: any = useDispatch();
  const isLoginLoading = useSelector(
    (state: RootState) => state.loginReducer.isLoginLoading
  );

  const showLoginError = useSelector(selectShowLoginError);

  useEffect(() => {
    dispatch(setLoginLoading(false));
  }, [dispatch]);

  return (
    <Button
      variant="primary login-button mt-3"
      type="submit"
      onClick={onClickLoginUser}
    >
      {isLoginLoading && !showLoginError ? (
        <div className="login-loading-spinner">
          <span className="mr-2">Loading...</span>
          <Spinner animation="grow" variant="light" size="sm" />
        </div>
      ) : (
        <div>Login</div>
      )}
    </Button>
  );
};

export default LoginLoadingButton;
