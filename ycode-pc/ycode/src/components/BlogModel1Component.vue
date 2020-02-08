<template>
  <div id="blog-model-1-component">
    <div class="left-img my-box-radius">
      <img class="my-box-radius" src="http://www.indream96.com:10001/2.jpg" alt="">
    </div>
    <div class="right-box">
      <div class="heading">
        <router-link to="#" tag="a" class="none-a">
          每个强者都会有背后的辛酸挫折1
        </router-link>
      </div>
      <div class="describe">
        Windows 10将会让搜索和使用信息更加简单，包括本地、网络和互联网搜索功能,直观的用户体验将更 加高级,还会整合自动化应用程序提交和交叉程序数据透明性。
        Windows 10 操作系统改进了基于角色的计算方案和用户账户管理,在数据保护和坚固协作的固有冲突之
        Windows 10将会让搜索和使用信息更加简单，包括本地、网络和互联网搜索功能,直观的用户体验将更 加高级,还会整合自动化应用程序提交和交叉程序数据透明性。
        Windows 10 操作系统改进了基于角色的计算方案和用户账户管理,在数据保护和坚固协作的固有冲突之
      </div>
      <div class="options">
        <div class="time">
          发布时间：2019-01-15
        </div>
        <div class="name">
          <router-link to="#" tag="a" class="none-a">
            大 BUG
          </router-link>
        </div>
        <div class="classify">
          【<router-link to="#" tag="a" class="none-a">
          Python
        </router-link>】
        </div>
        <div class="select-more">
          <router-link to="#" tag="a">
            查看全文
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
  #blog-model-1-component{margin-top:20px;padding:10px;background-color:#000;display: flex;flex-direction: row;clear:both;border-radius: 4px;box-shadow: 0px 0px 5px #aaa;
      .left-img{width: 180px;height:110px;}
      .left-img{
        img{width: 180px;height:110px;}
      }
      .right-box{margin-left:10px;display: flex;flex-direction: column;color:#fff;
        .heading{font-size:18px;font-weight: bold;}
        .describe{margin:7px 0 7px 0;width:100%;height:54px;line-height:18px;overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 3;line-clamp: 3;-webkit-box-orient: vertical;color:#ddd;}
        .options{display: flex;flex-direction: row;}
        .options{
          .name{margin-left: 20px;}
          .time{color:#ddd;}
          .classify{margin-left: 20px;}
          .select-more{margin-left: 20px;}
        }
      }
  }
</style>
