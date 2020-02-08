<template>
  <div id="login-logo-box">
    <img :src="login_logo_path" alt="">
  </div>
</template>

<script>
export default {
  props:[
    'login_logo_path'
  ]
}
</script>

<style lang="scss" scoped>
  #login-logo-box{ width: 100%;height: 100%;
    img{width: 100%;height: 100%;}
  }
</style>
