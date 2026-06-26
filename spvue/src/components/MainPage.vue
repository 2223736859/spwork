<template>
  <div>
    <h1>选课界面</h1>
    <div v-if="courses.length === 0">暂无可选课程</div>

    <div v-if="currentUser">
      <h2>当前登录用户信息：</h2>
      <p>用户名：{{ currentUser.stuName }}</p>
      <p>学号：{{ currentUser.stuNo }}</p>
    </div>
    <el-button @click="goToMyCourse">查看我的选课</el-button>

    <el-button @click="showProfileDialog">查看个人信息</el-button>

    <el-button @click="showSearchDialog">查询课程</el-button>

    <el-table :data="courses" :header-cell-style="{background: '#f2f5fc'}" border>
      <el-table-column prop="name" label="课程名称" width="120"></el-table-column>
      <el-table-column prop="teacher" label="授课老师" width="120"></el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="selectCourse(scope.row.courseId,currentUser.stuId)">选课</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="个人信息" :visible="profileDialogVisible" @close="profileDialogVisible = false">
      <el-form :model="profileForm" label-width="80px">
<!--        <el-form-item label="ID">-->
<!--          <el-input v-model="profileForm.stuId" :disabled="!isEditing"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="用户名">
          <el-input v-model="profileForm.stuName" :disabled="!isEditing"></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="profileForm.stuNo" :disabled="!isEditing"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="profileForm.stuPsd" :disabled="!isEditing"></el-input>
        </el-form-item>
        <!-- 添加其他个人信息字段的显示和编辑输入框 -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelEdit">取消</el-button>
        <el-button v-if="!isEditing" type="primary" @click="startEdit">编辑</el-button>
        <el-button v-else type="primary" @click="saveProfile">保存</el-button>
      </div>
    </el-dialog>
    <el-dialog title="查询课程" :visible="searchDialogVisible" @close="searchDialogVisible = false">
      <el-input v-model="searchText.name" placeholder="请输入课程名称" clearable></el-input>
      <el-input v-model="searchText.teacher" placeholder="请输入教师姓名" clearable></el-input>
      <el-button type="primary" @click="searchCourses">搜索</el-button>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
import router from "@/router";

export default {
  name: 'MainPage',
  data() {
    return {
      courses: [],
      currentUser: null,
      profileForm: {
        stuName: '',
        stuNo: '',
        stuId:'',
        stuPsd:''
      },
      profileDialogVisible: false,
      isEditing: false,
      searchDialogVisible: false,
      searchText: {
        name: '',
        teacher: '',
      },
    };
  },
  mounted() {
    this.fetchCourses();
    this.fetchCurrentUser();
  },
  methods: {
    fetchCourses() {
      axios.get('http://localhost:8090/course/list')
          .then(response => {
            const result = response.data;
            if (result.code === 200) {
              this.courses = result.data;
            } else {
              console.error(result.msg);
            }
          })
          .catch(error => {
            console.error(error);
          });
    },
    fetchCurrentUser() {
      const currentUser = JSON.parse(localStorage.getItem('currentUser'));
      if (currentUser) {
        this.currentUser = currentUser;
        this.profileForm.stuName = currentUser.stuName;
        this.profileForm.stuNo = currentUser.stuNo;
        this.profileForm.stuId = currentUser.stuId;
        this.profileForm.stuPsd = currentUser.stuPsd;
      }
    },
    selectCourse(courseId, stuId) {
      console.log(courseId);
      console.log(stuId);
      axios.post('http://localhost:8090/courseselection/insert', { courseId, stuId })
          .then(response => {
            console.log(response.data);
            if (response.data.code === 200) {
              this.$message.success('选课成功');
            } else if (response.data.code === 400) {
              this.$message.error(response.data.msg);
            } else {
              this.$message.error('选课失败，请重试');
            }
          })
          .catch(error => {
            console.error(error);
            this.$message.error('选课失败，请重试');
          });
    },
    goToMyCourse() {
      router.push('/MyCourse');
    },
    showProfileDialog() {
      this.profileDialogVisible = true;
    },
    startEdit() {
      this.isEditing = true;
    },
    cancelEdit() {
      this.isEditing = false;
      // 重新加载当前用户的信息
      this.fetchCurrentUser();
    },
    saveProfile() {
      // 发送更新个人信息的请求
      axios.post('http://localhost:8090/student/update', this.profileForm)
          .then(response => {
            const result = response.data;
            if (result.code === 200) {
              this.$message.success('个人信息保存成功');
              this.isEditing = false;
              // 更新当前用户信息
              this.currentUser.stuName = this.profileForm.stuName;
              this.currentUser.stuNo = this.profileForm.stuNo;
              // 关闭弹窗
              this.profileDialogVisible = false;
            } else {
              this.$message.error('个人信息保存失败，请重试');
            }
          })
          .catch(error => {
            console.error(error);
            this.$message.error('个人信息保存失败，请重试');
          });
    },
    showSearchDialog() {
      this.searchDialogVisible = true;
    },

    searchCourses() {
      // 发送查询课程的请求
      axios.get('http://localhost:8090/course/search', {
        params: {
          name: this.searchText.name,
          teacher: this.searchText.teacher,
        },
      })
          .then(response => {
            const result = response.data;
            if (result.code === 200) {
              this.courses = result.data;
            } else {
              console.error(result.msg);
            }
          })
          .catch(error => {
            console.error(error);
          });
      // 关闭弹窗
      this.searchDialogVisible = false;
      // 清空搜索文本
      this.searchText = {
        name: '',
        teacher: '',
      };
    },
  },


};
</script>

<style>

</style>
