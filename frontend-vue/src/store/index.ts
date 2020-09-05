import Vue from "vue";
import Vuex, { ActionTree, GetterTree, MutationTree } from "vuex";
import { RootState } from "./types";

Vue.use(Vuex);

const state: RootState = {
  loginError: "",
};

const mutations: MutationTree<RootState> = {
  setError(state, loginError) {
    state.loginError = loginError;
  },
};

const actions: ActionTree<RootState, any> = {
  SET_ERROR(context, loginError) {
    context.commit("setError", loginError);
  },
};

export default new Vuex.Store({
  state,
  mutations,
  actions,
  modules: {},
});
