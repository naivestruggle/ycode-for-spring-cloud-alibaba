<template>
  <div id="side-menu-component">
    <div class="side-menu-box">
      <el-row class="tac">
        <el-col :span="12">
          <el-menu
            default-active="2"
            router
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose">

            <el-menu-item index="/index">
              <i class="el-icon-menu"></i>
              <span slot="title">主页</span>
            </el-menu-item>

            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>资源操作</span>
              </template>

              <el-menu-item-group title="轮播图">
                <el-menu-item index="/slideshow/upload">上传轮播图</el-menu-item>
                <el-menu-item index="/slideshow/update">修改轮播图</el-menu-item>
                <el-menu-item index="/slideshow/recycled">轮播图回收站</el-menu-item>
              </el-menu-item-group>

              <el-menu-item-group title="分组2">
                <el-menu-item index="1-3">选项3</el-menu-item>
              </el-menu-item-group>

            </el-submenu>

            <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </el-menu-item>
            <el-menu-item index="3" disabled>
              <i class="el-icon-document"></i>
              <span slot="title">导航三</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-setting"></i>
              <span slot="title">导航四</span>
            </el-menu-item>
          </el-menu>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  export default {
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
</script>

<style lang="scss" scoped>
  #side-menu-component{padding-top: 60px; width: 250px;height: 99%; overflow-y: hidden; overflow-x: hidden;   position: absolute;top: 0;left: 0;bottom: 0;box-shadow: 0px 0px 5px #aaa;border-radius:4px;
    .side-menu-box{width: 250px;
      .el-col-12{width: 100%;}
    }
  }
  #side-menu-component:hover{overflow-y: auto;}
  #side-menu-component::-webkit-scrollbar{width:5px;height: 1px;}
  #side-menu-component::-webkit-scrollbar-thumb{border-radius: 5px; box-shadow   : inset 0 0 5px rgba(0, 0, 0, 0.1); background: rgba(83, 83, 83, 0.25);}
</style>
