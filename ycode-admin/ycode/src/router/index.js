import Vue from 'vue'
import Router from 'vue-router'
// import LoginComponent from '@/components/login/LoginComponent'
import LogoComponent from '@/components/login/LogoComponent'
import LoginPage from '@/components/page/LoginPage'
import IndexPage from '@/components/page/IndexPage'
import TopMenuComponent from '@/components/others/TopMenuComponent'
import SideMenuComponent from '@/components/others/SideMenuComponent'

// content
import IndexContentComponent from "../components/content/IndexContentComponent";
import UploadSlideshowComponent from "../components/content/slideshow/UploadSlideshowComponent";
import UpdateSlideshowComponent from "../components/content/slideshow/UpdateSlideshowComponent"
import RecycledSlideshowComponent from "../components/content/slideshow/RecycledSlideshowComponent";


Vue.use(Router)

var router = new Router({
  routes: [
    {
      path: '/',
      redirect(){return '/login'}
    },
    {
      path: '/login',
      name: 'LoginPage',     //登录页面
      component: LoginPage
    },
    {
      path: '/',
      name: 'IndexPage',    // 主页面
      component: IndexPage,
      children: [
        {path: '/index', component: IndexContentComponent},
        {path: '/slideshow/upload', component: UploadSlideshowComponent},
        {path: '/slideshow/update', component: UpdateSlideshowComponent},
        {path: '/slideshow/recycled', component: RecycledSlideshowComponent},
      ]
    },
    {
      path: '/topMenu',
      name: 'TopMenuComponent',  //头部的 菜单组件
      component: TopMenuComponent
    },
    {
      path: '/sideMenu',
      name: 'SideMenuComponent',   // 左边的  可滑动菜单组件
      component: SideMenuComponent
    },
  ]
})

// // 在路由前 会被调用的函数
// router.beforeEach((to, from, next) => {
//   var path = to.path  // 要路由到的路径
//
//   // 判断要路由的是不是 /login  如果不是，就要验证当前是否已经登录了
//   if(path !== '/login'){
//     // 调用当前的 $http  =  axios 想后端发送请求，检测当前是否已经登录
//     this.a.app.$http.post("/admin_sso/ping",)
//       .then(function (response) {
//         // 这个data 就是后端响应的 自定义的 ResponseBody 对象
//         var data = response.data
//
//         // 如果 响应状态码 是 302 ，则表示没有登录，要跳转到登录页面
//         if(data.status === 302){
//           next('/login')
//         }
//         // 如果 响应状态码 不是 302 ，则表示已经登录了
//         else{
//           next()
//         }
//       }).catch(function (error) {
//       // 如果请求失败了，说明后端服务器出现问题，则直接跳转到 login 页面
//       next('/login')
//     })
//   }
//   //  如果路由的 是  /login，那让直接跳转即可
//   else{
//     next()
//   }
//
// } )


export default router


