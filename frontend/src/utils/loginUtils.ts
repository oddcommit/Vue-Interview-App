import axiosHttp from './axios';
import { history } from './history';

const loginUser = (jwtToken: string) => {
  let axiosHeaders: any = axiosHttp.defaults.headers;

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
