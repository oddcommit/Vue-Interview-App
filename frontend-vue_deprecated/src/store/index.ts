import Vue from "vue";
import Vuex, { ActionTree, MutationTree, ActionContext } from "vuex";
import { RootState } from "./types";
import { User } from "@/views/types";
import { AxiosBackendError } from "@/types";
import { AxiosError, AxiosResponse } from "axios";
import isEmpty from "lodash/isEmpty";

Vue.use(Vuex);

const state: RootState = {
  loginError: "",
  isLoggedIn: false,
  users: [],
};

const mutations: MutationTree<RootState> = {
  setError(state: RootState, loginError: string) {
    state.loginError = loginError;
  },
  setLoggedIn(state: RootState, isLoggedIn: boolean) {
    state.isLoggedIn = isLoggedIn;
  },
  setUsers(state: RootState, users: Array<User>) {
    state.users = users;
  },
  handleError(state: RootState, error: AxiosError<AxiosBackendError>) {
    this._vm.$notify({
      group: "generalErrorMessage",
      title: "An error occured",
      text: error.message,
      type: "error",
    });
  },
};

const actions: ActionTree<RootState, any> = {
  SET_ERROR(context: ActionContext<RootState, RootState>, loginError: string) {
    context.commit("setError", loginError);
  },
  IS_LOGGED_IN(
    context: ActionContext<RootState, RootState>,
    isLoggedIn: boolean
  ) {
    context.commit("setLoggedIn", isLoggedIn);
  },
  SET_USERS(context: ActionContext<RootState, RootState>, users: Array<User>) {
    context.commit("setUsers", users);
  },
  FETCH_USERS(context: ActionContext<RootState, RootState>) {
    this._vm.$http
      .get("/users")
      .then((response: AxiosResponse<Array<User>>) => {
        if (!isEmpty(response)) {
          context.commit("setUsers", response.data);
        }
      })
      .catch((error: AxiosError<AxiosBackendError>) => {
        context.commit("handleError", error);
      });
  },
};

export default new Vuex.Store({
  strict: true,
  state,
  mutations,
  actions,
  modules: {},
});
