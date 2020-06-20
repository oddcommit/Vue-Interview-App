import axios from 'axios';
import createAuthRefreshInterceptor from 'axios-auth-refresh';
import { setToken, delteToken } from './tokenUtils';

let jwtToken = localStorage.getItem('jwtToken');

const axiosHttp = axios.create({
  baseURL: 'http://localhost:4667/api',
  headers: {
    ...(jwtToken && { Authorization: `Bearer ${jwtToken}` }),
    'content-type': 'application/json',
  },
});

const refreshAuthLogic = (failedRequest) => {
  return axiosHttp
    .post('/renewToken')
    .then((tokenRefreshResponse) => {
      setToken(tokenRefreshResponse.data.jwtToken);
      failedRequest.response.config.headers[
        'Authorization'
      ] = `Bearer ${jwtToken}`;
      return Promise.resolve();
    })
    .catch((error) => {
      delteToken();
      return Promise.reject(error);
    });
};
createAuthRefreshInterceptor(axiosHttp, refreshAuthLogic);

export default axiosHttp;
