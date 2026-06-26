<template>
  <div>
    <div v-if="currentUser">
      <h2>当前登录用户信息：</h2>
      <p>用户名：{{ currentUser.stuName }}</p>
      <p>学号：{{ currentUser.stuNo }}</p>
    </div>
    <el-table :data="coursesselcet" :header-cell-style="{ background: '#f2f5fc' }" border>
      <el-table-column prop="name" label="课程名称" width="120"></el-table-column>
      <el-table-column prop="teacher" label="授课老师" width="120"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click="cancelCourse(scope.row.courseId, currentUser.stuId)" size="small" type="danger">退课</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";

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
    fetchCourses() {
      const stuId = this.currentUser.stuId;
      const url = `http://localhost:8090/courseselection/list?selectId=${stuId}`;

      axios.get(url)
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
    cancelCourse(courseId, stuId) {
      console.log(courseId);
      console.log(stuId);
      const url = `http://localhost:8090/courseselection/cancel/${courseId}?stuId=${stuId}`;

      axios.delete(url)
          .then(response => {
            const result = response.data;
            if (result.code === 200) {
              const index = this.coursesselcet.findIndex(c => c.id === courseId);
              if (index !== -1) {
                this.coursesselcet.splice(index, 1);
              }
              console.log("退课成功");
              this.fetchCourses();
            } else {
              console.error(result.msg);
            }
          })
          .catch(error => {
            console.error(error);
          });
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
