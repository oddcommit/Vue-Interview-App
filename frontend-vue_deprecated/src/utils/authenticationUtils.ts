import store from "@/store";
import router from "@/router";
import { axiosHttp } from "@/axios";

const loginUser = (jwtToken: string | undefined) => {
  if (!jwtToken) {
    return;
  }
  store.dispatch("IS_LOGGED_IN", true);
  localStorage.setItem("jwtToken", jwtToken);
  axiosHttp.defaults.headers["Authorization"] = `Bearer ${jwtToken}`;
  if (router.currentRoute.path === "/") {
    router.push({ path: "/user" });
  }
};

const logoutUser = () => {
  store.dispatch("IS_LOGGED_IN", false);
  router.push({ path: "/" });
  delete axiosHttp.defaults.headers.common["Authorization"];
  const jwtToken: string | null = localStorage.getItem("jwtToken");
  if (jwtToken) {
    localStorage.removeItem("jwtToken");
  }
};

export { loginUser, logoutUser };
