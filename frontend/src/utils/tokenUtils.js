import axiosHttp from './axios';

const setToken = (jwtToken) => {
  let axiosHeaders = axiosHttp.defaults.headers;
  axiosHttp.defaults.headers = {
    ...axiosHeaders,
    Authorization: `Bearer ${jwtToken}`,
  };
  localStorage.setItem('jwtToken', jwtToken);
};

const delteToken = () => {
  localStorage.removeItem('jwtToken');
  delete axiosHttp.defaults.headers['Authorization'];
};

export { setToken, delteToken };
