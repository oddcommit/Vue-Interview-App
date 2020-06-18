import axios from 'axios';
import createAuthRefreshInterceptor from 'axios-auth-refresh';


let jwtToken = localStorage.getItem('jwtToken');


const axiosHttp = axios.create({
    baseURL: "http://localhost:4667/api",
    headers: {
        ...(jwtToken && { "Authorization": `Bearer ${jwtToken}` }),
        "content-type": "application/json"
    },
});
const refreshAuthLogic = failedRequest => {
    return axiosHttp.post('/renewToken')
        .then(tokenRefreshResponse => {
            let jwtToken = tokenRefreshResponse.data.jwtToken;
            localStorage.setItem('jwtToken', jwtToken);
            failedRequest.response.config.headers['Authorization'] = `Bearer ${jwtToken}`;
            return Promise.resolve();
        }).catch(error => {
            localStorage.removeItem('jwtToken');
            delete axiosHttp.defaults.headers.common["Authorization"];
            return Promise.reject(error);
        });
}
createAuthRefreshInterceptor(axiosHttp, refreshAuthLogic);

export default axiosHttp;