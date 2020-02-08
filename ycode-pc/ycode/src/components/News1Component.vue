<template>
  <div id="news-1-component">
    <div class="news-item my-box-shadow my-box-radius" :style="'height:'+news_1_component_height+'px'">
      <a :href="news_1_component_href">
        <img class="my-box-top-radius" :src="news_1_component_img" alt="">
      </a>
      <div class="news-item-title my-box-bottom-radius" :title="news_1_component_title">
        <a class="none-a" :href="news_1_component_href">
          {{ news_1_component_title }}
        </a>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data(){
      return {

      }
    },
    props:[
      'news_1_component_href',
      'news_1_component_img',
      'news_1_component_title',
      'news_1_component_height'
    ]
  }
</script>

<style lang="scss" scoped>
  #news-1-component{ margin: 0;padding: 0;
    .my-box-radius{border-radius: 4px;}
    .my-box-shadow{box-shadow: 0px 0px 5px #aaa;}
    .my-box-bottom-radius{border-bottom-left-radius: 4px;border-bottom-right-radius: 4px;}
    .none-a{text-decoration:none;color:#fff;}
    .none-a:hover{color:#0066ff;}

    .news-item{
      width: 100%;overflow: hidden;
      img{width: 100%;height: 85%;transition: all 0.6s;}
      img:hover{transform: scale(1.4);}
    }

    .news-item-title{
      background-color:#000;position: relative;bottom: 0px;height:15%;line-height: inherit;
      a{color:#fff;text-decoration:none;margin: 0 5px 0 5px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
    }
  }

</style>
