<template>
    <div id="login-page-box">

      <div class="login-page-logo-box">
        <logo-component :login_logo_path="login_guangao_path"></logo-component>
      </div>

      <div class="login-page-login-box">
        <login-component
          :login_logo_path="login_logo_path"
          @login="login"
          @set_login_component_account="set_login_component_account"
          @set_login_component_password="set_login_component_password"
          @set_login_component_remember_me_checked="set_login_component_remember_me_checked"></login-component>
      </div>

    </div>
</template>

<script>
  import LoginComponent from '../login/LoginComponent'
  import LogoComponent from '../login/LogoComponent'
  export default {
    data(){
      return {
        // 登录页面的LOGO 组件中 的图片路径
        login_logo_path:'http://192.168.31.10:40001/static-resources/logo_row.png',
        login_guangao_path :'http://192.168.31.10:40001/static-resources/guanggao.jpg',

        //登录页面的 登录组件 中的几个数据
        login_component_account:'',
        login_component_password:'',
        login_component_remember_me_checked: true,
      }
    },
    methods:{
      set_login_component_account(newValue){   //子组件调用  保证父子组件值同步
        this.login_component_account = newValue;
      },
      set_login_component_password(newValue){  //子组件调用  保证父子组件值同步
        this.login_component_password = newValue;
      },
      set_login_component_remember_me_checked(newValue){  //子组件调用  保证父子组件值同步
        this.login_component_remember_me_checked = newValue
      },
      login(){  //登录方法 由子组件回调
        var params = {
          account: this.login_component_account,
          password: this.login_component_password
        }
        var parent = this
        this.$http.post("/admin_sso/login", this.$qs.stringify(params)).then(function (response) {
          parent.$router.push('/index')
        }).catch(function (error) {
          console.log(error)
        })
      }
    },
    components:{
      'login-component': LoginComponent,
      'logo-component':LogoComponent
    }
  }

</script>

<style lang="scss" scoped>
  #login-page-box{
    .login-page-logo-box{width: 600px; height:400px;position: absolute; left: 100px;top:50px;}
    .login-page-login-box{width: 400px;position: absolute; right: 100px;top:100px;}
  }
</style>
