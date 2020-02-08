<template>
  <div id="index-page">
    <!-- 头部菜单栏  -->
    <header-bar-component :menuActiveIndex="header_bar_component_menuActiveIndex"></header-bar-component>

    <!--  正文区 -->
    <div class="body-box">
      <!-- 左边  -->
      <div class="left-body-box">
        <!--  轮播图 -->
        <div class="slide-show-box">
          <slide-show-component></slide-show-component>
        </div>
      </div>

      <!-- 右边  -->
      <div class="right-body-box">

      </div>

    </div>

  </div>
</template>

<script>
  import HeaderBarComponent from "../common/HeaderBarComponent"
  import SlideShowComponent from "../index/SlideShowComponent";
export default {
  data(){
    return {
      // 头部菜单栏 组件数据
      header_bar_component_menuActiveIndex: 0,

    }
  },
  methods:{
  },

  components: {
    "header-bar-component": HeaderBarComponent,
    'slide-show-component': SlideShowComponent,
  }
}
</script>

<style lang="scss" scoped>
  #index-page{width: 100%;text-align: center;
    .body-box{width: 90%;margin: 80px auto;display: flex;flex-direction: row;justify-content: space-between;
      .left-body-box{width: 1000px;}
      .right-body-box{width: 347px;background-color: #0066ff; height: 1000px;}
    }
  }
</style>
