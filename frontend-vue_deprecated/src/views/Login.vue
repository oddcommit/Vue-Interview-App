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
import { LoginPost } from "./types";
import { JwtTokenReqestOrResponse, AxiosBackendError } from "@/types";
import VueRouter, { RouteConfig } from "vue-router";
import { loginUser } from "@/utils/authenticationUtils";

export default {
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
      this.$http
        .post<JwtTokenReqestOrResponse>(
          "/login",
          {
            email: this.email,
            password: this.password,
          },
          { skipAuthRefresh: true }
        )
        .then((response: AxiosResponse<JwtTokenReqestOrResponse>) => {
          loginUser(response.data.jwtToken);
        })
        .catch((e: AxiosError<AxiosBackendError>) => {
          if (e && e.response) {
            this.loginError = e.response.data.errorMessage;
          } else {
            //TODO: Send mistake to server
            this.loginError = `We got some problems
            at the moment. Error message: ${e.message}. Try again later.`;
          }
        });
    },
  },
  computed: {
    loginError: {
      get(): string {
        return this.$store.state.loginError;
      },
      set(value: string): void {
        this.$store.dispatch("SET_ERROR", value);
      },
    },
  },
};
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
  text-align: center;
  margin-bottom: 10px;
}
</style>
