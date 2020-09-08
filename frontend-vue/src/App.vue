<template>
  <div id="app">
    <notifications
      class="notification"
      group="generalErrorMessage"
      position="top center"
    />
    <div class="loading" v-if="isLoading">
      <b-spinner type="grow" label="Spinning"></b-spinner>
    </div>
    <div class="height-100" v-if="!isLoading">
      <Navbar v-if="this.$store.state.isLoggedIn" />
      <router-view />
    </div>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import Navbar from "./components/Navbar.vue";
import axios, {
  AxiosError,
  AxiosResponse,
  AxiosInstance,
  AxiosRequestConfig,
} from "axios";
import { JwtTokenReqestOrResponse, AxiosBackendError } from "@/types";
import createAuthRefreshInterceptor, {
  AxiosAuthRefreshOptions,
} from "axios-auth-refresh";
import { loginUser, logoutUser } from "@/utils/authenticationUtils";

export default {
  data() {
    return {
      isLoading: true,
    };
  },
  mounted(): void {
    const jwtToken: string | null = localStorage.getItem("jwtToken");
    if (jwtToken) {
      this.$http
        .post<JwtTokenReqestOrResponse>(`/renewToken`, {
          jwtToken: jwtToken,
        })
        .then((response: AxiosResponse<JwtTokenReqestOrResponse>) => {
          loginUser(response.data.jwtToken);
          this.setLoadingFalse();
        })
        .catch((e: AxiosError<AxiosBackendError>) => {
          logoutUser();
          this.setLoadingFalse();
        });
    } else {
      if (this.$route.name !== "Login") {
        logoutUser();
      }
      this.setLoadingFalse();
    }
  },
  methods: {
    setLoadingFalse: function() {
      this.isLoading = false;
    },
  },
  components: {
    Navbar,
  },
};
</script>

<style lang="scss">
@import "node_modules/bootstrap/scss/bootstrap";
@import "node_modules/bootstrap-vue/src/index.scss";

.app {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

#app {
  @extend .app;
}

.loading {
  @extend .app;
  justify-content: center;
  align-items: center;
}

.notification {
  margin-top: 20vh;
}
</style>
