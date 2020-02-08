<template>
  <div id="blog-model-4-component">
    <div class="heading">
      Python如何使用List？
    </div>
    <div class="video">
      <video class="video-js">
        <source src="http://www.indream96.com:10001/1.mp4" type="video/mp4">
        <source src="http://www.indream96.com:10001/1.ogg" type="video/ogg">
      </video>
    </div>
    <div class="options">
      <div class="img-name-like-comment">
        <div class="img">
          <img src="http://www.indream96.com:10001/2.jpg" alt="">
        </div>
        <div class="name">
          大 BUG
        </div>
        <a href="#" class="like none-a-no-hover">
          <img src="http://www.indream96.com:10001/9.png" alt=""> 74
        </a>
        <router-link to="#" tag="a" class="comment none-a-no-hover">
          <img src="http://www.indream96.com:10001/10.png" alt=""> 13
        </router-link>
      </div>
      <div class="classify">
        <router-link to="#" tag="a" class="class-li none-a-no-hover">
          Python
        </router-link>
        <router-link to="#" tag="a" class="class-li none-a-no-hover">
          Java
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return {}
  },
  mounted(){
    this.videoWidth = document.getElementsByClassName('video')[0].offsetWidth;
    this.initVideo();
  },
  methods: {
    initVideo() {
      //初始化视频方法
      let myPlayer = this.$video(document.querySelector('.video-js'), {
        //确定播放器是否具有用户可以与之交互的控件。没有控件，启动视频播放的唯一方法是使用autoplay属性或通过Player API。
        controls: true,
        //自动播放属性,muted:静音播放
        // autoplay: "muted",
        autoplay: false,
        //建议浏览器是否应在<video>加载元素后立即开始下载视频数据。
        preload: "auto",
        //设置视频播放器的显示宽度（以像素为单位）
        width: this.videoWidth,
        //设置视频播放器的显示高度（以像素为单位）
        // height: "400px"
      });
    },
  },
  props:[

  ]
}
</script>

<style lang="scss" scoped>
  #blog-model-4-component{padding:20px;background-color:#fff;margin-top:20px;border-radius: 4px;box-shadow: 0px 0px 5px #aaa;
    .none-a-no-hover{text-decoration:none;color:#555;}

    .heading{font-size:20px;font-weight: bold;}
    .video{width:100%;margin:20px 0 20px 0;border-radius: 5px;
      video{width:100%;border-radius: 5px;}
    }
    .options{display:flex;flex-direction: row;justify-content: space-between;line-height: 30px;
      .img-name-like-comment{display: flex;flex-direction: row;}
      .img-name-like-comment{
        .img{width:30px;height:30px;
          img{width:30px;height:30px;border-radius: 15px;border-radius: 15px;box-shadow: 0px 0px 10px #000;}
        }
        .name{margin-left:10px;}
        .like{
          img{margin:-7px 0 0 20px;width: 15px;}
        }
        .comment{
          img{margin:-3px 0 0 10px;width: 15px;}
        }
      }
      .classify{display: flex;flex-direction: row;
        .class-li{padding:0 5px 0 5px;box-shadow: 0px 0px 5px inset dodgerblue;border-radius:3px;margin:0 5px 0 5px;}
        .class-li:hover{box-shadow: 0px 0px 10px inset dodgerblue;}
      }
    }
  }
</style>
