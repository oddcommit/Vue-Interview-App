import axios, { AxiosInstance, AxiosRequestConfig } from "axios";

let createAxios = () => {
  let jwtToken: string | null = localStorage.getItem("jwtToken");

  let axiosRequestConfig: AxiosRequestConfig = {
    baseURL: process.env.REACT_APP_API_URL,
    headers: {
      ...(jwtToken && { Authorization: `Bearer ${jwtToken}` }),
      "content-type": "application/json",
    },
  };
  return axios.create(axiosRequestConfig);
};

let axiosHttp: AxiosInstance = createAxios();

export default axiosHttp;
