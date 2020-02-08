<template>
  <div id="blog-model-3-component">
    <div class="left-like">
      <div class="number my-box-shadow my-box-radius">
        1
      </div>
      <div class="like my-box-shadow my-box-radius">
        <a href="#" class="none-a-no-hover">
          <i class="el-icon-thumb">点赞</i>
        </a>
      </div>
      <div class="msg">
        <span style="color:red;">请先</span><a href="#">登录</a>
      </div>
    </div>
    <div class="right-info">
      <div class="headline">
        Python基础入门总结
      </div>
      <div class="author-describe">
        <div class="author">
          <img src="http://www.indream96.com:10001/2.jpg" alt="">
        </div>
        <div class="describe">
          Python基础入门教学 基础中的基础 列表、元组(tuple)、字典、字符串 变量和引用 函数 python视频教程下载 基础中的基础 解释型语言和编译型语言差距；
          Python概述 解释器执行原理 which python3可以查看python3的位置(linux下)； 交互式环境中使用exitPython基础入门教学 基础中的基础 列表、元组(tuple)、字典、字符串 变量和引用 函数 python视频教程下载
          基础中的基础 解释型语言和编译型语言差距； Python概述 解释器执行原理 which python3可以查看python3的位置(linux下)； 交互式环境中使用exit
        </div>
      </div>
      <div class="options">
        <div class="name">
          <router-link to="#" tag="a" class="none-a-no-hover icon-hover">
            pypypypy
          </router-link>
        </div>
        <div class="time">
          发布于 2019-11-04 19:50
        </div>
        <div class="comment">
          <router-link to="#" tag="a" class="none-a-no-hover">
            <i class="el-icon-chat-line-square">评论(0)</i>
          </router-link>
        </div>
        <div class="read">
          <router-link to="#" tag="a" class="none-a-no-hover">
            <i class="el-icon-reading">阅读(29)</i>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return {}
  },
  props:[

  ]
}
</script>

<style lang="scss" scoped>
  #blog-model-3-component{margin-top:20px;background-color:#fff;padding:20px;display:flex;flex-direction: row;border-radius: 4px;box-shadow: 0px 0px 5px #aaa;
    .none-a-no-hover{text-decoration:none;color:#555;}
    .icon-hover:hover{color:#3399ea;}
    .my-box-shadow{box-shadow: 0px 0px 5px #aaa;}
    .my-box-radius{border-radius: 4px;}


    .left-like{width:60px;display:flex;flex-direction: column;
      .number{width:60px;height:30px;background-color:#F63;line-height: 30px;text-align: center;}
      .like{margin-top:5px;width:60px;height: 20px;background-color: #9cf;line-height: 20px;text-align: center;}
      .msg{margin-top:5px;}
    }
    .right-info{margin-left:20px;display:flex;flex-direction: column;
      .headline{font-size:20px;font-weight: bold;}
      .author-describe{display:flex;flex-direction: row;}
      .author-describe{
        .describe{margin:10px 0px 10px 10px;overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 2;line-clamp: 2;-webkit-box-orient: vertical;}
        .author{
          img{width:50px;height:50px;}
        }
      }
      .options{display:flex;flex-direction: row;
        .name{width: 50px;overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 1;line-clamp: 1;-webkit-box-orient: vertical;}
        .time{margin-left: 10px;}
        .comment{margin-left:20px;}
        .read{margin-left:10px;}
      }
    }
  }
</style>
