<template>
  <div id="recommend-1-component">
    <div class="panel panel-default my-box-shadow">
      <div class="panel-heading">
        <h3 class="panel-title">{{ recommend_1_component_name }}</h3>
        <router-link class="recent-closer-resource-more-link" :to="recommend_1_component_moreUri" tag="a" title="点击查看更多">更多 ></router-link>
      </div>
      <div class="panel-body">
        <div class="content-item" v-for="item in recommend_1_component_conentList" :key="item.id">
          <div class="content-item-title" :title="item.title">
            <router-link to="#" tag="a">
              {{ item.title }}
            </router-link>
          </div>
          <div class="content-item-time">
            <div>
              {{ item.dateStr }}
            </div>
          </div>
        </div>
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
    'recommend_1_component_name',
    'recommend_1_component_moreUri',
    'recommend_1_component_conentList'
  ]
}
</script>

<style lang="scss" scoped>
  #recommend-1-component{
    .my-box-shadow{box-shadow: 0px 0px 5px #aaa;}
    .panel{border:0px;}
    .top{margin-top: 20px;}
    .panel{width:100%;float:left;}
    .panel-heading{display: flex;flex-direction: row;justify-content: space-between;}
    .content-item-title{width: 70%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
    .content-item{display:flex;flex-direction: row;border-bottom:1px dashed #ccc;padding:5px 0 5px 0;}
    .content-item-time{width:30%;}
    .content-item-time div{float: right;width: auto;}
  }
</style>
