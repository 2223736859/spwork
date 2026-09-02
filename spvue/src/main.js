import Vue from 'vue'
import App from './App.vue'
import router from './router.js';
import ElementUI from 'element-ui';                      // 引入element-ui
import 'element-ui/lib/theme-chalk/index.css';
import './api';                                            // 应用全局 axios 配置（拦截器等）
Vue.config.productionTip = false
Vue.use(ElementUI);
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
