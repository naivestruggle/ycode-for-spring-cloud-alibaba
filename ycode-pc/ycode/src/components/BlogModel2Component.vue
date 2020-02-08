<template>
  <div id="blog-model-2-component">
    <div class="heading">
      最近程序员频繁被抓，如何避免面向监狱编程？
    </div>
    <div class="describe">
      最近，有关程序员因为参与某些项目开发导致被起诉，甚至被判刑的事件发生的比较多：某程序员因为接了个外包，
      最近，有关程序员因为参与某些项目开发导致被起诉，甚至被判刑的事件发生的比较多：某程序员因为接了个外包，
    </div>
    <div class="options">
      <div class="author">
        <router-link to="#" tag="a" class="none-a-no-hover icon-hover">
          <img src="http://www.indream96.com:10001/2.jpg" alt="">
        </router-link>
        <router-link to="#" tag="a" class="none-a-no-hover icon-hover">
          大 BUG
        </router-link>
      </div>
      <div class="info-operate">
        <a href="#" class="none-a-no-hover" >
          <span class="glyphicon glyphicon-thumbs-up icon-hover" aria-hidden="true"></span>
          <span class="number">281</span>
        </a>
        <router-link to="#" class="none-a-no-hover" tag="a" style="border-left:1px solid #aaaaaa;padding:0 5px 0 5px;">
          <span class="glyphicon glyphicon-eye-open icon-hover" aria-hidden="true"></span>
          <span class="number">42342</span>
        </router-link>
        <router-link to="#" class="none-a-no-hover" tag="a" style="border-left:1px solid #aaaaaa;padding:0 5px 0 5px;">
          <span class="glyphicon glyphicon-comment icon-hover" aria-hidden="true"></span>
          <span class="number">81</span>
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
  props:[

  ]
}
</script>

<style lang="scss" scoped>
  #blog-model-2-component{margin-top:20px;background-color: #fff;padding:20px;border-radius: 4px;box-shadow: 0px 0px 5px #aaa;
    .none-a-no-hover{text-decoration:none;color:#555;}
    .icon-hover:hover{color:#3399ea;}

    .heading{font-size:20px;font-weight: bold;}
    .describe{margin:10px 0 10px 0;width:100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
    .options{display:flex;flex-direction: row;justify-content:space-between;
      .author img{width:30px;height:30px;border-radius: 15px;}
      .info-operate{line-height: 30px;
        .number{color:#3399ea;}
      }
    }
  }
</style>
