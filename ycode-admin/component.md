# 登录

## LoginComponent

![image-20200126101937354](images\image-20200126101937354.png)

```html
<login-component
          :login_logo_path="login_logo_path"
          @login="login"
          @set_login_component_account="set_login_component_account"
          @set_login_component_password="set_login_component_password"          @set_login_component_remember_me_checked="set_login_component_remember_me_checked"></login-component>
```

```js
data(){
      return {
        // 登录页面的LOGO 组件中 的图片路径
        login_logo_path:'http://192.168.31.10:40001/static-resources/logo_row.png',

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
        ....
      }
    },
        
        
components:{
      'login-component': LoginComponent
    }
```



## LogoComponent

![image-20200126102530285](images\image-20200126102530285.png)

```html
<logo-component :login_logo_path="login_guangao_path"></logo-component>
```

```js
data(){
      return {
        login_guangao_path :'http://192.168.31.10:40001/static-resources/guanggao.jpg',
      }
    },
        
components:{
      'logo-component':LogoComponent
    }
```



# 主页上的组件

