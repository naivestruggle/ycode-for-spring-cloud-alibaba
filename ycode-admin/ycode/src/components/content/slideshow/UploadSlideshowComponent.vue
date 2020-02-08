<template>
  <div id="upload-slide-show-component">

    <!--  标题 -->
    <div class="headline">
      上传轮播图
    </div>

    <!-- 轮播图信息输入框  -->
    <div class="slide-show-info-box">
      <!-- 标题的输入框  -->
      <div class="title">
        <el-input v-model="title_input" placeholder="给轮播图设置一个吸引人的标题吧">
          <template slot="prepend">标题：</template>
        </el-input>
      </div>

      <!--  轮播图链接 -->
      <div class="url">
        <el-input v-model="url_input" placeholder="点击轮播图会跳转到哪里呢">
          <template slot="prepend">链接：</template>
        </el-input>
      </div>

      <!--  上传轮播图的地方，图片上传与信息存储是分离的 -->
      <div class="image" onclick="slideshow.click()">
        <img :src="imageUrl" alt="" class="img-default" v-if="!hasRealImage">
        <img :src="realUrl" alt="" class="img-real" v-if="hasRealImage">
        <input id="slideshow" type="file" ref="upload_file" @change="changeSlideshowFile" style="display: none;">
      </div>

      <!--  保存按钮 -->
      <div class="save-button-box">
        <el-button type="primary" @click="saveSlideshow">保存</el-button>
        <el-button type="warning" @click="slideshowInfoReset">重置</el-button>
      </div>
    </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        title_input: '',  // 轮播图标题
        url_input: 'http://',  //轮播图链接
        hasRealImage: false,  // 当前是否上传了图片
        imageUrl: 'http://192.168.31.10:40001/static-resources/upload_slideshow_default.png',  // 默认图片
        realUrl: '',  //上传成功后 返回的真实路径
      };
    },
    methods: {
      /**
       * 保存轮播图
       */
      saveSlideshow(){
        var params = {  // 封装参数对象
          image: this.realUrl,  // 图片的真实路径
          title: this.title_input, // 轮播图标题
          url: this.url_input, // 轮播图链接
        }
        // 使用axios 的 put 请求  访问 ycode-web-slideshow-admin 服务提供的接口
        // this.$http：是在main.js 中将 axios 赋值了
        // this.$qs.stringify(params)：axios 默认提交参数的编码格式是 application/x-www-form-urlencoded   就是 name=yangxinhu?age=22  的形式，所以要手动将js 对象转为这种格式
        this.$http.put("/admin_slideshow/insert", this.$qs.stringify(params)).then(response => {
          this.title_input = '';  // 轮播图标题
          this.url_input = 'http://';  //轮播图链接
          this.hasRealImage = false;  // 当前是否上传了图片
          this.realUrl = '';
          this.$message({type: 'success', message: '操作成功'});
        }).catch(error => {
          var response = error.response;
          var status = response.status;
          // 判断响应状态码 是否 为 未登录的状态码   this.$global.HTTP_STATUS_CODE__ADMIN_NOT_LOGIN ：是自定义的全局变量
          if(status === this.$global.HTTP_STATUS_CODE__ADMIN_NOT_LOGIN){
            var realThis = this;

            // 弹出选项
            this.$confirm('此操作需要登录(登录后无操作10分钟自动退出)，是否登录?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              realThis.$router.push("/login")  // 跳转到登录页面
            }).catch(() => {
              //提示框
              this.$message({type: 'info', message: '操作已取消'});
            })

          }else{
            //提示框   弹出其他错误
            this.$message({type: 'error', message: response});
          }
        })
      },

      /**
       * 选中图片，要讲选中的图片上传到 fastDFS 中
       */
      changeSlideshowFile(){
        var _this = this;
        // 文件数组， 是当前选中的文件
        var files = _this.$refs.upload_file.files

        // 封装参数对象
        var formData = new FormData();
        // 将 文件数组中第一个文件添加到参数对象中
        formData.append('multipartFile', files[0])

        // 使用 axios 的 put 请求， 访问 ycode-web-slideshow-admin 服务提供的接口
        // this.$http：是在main.js 中将 axios 赋值了
        _this.$http.put("/admin_slideshow/slideshow", formData).then(response => {
          var data = response.data  // 取出响应对象中的 数据对象
          _this.realUrl = data.data  // 得到上传成功，返回来的真实图片路径
          _this.hasRealImage = true  // 将 是否有真实路径 改为 true
        }).catch(error => {
          var response = error.response;
          var status = response.status;
          // 判断响应状态码 是否 为 未登录的状态码   this.$global.HTTP_STATUS_CODE__ADMIN_NOT_LOGIN ：是自定义的全局变量
          if(status === this.$global.HTTP_STATUS_CODE__ADMIN_NOT_LOGIN){
            _this.toLogin()
          }else{
            //提示框   弹出其他错误
            this.$message({type: 'error', message: response});
          }
        });
      },

      /**
       * 重置所有信息
       */
      slideshowInfoReset(){
        this.hasRealImage = false;
        this.realUrl = '';
        this.url_input = 'http://';
        this.title_input = '';
      },
    }
  }

</script>

<style lang="scss" scoped>
  #upload-slide-show-component{
    /*标题*/
    .headline{width: 100%; height: 40px;font-size:20px;border-bottom:1px #409EFF solid;}

    /*信息输入框*/
    .slide-show-info-box{text-align: center;
      .title{width:500px;margin: 20px auto;}
      .url{width:500px;margin: 20px auto;}
      /*轮播图上传框*/
      .image{width:800px;height:320px;border:1px #888 dashed;cursor: pointer;text-align: center;line-height: 200px;border-radius: 10px;margin: 20px auto;
        .img-real{width: 100%; height: 100%;border-radius: 10px;}
        .img-default{width: 150px;height:150px;margin:90px auto;border-radius: 10px;}
      }
      .image:hover{background-color: #eee; border:1px blue dashed;}
    }

  }
</style>
