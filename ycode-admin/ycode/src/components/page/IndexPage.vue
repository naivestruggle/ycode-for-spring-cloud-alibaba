<template>
  <div id="index-page-box">

    <!--  头部菜单栏 -->
    <div class="top-menu-bar">
      <top-menu-component></top-menu-component>
    </div>

    <!--  左侧的菜单导航栏 -->
    <div class="left-side-menu-bar">
      <side-menu-component></side-menu-component>
    </div>

    <div class="content-box">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  import TopMenuComponent from "../others/TopMenuComponent"
  import SideMenuComponent from "../others/SideMenuComponent"
  export default {
    data(){
      return {}
    },
    components:{
      'top-menu-component': TopMenuComponent,
      'side-menu-component': SideMenuComponent,
    }
  }
</script>

<style lang="scss" scoped>
  #index-page-box{
    .left-side-menu-bar{}
    .content-box{position: absolute;width:1280px; left: 250px;top:0;padding:80px 20px 20px 20px;height: 99%; overflow-y: auto; overflow-x: hidden;box-shadow: 0px 0px 5px #aaa;border-radius:4px;

    }
    .content-box::-webkit-scrollbar{width:5px;height: 1px;}
    .content-box::-webkit-scrollbar-thumb{border-radius: 5px; box-shadow   : inset 0 0 5px rgba(0, 0, 0, 0.1); background: rgba(83, 83, 83, 0.25);}
  }


</style>
