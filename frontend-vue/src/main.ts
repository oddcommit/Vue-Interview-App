import Vue from "vue";
import { BootstrapVue } from "bootstrap-vue";
import Notifications from "vue-notification";

import App from "./App.vue";
import router from "./router";
import store from "./store";
import { axiosHttp } from "./axios";

Vue.config.productionTip = false;
Vue.use(BootstrapVue);
Vue.prototype.$http = axiosHttp;
Vue.use(Notifications);


new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
