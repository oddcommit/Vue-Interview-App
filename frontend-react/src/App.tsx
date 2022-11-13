import { useEffect } from "react";
import "./App.css";
import { BrowserRouter as Router } from "react-router-dom";
import Spinner from "react-bootstrap/esm/Spinner";
import "bootstrap/dist/css/bootstrap.min.css";
import RouterComponent from "./Router";
import axiosHttp from "./utils/axios";
import createAuthRefreshInterceptor from 'axios-auth-refresh';
import { AxiosResponse } from "axios";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from ".";
import {
  authenticateUser,
  initRenewTokenRequest,
  logoutUser,
} from "./authentication/authentication-thunk";

export interface TokenRefreshResponse {
  jwtToken: string;
}

function App() {
  const dispatch: any = useDispatch();

  const isApplicationLoading = useSelector(
    (state: RootState) => state.generalReducer.isApplicationLoading
  );

  useEffect(() => {
    const refreshAuthLogic = (failedRequest: any) => {
      return axiosHttp
        .post("/renewToken")
        .then((tokenRefreshResponse: AxiosResponse<TokenRefreshResponse>) => {
          failedRequest.response.config.headers["Authorization"] =
            "Bearer " + tokenRefreshResponse.data.jwtToken;
          dispatch(authenticateUser(tokenRefreshResponse.data.jwtToken));
          return Promise.resolve();
        })
        .catch((error: any) => {
          dispatch(logoutUser());
          return Promise.reject(error);
        });
    };

    createAuthRefreshInterceptor(axiosHttp, refreshAuthLogic);
  }, [dispatch]);

  useEffect(() => {
    dispatch(initRenewTokenRequest());
  }, [dispatch]);

  return (
    <div className="app-body">
      <Router>
        {isApplicationLoading ? (
          <div className="loading-screen">
            <Spinner animation="grow" />
            <div>Loading...</div>
          </div>
        ) : (
          <RouterComponent />
        )}
      </Router>
    </div>
  );
}

export default App;
