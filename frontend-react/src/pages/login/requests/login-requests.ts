import { AxiosResponse } from "axios";
import { AxiosAuthRefreshRequestConfig } from "axios-auth-refresh";
import { ThunkDispatch } from "redux-thunk";
import { AnyAction } from "@reduxjs/toolkit";
import { setLoginError } from "../store/login-slice";
import {
  authenticateUser,
  logoutUser,
} from "../../../authentication/authentication-thunk";
import axiosHttp from "../../../utils/axios";
import { LoginResponse, UserLoginData } from "../types";

export const loginUserRequest = (
  emailAndPasswordPostRequest: UserLoginData
) => async (
  dispatch: ThunkDispatch<unknown, unknown, AnyAction>
): Promise<void> => {
  const axiosConfig: AxiosAuthRefreshRequestConfig = { skipAuthRefresh: true };

  axiosHttp
    .post("/login", emailAndPasswordPostRequest, axiosConfig)
    .then((response: AxiosResponse<LoginResponse>) => {
      dispatch(authenticateUser(response.data.jwtToken));
    })
    .catch(() => {
      dispatch(setLoginError(true));
      logoutUser();
    });
};
