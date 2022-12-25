<template>
  <div class="about-container">
    <div>
      <h2>This is the users page</h2>
      <b-table striped hover :items="this.$store.state.users"></b-table>
    </div>
  </div>
</template>

<script lang="ts">
import { AxiosBackendError } from "@/types";
import axios, { AxiosResponse, AxiosError } from "axios";

import Vue from "vue";
import { User } from "./types";
import isEmpty from "lodash/isEmpty";

export default Vue.extend({
  mounted(): void {
    this.$store.dispatch("FETCH_USERS");
  },
  // computed: {
  //   users: {
  //     get(): Array<User> {
  //       return this.$store.state.users;
  //     },
  //     set(value: Array<User>): void {
  //       this.$store.dispatch("SET_USERS", value);
  //     },
  //   },
  // },
});
</script>

<style lang="scss" scoped>
.about-container {
  @extend .center;
  text-align: center;
}
</style>
