<template>
  <div id="slide-show-component">
    <!-- 轮播图  -->
    <div class="block my-box-shadow slide-show-box my-box-radius">
      <el-carousel @change="slideShowChange" height="400px">
        <el-carousel-item v-for="item in showImgList" :key="item.id">
          <img :src="item.img" alt="">
        </el-carousel-item>
      </el-carousel>
      <!-- 轮播图描述  -->
      <span class="demonstration my-box-shadow-bold my-box-bottom-radius">
          <a class="none-a" :href="initSlideshowDemonstrationHref">
            {{ initSlideshowDemonstration }}
          </a>
        </span>
    </div>
  </div>
</template>


<script>
  export default {
    data(){
      return {
        initSlideshowDemonstration: '',
        initSlideshowDemonstrationHref: '',
        showImgList: [],
        showTextList: [],
      }
    },
    methods:{
      slideShowChange(nowIndex, oldIndex){  //轮播图切换的回调函数
        this.setSideShowDemonstration(nowIndex)
      },
      setSideShowDemonstration(index){  //设置轮播图下面的描述
        var bean = this.showTextList[index]
        this.initSlideshowDemonstration = bean.title
        this.initSlideshowDemonstrationHref = bean.href
      },
    },
    created() {
      var parentThis = this;
      // 获取轮播图
      parentThis.$http.get("/user_slideshow/show").then(succeed => {
        var data = succeed.data
        if(data.status === this.$global.HTTP_STATUS_CODE__OK){
          var dataList = data.data
          for(var i=0; i< dataList.length; i++){
            var bean = dataList[i];
            parentThis.showImgList[i] = {id: bean.id, img: bean.image};
            parentThis.showTextList[i] = {id: bean.id, href: bean.url, title: bean.title}
          }
          parentThis.initSlideshowDemonstration = parentThis.showTextList[0].title;
          parentThis.initSlideshowDemonstrationHref = parentThis.showTextList[0].href;
        }
      }).catch(error => {
        console.log(error.response)
      })
    },
  }
</script>

<style lang="scss" scoped>
  #slide-show-component{
    .my-box-radius{border-radius: 4px;}
    .my-box-shadow{box-shadow: 0px 0px 5px #aaa;}
    .my-box-bottom-radius{border-bottom-left-radius: 4px;border-bottom-right-radius: 4px;}
    .none-a{text-decoration:none;color:#fff;}
    .none-a:hover{color:#0066ff;}
    /*轮播图区域*/
    .slide-show-box{
      .demonstration{width:100%;height:40px;line-height:40px;background-color:#000;text-align:center;display:inline-block;}
      img{width:100%;}
    }
    .my-box-shadow-bold{
      a{text-decoration:none;color:#fff;}
    }
  }
</style>
