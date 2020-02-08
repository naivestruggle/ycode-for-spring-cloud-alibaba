<template>
  <div id="login-box">
    <div class="login-headline-text">
      <img :src="login_logo_path" alt="LOGO"/>
      Ycode Admin
    </div>
    <div class="login-account-password-box">
      <el-input placeholder="请输入账号" v-model="login_component_account" clearable ></el-input>
      <br/>
      <el-input placeholder="请输入密码" v-model="login_component_password" show-password></el-input>
      <br/>
      <el-button type="primary" @click="login" >登录</el-button>
    </div>
    <div class="login-options-box">
      <!-- `checked` 为 true 或 false -->
      <div class="remember-me-checkbox">
        <el-checkbox v-model="login_component_remember_me_checked">记住账号</el-checkbox>
      </div>
      <div class="forgot-password-link">
        忘记密码
      </div>
    </div>
    <div class="to-register-link">
      没有账号？请向有关部门申请
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return {
      login_component_account:'',
      login_component_password:'',
      login_component_remember_me_checked: true,
    }
  },
  methods:{
    login(){
      // 调用父组件的 login() 方法  如果要传递参数，跟在后面即可
      this.$emit("login")
    }
  },
  props:[
    'login_logo_path'
  ],
  watch:{
    login_component_account(newValue, oldValue){
      this.$emit("set_login_component_account", newValue);
    },
    login_component_password(newValue, oldValue){
      this.$emit("set_login_component_password", newValue);
    },
    login_component_remember_me_checked(newValue, oldValue){
      this.$emit("set_login_component_remember_me_checked", newValue);
    }
  },

  created() {  //全局监听事件
    var _this = this;
    document.onkeydown = function(e) {
      let key = window.event.keyCode;
      if (key === 13) {
        _this.login();
      }
    };
  }
}
</script>

<style lang="scss" scoped>
  #login-box{ width: 100%; text-align: center; font-size: 30px;box-shadow: 0px 0px 5px #aaa;border-radius:4px;padding:30px 0 30px 0;
    .login-headline-text{margin:0 auto;
      img{width:50px;height:50px;}
    }
    .login-account-password-box{margin-top:20px;
      .el-input{width:300px;
        .el-input__inner{height: 30px !important;line-height: 30px;}
      }
      .el-button{width: 300px;margin-top: 10px;}
    }
    .login-options-box{display: flex;flex-direction: row; justify-content:space-between;width: 300px; margin: 0 auto;
      .remember-me-checkbox{width:100px;height: 40px;line-height: 40px;}
      .forgot-password-link{font-size:13px;width:100px;height: 40px;line-height: 50px;}
    }
    .to-register-link{margin-top:10px; font-size: 12px;}
  }
</style>
