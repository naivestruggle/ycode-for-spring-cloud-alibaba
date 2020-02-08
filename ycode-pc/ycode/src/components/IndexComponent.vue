<template>
  <div id="index-component">
<!--    【示例-轮播图】-->
<!--    <slide-show-component-->
<!--      :slide_show_component_showImgList="slide_show_component_showImgList"-->
<!--      :slide_show_component_showTextList="slide_show_component_showTextList">-->
<!--    </slide-show-component>-->

<!--    【示例-新闻模板1】-->
<!--    <news-1-component-->
<!--      :news_1_component_href="news_1_component_href"-->
<!--      :news_1_component_img="news_1_component_img"-->
<!--      :news_1_component_title="news_1_component_title"-->
<!--      :news_1_component_height="news_1_component_height">-->
<!--    </news-1-component>-->

<!--    【示例-推荐模块1】-->
<!--    <recommend-1-component-->
<!--      :recommend_1_component_name="recommend_1_component_name"-->
<!--      :recommend_1_component_moreUri="recommend_1_component_moreUri"-->
<!--      :recommend_1_component_conentList="recommend_1_component_conentList">-->
<!--    </recommend-1-component>-->

<!--    【示例-博文模板1】-->
<!--    <blog-model-1-component></blog-model-1-component>-->

    <!--    【示例-博文模板2】-->
<!--    <blog-model-2-component></blog-model-2-component>-->

    <!--    【示例-博文模板3】-->
<!--    <blog-model-3-component></blog-model-3-component>-->

    <!--    【示例-博文模板4】-->
<!--    <blog-model-4-component></blog-model-4-component>-->

<!--    【示例-加载更多按钮】-->
    <load-more-component></load-more-component>
  </div>
</template>

<script>
  // import SlideShowComponent from "./index/SlideShowComponent"
  import News1Component from "../components/News1Component"
  import Recommend1Component from "../components/Recommend1Component"
  import BlogModel1Component from "../components/BlogModel1Component"
  import BlogModel2Component from "../components/BlogModel2Component"
  import BlogModel3Component from "../components/BlogModel3Component"
  import BlogModel4Component from "../components/BlogModel4Component"
  import LoadMoreComponent from "../components/LoadMoreComponent"
  export default {
    data(){
      return {

        //【示例-轮播图】
        // slide_show_component_showImgList:[
        //   {id:1, img:'http://www.indream96.com:10001/1.jpg'},
        //   {id:2, img:'http://www.indream96.com:10001/2.jpg'},
        //   {id:3, img:'http://www.indream96.com:10001/3.jpg'},
        //   {id:4, img:'http://www.indream96.com:10001/4.jpg'},
        // ],
        // slide_show_component_showTextList:[
        //   {id:1,href:'http://#1',title:'每个强者都会有背后的辛酸挫折1'},
        //   {id:2,href:'http://#2',title:'每个强者都会有背后的辛酸挫折2'},
        //   {id:3,href:'http://#3',title:'每个强者都会有背后的辛酸挫折3'},
        //   {id:4,href:'http://#4',title:'每个强者都会有背后的辛酸挫折4'},
        // ],

        //【示例-新闻模板1】
        // news_1_component_href:'http://news1',
        // news_1_component_img:'http://www.indream96.com:10001/2.jpg',
        // news_1_component_title:'news1news1news1news1news1news1news1news1news1news1news1news1news1news1',
        // news_1_component_height:500

        // 【示例-推荐模块1】
        // recommend_1_component_name:'最新文章',
        // recommend_1_component_moreUri:'http://www.baidu.com',
        // recommend_1_component_conentList: [
        //   {id:1, title:'每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折',dateStr:'2019-01-14'},
        //   {id:2, title:'每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折',dateStr:'2019-01-14'},
        //   {id:3, title:'每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折',dateStr:'2019-01-14'},
        //   {id:4, title:'每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折',dateStr:'2019-01-14'},
        //   {id:5, title:'每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折',dateStr:'2019-01-14'},
        //   {id:6, title:'每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折',dateStr:'2019-01-14'},
        //   {id:7, title:'每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折',dateStr:'2019-01-14'},
        //   {id:8, title:'每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折每个强者都会有背后的辛酸挫折',dateStr:'2019-01-14'},
        // ]
      }
    },
    components:{
      // 'slide-show-component':SlideShowComponent,
      'news-1-component':News1Component,
      'recommend-1-component':Recommend1Component,
      'blog-model-1-component':BlogModel1Component,
      'blog-model-2-component':BlogModel2Component,
      'blog-model-3-component':BlogModel3Component,
      'blog-model-4-component':BlogModel4Component,
      'load-more-component':LoadMoreComponent,
    }
  }
</script>

<style lang="scss" scoped>

</style>
