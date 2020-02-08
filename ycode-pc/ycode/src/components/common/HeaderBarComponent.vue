<template>
  <div id="header-bar-component">

    <!--  最大的盒子  1366px 是居中的 -->
    <div class="header-bar-box">

      <!--  logo -->
      <div class="logo-box">
        <img :src="logo_src_path" alt="logo">
      </div>

      <!--  菜单列表 -->
      <div class="menu-list-box">
        <router-link tag="div" to="/index" :class="menuClass[0]" >首页</router-link>
        <router-link tag="div" to="/blog" :class="menuClass[1]">博文</router-link>
        <router-link tag="div" to="/media" :class="menuClass[2]">音频</router-link>
        <router-link tag="div" to="/software" :class="menuClass[3]">软件</router-link>
        <router-link tag="div" to="/info" :class="menuClass[4]">资讯</router-link>
      </div>

      <!--  搜索框 -->
      <div class="search-box">
        <input class="search-input no-input-style" type="text" placeholder="Search...">
        <el-button class="search-submit no-input-style" icon="el-icon-search" title="搜索"></el-button>
      </div>

      <!--  头像 -->
      <div class="head-image-box">

        <!--  用户登录后显示的用户头像 -->
        <el-dropdown trigger="click" v-if="isLogined">
          <span class="el-dropdown-link">
            <img :src="logined_user_image_path" alt="用户头像" title="yangxinhu">
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-plus">黄金糕</el-dropdown-item>
            <el-dropdown-item icon="el-icon-circle-plus">狮子头</el-dropdown-item>
            <el-dropdown-item icon="el-icon-circle-plus-outline">螺蛳粉</el-dropdown-item>
            <el-dropdown-item icon="el-icon-check">双皮奶</el-dropdown-item>
            <el-dropdown-item icon="el-icon-circle-check">蚵仔煎</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <!--  用户没有登录时 显示的头像 -->
        <img :src="default_user_image_path" alt="默认头像" v-if="!isLogined" @click="toLogin" title="登录">

      </div>
    </div>

  </div>
</template>

<script>
export default {
  data(){
    return {
      logo_src_path: 'http://192.168.31.10:40001/static-resources/logo_column.png',
      logined_user_image_path: 'http://192.168.31.10:40001/static-resources/user_head_image.jpg',
      default_user_image_path: 'http://192.168.31.10:40001/static-resources/default_user_head_image.png',
      isLogined: true,
      menuClass: [
        'menu-li',
        'menu-li',
        'menu-li',
        'menu-li',
        'menu-li',
      ],
    }
  },
  created() {
    this.menuClass[this.menuActiveIndex] = 'menu-li active-menu'
  },
  methods:{
    toLogin(){
      console.log("登录")
    }
  },
  props:[
    'menuActiveIndex'
  ]
}
</script>

<style lang="scss" scoped>

  .no-input-style{outline:none; border:none;padding:5px 10px 5px 10px;}

  #header-bar-component{width: 100%;height:auto;text-align: center;background: linear-gradient(to right, #eee, #000000);box-shadow: 0px 0px 5px #888888;position: fixed;top:0px;left:0px;z-index: 9999;
    .header-bar-box{width: 90%;height: 60px;margin: 0 auto;display: flex;flex-direction: row;justify-content: space-between;

      /*logo图标框*/
      .logo-box{width: 200px; height: 60px;
        img{width: 160px; height: 60px;}
      }

      /*菜单列表*/
      .menu-list-box{width: 650px;height: 60px;display:flex; flex-direction: row;justify-content: space-between;
        .menu-li{font-size:20px; letter-spacing: 10px; width:130px; line-height: 60px; cursor: pointer;  transition-duration:0.5s;border-left: 1px #fff solid;border-right: 1px #fff solid;}
        .menu-li:hover{background-color: rgba(255,255,255,0.6);}

        .active-menu{background-color: #fff;}
      }

      /*搜索框*/
      .search-box{width: 300px;height: 60px;padding-top: 13px;
        .search-input{height:34px;background-color: rgba(255,255,255,0.7);transition-duration:0.5s;border-top-left-radius: 17px;border-bottom-left-radius: 17px;}
        .search-input:focus{border:none;color:#000;background-color: #fff;}

        .search-submit{margin-left:-3px;height:34px;width:50px;transition-duration:0.5s;border-top-right-radius: 17px;border-bottom-right-radius: 17px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;background-color: rgba(255,255,255,0.7);}
        .search-submit:hover{border:none;color:#000;background-color: rgba(255,255,255,0.9);}
      }

      /*头像框*/
      .head-image-box{width:60px;height: 60px;line-height: 60px;
        img{width: 50px; height: 50px;border-radius: 50px;box-shadow: 0px 0px 5px #fff;background-color: #fff;cursor: pointer;}
      }
    }

  }
</style>
