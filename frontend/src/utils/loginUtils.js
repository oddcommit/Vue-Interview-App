import axiosHttp from './axios';
import { history } from './history';

const loginUser = (jwtToken) => {
  let axiosHeaders = axiosHttp.defaults.headers;
  delete axiosHttp.defaults.headers['Authorization'];

  axiosHttp.defaults.headers = {
    ...axiosHeaders,
    Authorization: `Bearer ${jwtToken}`,
  };

  localStorage.setItem('jwtToken', jwtToken);

  if (window.location.pathname === '/') {
    history.push('/home');
  }
};

const logoutUser = () => {
  localStorage.removeItem('jwtToken');
  delete axiosHttp.defaults.headers['Authorization'];
  history.push('/');
};

export { loginUser, logoutUser };
