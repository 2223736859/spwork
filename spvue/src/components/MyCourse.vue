<template>
  <div>
    <div v-if="currentUser">
      <h2>当前登录用户信息：</h2>
      <p>用户名：{{ currentUser.stuName }}</p>
      <p>学号：{{ currentUser.stuNo }}</p>
    </div>
    <el-button @click="goBack">返回选课</el-button>
    <el-table :data="coursesselcet" :header-cell-style="{ background: '#f2f5fc' }" border>
      <el-table-column prop="name" label="课程名称" width="120"></el-table-column>
      <el-table-column prop="teacher" label="授课老师" width="120"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <!-- stuId 不再前端传参，由后端从 JWT Token 中获取 -->
          <el-button @click="cancelCourse(scope.row.courseId)" size="small" type="danger">退课</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
  name: "MyCourse",
  data() {
    return {
      coursesselcet: []
    };
  },
  mounted() {
    if (this.currentUser) {
      this.fetchCourses();
    }
  },
  methods: {
    // 查询已选课程：stuId 由后端从 JWT Token 中获取，前端不传
    fetchCourses() {
      axios.get('http://localhost:8090/courseselection/list')
          .then(response => {
            const result = response.data;
            if (result.code === 200) {
              this.coursesselcet = result.data;
            } else {
              console.error(result.msg);
            }
          })
          .catch(error => {
            console.error(error);
          });
    },
    // 退课：只传 courseId，stuId 由后端从 JWT Token 中获取
    cancelCourse(courseId) {
      const url = `http://localhost:8090/courseselection/cancel/${courseId}`;

      axios.delete(url)
          .then(response => {
            const result = response.data;
            if (result.code === 200) {
              this.$message.success("退课成功");
              this.fetchCourses();
            } else {
              this.$message.error(result.msg || "退课失败");
            }
          })
          .catch(error => {
            console.error(error);
            this.$message.error("退课失败，请重试");
          });
    },
    goBack() {
      router.push('/MainPage');
    }
  },
  computed: {
    currentUser() {
      return JSON.parse(localStorage.getItem("currentUser"));
    }
  }
};
</script>

<style scoped></style>
