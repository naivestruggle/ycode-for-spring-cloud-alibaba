import Vue from 'vue'
import Router from 'vue-router'
import IndexComponent from '@/components/IndexComponent'
import IndexPage from '@/components/page/IndexPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: "/",
      redirect:'/index'
    },
    {
      path: '/index',
      name:'IndexPage',
      component:IndexPage
    },
  ]
})
