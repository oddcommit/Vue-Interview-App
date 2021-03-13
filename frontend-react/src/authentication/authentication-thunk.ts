import { AnyAction } from "@reduxjs/toolkit";
import { ThunkDispatch } from "redux-thunk";
import { setIsApplicationLoading } from "../reducer/general-reducer/general-slice";
import { setLoggedIn } from "../pages/login/store/login-slice";
import axiosHttp from "../utils/axios";

export const initRenewTokenRequest = () => async (
  dispatch: ThunkDispatch<unknown, unknown, AnyAction>
): Promise<void> => {
  let jwtToken = localStorage.getItem("jwtToken");
  if (jwtToken) {
    axiosHttp
      .post("/renewToken", { skipAuthRefresh: true })
      .then((tokenRefreshResponse) => {
        dispatch(authenticateUser(tokenRefreshResponse.data.jwtToken));
        dispatch(setIsApplicationLoading(false));
      })
      .catch(() => {
        dispatch(userLogout());
        dispatch(setIsApplicationLoading(false));
      });
  } else {
    dispatch(setIsApplicationLoading(false));
  }
};

export const logoutUser = () => (
  dispatch: ThunkDispatch<unknown, unknown, AnyAction>
) => {
  localStorage.removeItem("jwtToken");
  delete axiosHttp.defaults.headers["Authorization"];
  dispatch(setLoggedIn(false));
};

export const authenticateUser = (jwtToken: string) => async (
  dispatch: ThunkDispatch<unknown, unknown, AnyAction>
): Promise<void> => {
  let axiosHeaders: any = axiosHttp.defaults.headers;

  axiosHttp.defaults.headers = {
    ...axiosHeaders,
    Authorization: `Bearer ${jwtToken}`,
  };

  localStorage.setItem("jwtToken", jwtToken);
  dispatch(setLoggedIn(true));
};
function userLogout(): any {
  throw new Error("Function not implemented.");
}
