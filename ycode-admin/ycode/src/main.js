// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

Vue.config.productionTip = false

//引入jquery
import $ from 'jquery'

//引入bootstrap
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min'
import 'bootstrap/dist/fonts/glyphicons-halflings-regular.eot'
import 'bootstrap/dist/fonts/glyphicons-halflings-regular.svg'
import 'bootstrap/dist/fonts/glyphicons-halflings-regular.ttf'
import 'bootstrap/dist/fonts/glyphicons-halflings-regular.woff'
import 'bootstrap/dist/fonts/glyphicons-halflings-regular.woff2'


//引入element-ui
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/fonts/element-icons.ttf'
import 'element-ui/lib/theme-chalk/fonts/element-icons.woff'
import 'element-ui/lib/index.js'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(Element)

// 引入全局变量
import global from './components/Global.vue'
Vue.prototype.$global=global

Vue.prototype.toLogin=function () {
  var _this = this;
  _this.$alert('登录已过期，请重新登录！', '登录过期', {
    confirmButtonText: '确定',
    callback: action => {
      _this.$router.push("/login")  // 跳转到登录页面
    }
  });
}

//引入 axios
import axios from 'axios'

// 引入qs 模块 处理axios的post传参问题
import qs from 'qs';
Vue.prototype.$qs = qs;

axios.defaults.withCredentials=true  // 请求时带上cookie
axios.defaults.baseURL=global.requestBaseUrl;  //请求地址的公有部分
Vue.prototype.$http=axios


import Cookies from 'vue-cookies'
Vue.use(Cookies)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
