<template>
  <div class="height-100 center">
    <div class="login-continer">
      <h3>Login</h3>
      <b-form-input
        type="text"
        aria-label="email"
        placeholder="E-Mail"
        v-model="email"
        @keydown="clearError"
      ></b-form-input>
      <b-form-input
        type="password"
        aria-label="password"
        placeholder="Password"
        v-model="password"
        @keydown="clearError"
      ></b-form-input>
      <b-alert show variant="danger" v-if="loginError" class="login-failed">{{
        loginError
      }}</b-alert>
      <b-button variant="primary" @click="login">Login</b-button>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import { mapActions } from "vuex";
import axios, { AxiosError, AxiosResponse } from "axios";
import { AxiosLoginErrorResponse } from "@/types";
import { LoginPost, LoginResponse } from "./types";
import VueRouter, { RouteConfig } from "vue-router";

export default Vue.extend({
  name: "Login",
  data: function() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    clearError: function(): void {
      if (this.loginError) {
        this.loginError = "";
      }
    },
    login: function(): void {
      axios
        .post<LoginResponse>(`http://localhost:4667/api/login`, {
          email: this.email,
          password: this.password,
        })
        .then((response: AxiosResponse<LoginResponse>) => {
          localStorage.setItem("jwtToken", response.data.jwtToken);
          this.$router.push({ path: "/user" });
        })
        .catch((e: AxiosError<AxiosLoginErrorResponse>) => {
          if (e && e.response) {
            this.loginError = e.response.data.errorMessage;
          } else {
            //TODO: Send mistake to server
            this.loginError =
              "Something really odd occurred. Please contect us at ...";
          }
        });
    },
  },
  computed: {
    loginError: {
      get() {
        return this.$store.state.loginError;
      },
      set(value) {
        this.$store.dispatch("SET_ERROR", value);
      },
    },
  },
});
</script>

<style lang="scss" scoped>
.login-failed {
  width: 100%;
}

.login-continer {
  @extend .center;
  flex-wrap: wrap;
  max-width: 430px;
  padding: 30px;
  box-shadow: 0px 0px 7px -1px rgba(0, 0, 0, 0.35);
}

input {
  margin: 10px;
}

.alert {
  margin-bottom: 10px;
}
</style>
