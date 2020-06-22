import axios from 'axios';
import createAuthRefreshInterceptor from 'axios-auth-refresh';
import { setToken, delteToken } from './tokenUtils';

let createAxios = () => {
  let jwtToken = localStorage.getItem('jwtToken');
  return axios.create({
    baseURL: 'http://localhost:4667/api', //TODO: Replace this with environment variable
    headers: {
      ...(jwtToken && { Authorization: `Bearer ${jwtToken}` }),
      'content-type': 'application/json',
    },
  });
};

let axiosHttp = createAxios();

const refreshAuthLogic = (failedRequest) => {
  return axiosHttp
    .post('/renewToken')
    .then((tokenRefreshResponse) => {
      setToken(tokenRefreshResponse.data.jwtToken);
      return Promise.resolve();
    })
    .catch((error) => {
      delteToken();
      return Promise.reject(error);
    });
};

createAuthRefreshInterceptor(axiosHttp, refreshAuthLogic);

export default axiosHttp;
