import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import createAuthRefreshInterceptor from 'axios-auth-refresh';
import { loginUser, logoutUser } from './loginUtils';

interface TokenRefreshResponse {
  jwtToken: string;
}

let createAxios = () => {
  let jwtToken: string | null = localStorage.getItem('jwtToken');

  let axiosRequestConfig: AxiosRequestConfig = {
    baseURL: process.env.REACT_APP_API_URL,
    headers: {
      ...(jwtToken && { Authorization: `Bearer ${jwtToken}` }),
      'content-type': 'application/json',
    },
  };
  return axios.create(axiosRequestConfig);
};

let axiosHttp: AxiosInstance = createAxios();

const refreshAuthLogic = (failedRequest: any) => {
  return axiosHttp
    .post('/renewToken')
    .then((tokenRefreshResponse: AxiosResponse<TokenRefreshResponse>) => {
      failedRequest.response.config.headers['Authorization'] =
        'Bearer ' + tokenRefreshResponse.data.jwtToken;
      loginUser(tokenRefreshResponse.data.jwtToken);
      return Promise.resolve();
    })
    .catch((error: any) => {
      logoutUser();
      return Promise.reject(error);
    });
};

createAuthRefreshInterceptor(axiosHttp, refreshAuthLogic);

export default axiosHttp;
