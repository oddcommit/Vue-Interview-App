<template>
  <nav>
    <b-button variant="primary" @click="onLogoutClicked">Logout</b-button>
  </nav>
</template>

<script lang="ts">
import { logoutUser } from "@/utils/authenticationUtils";
import Vue from "vue";
export default Vue.extend({
  methods: {
    onLogoutClicked: function(): void {
      logoutUser();
    },
  },
});
</script>

<style lang="scss" scoped>
nav {
  display: flex;
  justify-content: flex-end;
  padding: 10px;
  margin-bottom: 20px;
  height: 64px;
  box-shadow: 0px 1px 4px 0px rgba(0, 0, 0, 0.1);
}
</style>
