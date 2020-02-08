<template>
  <div id="recycled-slide-show-component">

    <!--  标题 -->
    <div class="headline">
      轮播图回收站
    </div>

    <!--  列表查看 -->
    <div class="slide-show-info-list" v-if="!isEditPage">

      <!--  组合条件查找 输入框 -->
      <div class="params-condition-select-box">
        <!--  标题参数 -->
        标题：
        <div class="params-title-box">
          <el-input v-model="params_title_input" placeholder="请输入标题"></el-input>
        </div>

        <!--  链接参数 -->
        链接：
        <div class="params-url-box">
          <el-input v-model="params_url_input" placeholder="请输入链接"></el-input>
        </div>

        <!--  查询按钮 -->
        <div class="submit-box">
          <el-button type="primary" @click="params_select">查询</el-button>
          <el-button type="warning" @click="params_select_reset">重置</el-button>
          <el-button type="success" @click="params_select_recover">恢复选中</el-button>
          <el-button type="danger" @click="handleDeleteSelect">彻底删除选中</el-button>
        </div>
      </div>

      <!--  表格 -->
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">

        <el-table-column
          type="selection"
          width="55">
        </el-table-column>

        <el-table-column
          prop="date"
          label="日期"
          width="200">
          <template slot-scope="scope">{{ scope.row.date }}</template>
        </el-table-column>

        <el-table-column
          prop="title"
          label="标题"
          width="300">
        </el-table-column>

        <el-table-column
          prop="url"
          label="链接"
          show-overflow-tooltip>
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button
              size="mini"
              type="success"
              @click="handleRecover(scope.$index, scope.row)">恢复</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">彻底删除</el-button>
          </template>
        </el-table-column>

      </el-table>


      <!-- 分页按钮  -->
      <div class="page-show-select-box">
        <div class="page-show-select block">
          <el-pagination
            background
            layout="prev, pager, next"
            :page-size="slideShowPageSize"
            hide-on-single-page
            @current-change="setPageData"
            :total="slideShowTotal">
          </el-pagination>
        </div>
      </div>

    </div>

    <!--  单个查看 -->
    <div class="slide-show-info-single" v-if="isEditPage">
      <div class="return-button-box"> <!-- 返回按钮框  -->
        <el-button type="primary" icon="el-icon-arrow-left" round @click="isEditPage=false">返回</el-button>
      </div>
      <div class="input-info-box">  <!-- 信息输入框  -->
        <!-- 标题  -->
        <div class="title">
          <el-input placeholder="请输入标题" v-model="single_title_input" clearable>
            <template slot="prepend">标题：</template>
          </el-input>
        </div>

        <!-- 链接  -->
        <div class="url">
          <el-input placeholder="请输入轮播图链接" v-model="single_url_input" clearable>
            <template slot="prepend">链接：</template>
          </el-input>
        </div>

        <!-- 上传轮播图  -->
        <div class="image" onclick="slideshow.click()">
          <img :src="single_image_path" alt="" class="img-real" v-if="single_hasRealImage">
          <img :src="single_image_default_path" alt="" class="img-default" v-if="!single_hasRealImage">
          <input id="slideshow" type="file" ref="upload_file" @change="changeSlideshowFile" style="display: none;">
        </div>

        <!--  确认按钮 -->
        <div class="submit-button-box">
          <el-button type="success" @click="singleInfoSubmit">确认修改</el-button>
          <el-button type="warning" @click="singleInfoReset">全部重置</el-button>
        </div>
      </div>

    </div>

  </div>
</template>

<script>
  export default {
    data() {
      return {
        nowPage: 1,   //当前页
        slideShowTotal : 0,  // 记录总条数
        slideShowPageSize: 3, // 每页显示条数
        tableData: [],  // 表格中的数据
        multipleSelection: [], // 选中的数据
        params_title_input: '', // 查询参数  标题
        params_url_input: '',  // 查询参数   链接


        // 下面是 单页 编辑的信息
        isEditPage: false,   // 是否是编辑页面  编辑页面和列表页面是互相影藏的
        single_id: '',  // 当前编辑的id
        single_title_input: '',  //当前编辑的标题
        single_url_input: '', // 当前编辑的链接
        single_image_path: '',  //轮播图的路径
        single_image_default_path: 'http://192.168.31.10:40001/static-resources/upload_slideshow_default.png',
        single_hasRealImage: true,  // 是否有真实路径
      }
    },

    methods: {
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },

      /**
       * 请求 API 获取数据， 并将数据刷新到表格中
       * @param paramsObj 参数对象（js对象）
       */
      requestDataAndFlushTable(paramsObj){
        var parentThis = this;  // 将this 提出来，是怕后面各种嵌套将 this 污染了
        // 请求后端 API获取数据
        parentThis.$http.get("/admin_slideshow/slideshow",{
          params: paramsObj
        }).then(succeed => {
          // 如果返回的状态码是 200
          if(succeed.data.status === parentThis.$global.HTTP_STATUS_CODE__OK){
            var page = succeed.data.data;  // 查询到的分页对象
            parentThis.slideShowTotal = page.total; // 设置总记录数
            var arr = page.list;
            parentThis.tableData = []  // 将表格重置
            var len = arr.length;
            if(len === 0){  // 判断当前页有没有  没有数据  就回到前一页
              paramsObj.pageNum = paramsObj.pageNum - 1;
              this.requestDataAndFlushTable(paramsObj)
              return;
            }
            // 遍历 将数据 刷新到表格中
            for(var i=0; i<len; i++){
              var bean = arr[i]
              parentThis.tableData[i] = {id: bean.id, date: bean.update_time, title: bean.title, url: bean.url, image: bean.image}
            }
            this.nowPage = paramsObj.pageNum;
          }else{
            // 请求状态码 不是 200 但是请求成功了
            this.$message({type: 'error', message: succeed.data.message});
          }
        }).catch(error => {
          console.log(error.response)
          // 如果返回的状态码是  402  就跳转到未登录页面
          if(error.response.status === parentThis.$global.HTTP_STATUS_CODE__ADMIN_NOT_LOGIN){
            parentThis.$router.push("/login")
          }
        });
      },


      /**
       * 设置分页数据  可用于：页面初始化，分页查询等
       * @param selectPageNum 参数对象
       */
      setPageData(selectPageNum){
        var paramsObj = {
          pageNum: selectPageNum,
          pageSize: this.slideShowPageSize,
          status: '0',
        }
        this.requestDataAndFlushTable(paramsObj)
      },

      /**
       * 组合条件查询
       */
      params_select(){
        var paramsObj = {
          pageNum: 1,
          pageSize: this.slideShowPageSize,
          title: this.params_title_input,
          url: this.params_url_input,
          status: '0',
        }
        this.requestDataAndFlushTable(paramsObj)
      },

      // 查询条件重置
      params_select_reset(){
        this.params_title_input = '';
        this.params_url_input = '';
      },


      /**
       * 删除按钮
       * @param index 索引
       * @param content 内容
       */
      handleDelete(index, content){
        var _this = this;
        var title = content.title;
        var id = content.id;
        // 弹出选项
        _this.$confirm('是否彻底删除标题为【'+ title +'】的轮播图信息，彻底删除后无法找回?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var params = {
            id: id,
            status: '-1',
          }
          // 点击了确定按钮  要删除轮播图信息
          _this.$http.post("/admin_slideshow/slideshow", _this.$qs.stringify(params)).then(succeed => {
            var data = succeed.data;
            if(data.status === _this.$global.HTTP_STATUS_CODE__CREATED){
              // 删除成功
              this.setPageData(this.nowPage)
            }
          }).catch(error => {
            console.log(error)
          });

        }).catch(() => {
          //提示框
          _this.$message({type: 'info', message: '操作已取消'});
        });

      },

      /**
       * 删除选中
       */
      handleDeleteSelect(){
        var _this = this;
        var len = this.multipleSelection.length;
        if(len > 0){
          _this.$confirm('是否删除选中的 '+ len +' 条轮播图信息，删除后可以在回收站找回?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            var succeedCount = 0;
            for(var i=0; i<len; i++){
              var bean = this.multipleSelection[i];
              var params = {
                id: bean.id,
                status: '-1',
              }
              _this.$http.post("/admin_slideshow/slideshow", _this.$qs.stringify(params)).then(succeed => {
                var data = succeed.data;
                if(data.status === _this.$global.HTTP_STATUS_CODE__CREATED){
                  succeedCount += 1;
                  if(succeedCount === len){
                    // 删除成功
                    this.setPageData(this.nowPage)
                    _this.$notify({title: '成功', message: '彻底删除了 ' + len + ' 条轮播图', type: 'success'});
                  }
                }
              }).catch(error => {
                console.log(error)
              });
            }
          }).catch(() => {
            //提示框
            this.$notify({title: '消息', message: '操作已取消', type: 'info'});
          });

        }else{
          this.$notify({title: '警告', message: '你还没有选择', type: 'warning'});
        }
      },

      /**
       * 编辑
       * @param index 下标
       * @param content 内容
       */
      handleEdit(index, content){
        var _this = this;
        _this.single_id = content.id;
        _this.single_title_input = content.title;
        _this.single_url_input = content.url;
        _this.single_image_path = content.image;
        _this.isEditPage = true;
        _this.single_hasRealImage = true;
      },

      /**
       * 选中图片，要讲选中的图片上传到 fastDFS 中
       */
      changeSlideshowFile() {
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
          _this.single_image_path = data.data  // 得到上传成功，返回来的真实图片路径
        }).catch(error => {
          var response = error.response;
          var status = response.status;
          // 判断响应状态码 是否 为 未登录的状态码   this.$global.HTTP_STATUS_CODE__ADMIN_NOT_LOGIN ：是自定义的全局变量
          if (status === _this.$global.HTTP_STATUS_CODE__ADMIN_NOT_LOGIN) {
            // 弹出选项
            _this.$confirm('此操作需要登录(登录后无操作10分钟自动退出)，是否登录?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              _this.$router.push("/login")  // 跳转到登录页面
            }).catch(() => {
              //提示框
              _this.$message({type: 'info', message: '操作已取消'});
            })

          } else {
            //提示框   弹出其他错误
            _this.$message({type: 'error', message: response});
          }
        });
      },

      /**
       * 单页 编辑中的 信息全部重置按钮
       */
      singleInfoReset(){
        var _this = this;
        _this.single_title_input = '';
        _this.single_url_input = '';
        _this.single_image_path = '';
        _this.single_hasRealImage = false;
      },

      /**
       * 单页  信息修改
       */
      singleInfoSubmit(){
        var _this = this;
        var params = {  // 封装参数对象
          id: _this.single_id,   //ID
          image: _this.single_image_path,  // 图片的真实路径
          title: _this.single_title_input, // 轮播图标题
          url: _this.single_url_input, // 轮播图链接
        }
        this.$http.post("/admin_slideshow/slideshow", this.$qs.stringify(params)).then(response => {
          _this.$alert('修改成功', '标题名称', {confirmButtonText: '确定',
            callback: action => {
              _this.isEditPage = false;
              _this.setPageData(_this.nowPage);
            }
          });
        }).catch(error => {
          var response = error.response;
          var status = response.status;
          // 判断响应状态码 是否 为 未登录的状态码   this.$global.HTTP_STATUS_CODE__ADMIN_NOT_LOGIN ：是自定义的全局变量
          if(status === this.$global.HTTP_STATUS_CODE__ADMIN_NOT_LOGIN){
            _this.$router.push("/login")  // 跳转到登录页面
          }else{
            //提示框   弹出其他错误
            _this.$message({type: 'error', message: response});
          }
        });
      },


      /**
       * 恢复选中
       */
      params_select_recover(){
        var _this = this;
        var len = this.multipleSelection.length;
        if(len > 0){
          _this.$confirm('是否恢复选中的 '+ len +' 条轮播图信息?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            var succeedCount = 0;
            for(var i=0; i<len; i++){
              var bean = this.multipleSelection[i];
              var params = {
                id: bean.id,
                status: '1',
              }
              _this.$http.post("/admin_slideshow/slideshow", _this.$qs.stringify(params)).then(succeed => {
                var data = succeed.data;
                if(data.status === _this.$global.HTTP_STATUS_CODE__CREATED){
                  succeedCount += 1;
                  if(succeedCount === len){
                    // 删除成功
                    this.setPageData(this.nowPage)
                    _this.$notify({title: '成功', message: '恢复了 ' + len + ' 条轮播图', type: 'success'});
                  }
                }
              }).catch(error => {
                console.log(error)
              });
            }
          }).catch(() => {
            //提示框
            this.$notify({title: '消息', message: '操作已取消', type: 'info'});
          });

        }else{
          this.$notify({title: '警告', message: '你还没有选择', type: 'warning'});
        }
      },

      /**
       * 恢复单个
       * @param index 索引
       * @param content 内容
       */
      handleRecover(index, content){
        var _this = this;
        _this.$confirm('是否恢复标题为【'+ content.title +'】的轮播图信息?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var params = {
            id: content.id,
            status: '1',
          }
          _this.$http.post("/admin_slideshow/slideshow", _this.$qs.stringify(params)).then(succeed => {
            var data = succeed.data;
            if(data.status === _this.$global.HTTP_STATUS_CODE__CREATED){
              // 删除成功
              this.setPageData(this.nowPage)
              _this.$notify({title: '成功', message: '恢复了 ' + len + ' 条轮播图', type: 'success'});
            }
          }).catch(error => {
            console.log(error)
          });
        }).catch((error) => {
          console.log(error)
          //提示框
          this.$notify({title: '消息', message: '操作已取消', type: 'info'});
        });
      },

    },
    created() {
      // 当组件加载成功后  请求第一页的数据
      this.setPageData(this.nowPage)
    }
  }
</script>

<style lang="scss" scoped>
  #recycled-slide-show-component{

    /*标题*/
    .headline{width: 100%; height: 40px;font-size:20px;border-bottom:1px #409EFF solid;}

    /*列表显示页面*/
    .slide-show-info-list{margin-top:20px;
      /* 组合条件查询 */
      .params-condition-select-box{width:100%;height:60px;border-bottom: 1px #aaa solid;line-height: 60px;display: flex;flex-direction: row;
        .params-date-box{margin-right:30px;}
        .params-title-box{margin-right:30px;}
        .params-url-box{margin-right:30px;}
      }

      /* 分页 */
      .page-show-select-box{width: 100%;text-align: center;
        .page-show-select{margin:30px auto;}
      }
    }

    /*单个轮播图编辑页面*/
    .slide-show-info-single{
      /*返回按钮*/
      .return-button-box{width: 100%; height:60px; line-height: 60px; }
      /*信息输入框*/
      .input-info-box{width: 100%; text-align: center;
        .title{width:500px;margin: 20px auto;}
        .url{width:500px;margin: 20px auto;}
        .image{width:800px;height:320px;border:1px #888 dashed;cursor: pointer;text-align: center;line-height: 200px;border-radius: 10px;margin: 20px auto;
          .img-real{width: 100%; height: 100%;border-radius: 10px;}
          .img-default{width: 150px;height:150px;margin:90px auto;border-radius: 10px;}
        }
        .image:hover{background-color: #eee; border:1px blue dashed;}
      }
    }
  }
</style>
