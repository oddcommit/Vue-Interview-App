import axios, { AxiosRequestConfig, AxiosResponse, AxiosInstance } from "axios";
import { JwtTokenReqestOrResponse } from "@/types";
import createAuthRefreshInterceptor from "axios-auth-refresh";
import { AxiosAuthRefreshOptions } from "axios-auth-refresh";
import { loginUser, logoutUser } from "@/utils/authenticationUtils";

const jwtToken: string | null = localStorage.getItem("jwtToken");

const axiosRequestConfig: AxiosRequestConfig = {
  baseURL: process.env.VUE_APP_BASE_URL,
  headers: {
    ...(jwtToken && { Authorization: `Bearer ${jwtToken}` }),
    "content-type": "application/json",
  },
};

const axiosHttp: AxiosInstance = axios.create(axiosRequestConfig);

const refreshAuthLogic = (failedRequest: any) => {
  return axiosHttp
    .post("/renewToken")
    .then((tokenRefreshResponse: AxiosResponse<JwtTokenReqestOrResponse>) => {
      failedRequest.response.config.headers["Authorization"] =
        "Bearer " + tokenRefreshResponse.data.jwtToken;
      loginUser(tokenRefreshResponse.data.jwtToken);
      return Promise.resolve();
    })
    .catch((error: any) => {
      logoutUser();
      return Promise.reject(error);
    });
};

const refreshOptions: AxiosAuthRefreshOptions = {
  statusCodes: [401, 403],
};

createAuthRefreshInterceptor(axiosHttp, refreshAuthLogic, refreshOptions);

export { axiosHttp };
