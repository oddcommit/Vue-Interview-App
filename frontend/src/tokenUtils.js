import axiosHttp from './Axios';

const setToken = (jwtToken) => {
  axiosHttp.defaults.headers.common = {
    Authorization: `Bearer ${jwtToken}`,
  };
  localStorage.setItem('jwtToken', jwtToken);
};

const delteToken = () => {
  localStorage.removeItem('jwtToken');
  delete axiosHttp.defaults.headers.common['Authorization'];
};

export { setToken, delteToken };
