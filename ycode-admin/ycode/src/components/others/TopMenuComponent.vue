<template>
  <div id="top-menu-component">

    <!-- 左边的 LOGO  -->
    <div class="logo-box">
      <img :src="logo_path" alt="logo">
    </div>

    <!--  右边部分 -->
    <div class="menu-right-box">

      <!--  菜单按钮 -->
      <div class="menu-icon-menu menu-icon-button active-button">
        <i class="el-icon-menu"></i>
      </div>

      <!--  搜索框 -->
      <div class="menu-search-box">
        <div class="search-input-box">
          <input type="text" class="search-input no-input-style" placeholder="Search...">
        </div>
        <div class="search-button-box">
          <span class="glyphicon glyphicon-search search-button" aria-hidden="true"></span>
        </div>
      </div>

      <!--  邮件按钮 -->
      <div class="menu-icon-email menu-icon-button active-button">
        <el-badge is-dot class="item" :hidden="email_badge_hidden_boolean">
          <i class="el-icon-message"></i>
        </el-badge>
      </div>

      <!--  响铃按钮 -->
      <div class="menu-icon-message menu-icon-button active-button">
        <el-badge is-dot class="item" :hidden="message_badge_hidden_boolean">
          <i class="el-icon-message-solid"></i>
        </el-badge>
      </div>


      <!--  头像 -->
      <el-col :span="12">
        <el-dropdown trigger="click" @command="handlerCommand">
          <span class="el-dropdown-link">
            <img :src="head_image_path" alt="" class="menu-head-image">
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-plus">黄金糕</el-dropdown-item>
            <el-dropdown-item icon="el-icon-circle-plus">狮子头</el-dropdown-item>
            <el-dropdown-item icon="el-icon-circle-plus-outline">螺蛳粉</el-dropdown-item>
            <el-dropdown-item icon="el-icon-check">双皮奶</el-dropdown-item>
            <el-dropdown-item command="a" icon="el-icon-circle-check" >蚵仔煎123123</el-dropdown-item>
            <el-dropdown-item command="logout" icon="el-icon-switch-button">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>


    </div>
  </div>
</template>

<script>
  export default {
    data(){
      return {
        logo_path : 'http://192.168.31.10:40001/static-resources/logo_column.png',
        search_input_box: '',
        head_image_path : 'http://192.168.31.10:40001/static-resources/guanggao.jpg',

        email_badge_hidden_boolean : true,
        message_badge_hidden_boolean : false,
      }
    },
    methods: {

      // 右上角的 头像 列表的事件
      handlerCommand(command){  //处理器，根据点击的列表，调用对应的方法
        eval("(this."+command+"())")
      },
      logout(){  //退出按钮监听事件
        var parentThis = this;

        // 弹出选项
        parentThis.$confirm('确认退出登录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 选择了确定  开始退出登录的请求
          parentThis.$http.post("/admin_sso/logout").then(response => {
            if(response.status === parentThis.$global.HTTP_STATUS_CODE__CREATED){
              // 退出登录成功  跳转到登录页面
              parentThis.$router.push('/login')
            }else{
              console.log("退出登录失败了，请求是成功的")
              console.log(response)

              parentThis.$message({type: 'error', message: '操作失败'}); //提示框
            }
          }).catch(error => {
            if(error.response.status === parentThis.$global.HTTP_STATUS_CODE__ADMIN_NOT_LOGIN){
              parentThis.$router.push("/login")
            }else{
              parentThis.$message({type: 'error', message: '操作失败'}); //提示框
            }
          });

        }).catch(() => {
          parentThis.$message({type: 'info', message: '操作已取消'}); //提示框
        });

      },

    }
  }
</script>

<style lang="scss" scoped>
  /*修改 input 的  placeholder*/
  .no-input-style:-ms-input-placeholder {color: #fff;}/* Internet Explorer 10+ */
  .no-input-style::-webkit-input-placeholder {color: #fff;}/* WebKit browsers */
  .no-input-style::-moz-placeholder {color: #fff;}/* Mozilla Firefox 4 to 18 */
  .no-input-style:-moz-placeholder {color: #fff;}/* Mozilla Firefox 19+ */

  .no-input-style:focus:-ms-input-placeholder {color: #000;}/* Internet Explorer 10+ */
  .no-input-style:focus::-webkit-input-placeholder {color: #000;}/* WebKit browsers */
  .no-input-style:focus::-moz-placeholder {color: #000;}/* Mozilla Firefox 4 to 18 */
  .no-input-style:focus:-moz-placeholder {color: #000;}/* Mozilla Firefox 19+ */

  .no-input-style{outline:none; border:none;padding:5px 10px 5px 10px;color:#fff;}

  *{margin:0; padding:0;}
  #top-menu-component{position: fixed;top:0px; left:0px; z-index: 1999; width: 100%;height: 60px;background: linear-gradient(to right, #2fbe91, #8261c3);display: flex;flex-direction: row;justify-content:space-between;padding-right: 40px; box-shadow: 0px 0px 5px #aaa;border-radius:4px;
    .logo-box{width: 230px;height:60px;line-height: 60px;padding:0 40px 0 40px;
      img{width: 150px;height:50px;}
    }
    .menu-right-box{display: flex;flex-direction: row;justify-content:space-between;line-height: 56px;
      .active-button{background-color:rgba(255,255,255,0.3); }
      .menu-icon-button{ width:30px; height:30px; text-align: center; line-height:30px; margin-top:15px; border-radius: 4px; font-size:20px;color:#fff;}
      .menu-search-box{display: flex; flex-direction: row;
        .search-input-box{line-height: 60px;
          .search-input{background-color: rgba(255,255,255,0.3);transition-duration:0.5s;margin-left:40px;width: 800px;height: 30px;border-top-left-radius: 4px;border-bottom-left-radius: 4px;}
          .search-input:focus{border:none;color:#000;background-color: #fff;}
        }
        .search-button-box{line-height: 62px;
          .search-button{background-color: rgba(255,255,255,0.4);width: 50px;height: 30px;margin:0;color:#fff;line-height: 30px;text-align: center;border-top-right-radius: 4px;border-bottom-right-radius: 4px;}
          .search-button:hover{background-color: rgba(255,255,255,0.5);cursor: pointer;}
        }
      }
      .menu-icon-email{margin-left:40px;}
      .menu-icon-message{margin-left:20px;}
      .menu-head-image{margin-left:20px;width:30px;height: 30px;border-radius: 50%;}
      .el-col-12{width: 50px;}

      /*element-ui 下拉菜单的样式*/
      .el-dropdown-link {cursor: pointer;color: #409EFF;}
      .el-icon-arrow-down {font-size: 12px;}
      .demonstration {display: block;color: #8492a6;font-size: 14px;margin-bottom: 20px;}
    }
  }
  /*left: #2fbe91*/
  /*right: #8261c3*/
</style>
