<template>
  <div>
    <h1>学生登录</h1>
    <form @submit.prevent="login">
      <div>
        <label for="username">学号:</label>
        <input type="text" id="username" v-model="stuNo" required>
      </div>
      <div>
        <label for="password">密码:</label>
        <input type="password" id="password" v-model="stuPsd" required>
      </div>
      <button @click="showRegisterModal">注册</button>
      <button type="submit">登录</button>
    </form>

    <div v-if="isRegisterModalVisible" class="modal">
      <div class="modal-content">
        <h2>注册</h2>
        <div>
          <label for="register-name">姓名:</label>
          <input type="text" id="register-name" v-model="registerName" required>
        </div>
        <div>
          <label for="register-stuNo">学号:</label>
          <input type="text" id="register-stuNo" v-model="registerStuNo" required>
        </div>
        <div>
          <label for="register-password">密码:</label>
          <input type="password" id="register-password" v-model="registerPassword" required>
        </div>
        <button @click="register">注册</button>
        <button @click="hideRegisterModal">取消</button>
      </div>
    </div>
  </div>

</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      stuNo: '',
      stuPsd: '',
      isRegisterModalVisible: false,  // 控制模态框的显示/隐藏
      registerName: '',  // 注册时的姓名
      registerStuNo: '',  // 注册时的学号
      registerPassword: ''  // 注册时的密码
    };
  },
  methods: {
    showRegisterModal() {
      this.isRegisterModalVisible = true;
    },
    hideRegisterModal() {
      this.isRegisterModalVisible = false;
    },
    register() {
      // 构造请求数据
      const requestData = {
        stuName: this.registerName,
        stuNo: this.registerStuNo,
        stuPsd: this.registerPassword
      };

      axios.post('http://localhost:8090/student/insert', requestData)
          .then(response => {
            console.log(response.data);
            const result = response.data;
            if (result.code === 200) {
              // 注册成功，获取返回的学生信息
              const student = result.data;
              console.log('注册成功，学生信息：', student);
              this.$message.success('注册成功');
            } else {
              // 注册失败，显示错误提示
              console.error(result.msg);
              this.$message.error('注册失败');
            }
          })
          .catch(error => {
            // 处理注册失败的情况
            console.error(error);
          });

      this.hideRegisterModal();  // 注册完成后隐藏模态框
    },

    login() {
      // 构造请求数据
      const requestData = {
        stuNo: this.stuNo,
        stuPsd: this.stuPsd
      };
      axios.post('http://localhost:8090/student/login', requestData)
          .then(response => {
            const result = response.data;
            if (result.code === 200) {
              const data = result.data;
              // 保存 JWT 令牌（请求拦截器会自动携带到 Authorization 头）
              localStorage.setItem('token', data.token);
              // 保存用户基本信息用于页面展示（不存 token、不存密码）
              localStorage.setItem('currentUser', JSON.stringify({
                stuId: data.stuId,
                stuNo: data.stuNo,
                stuName: data.stuName
              }));
              console.log('登录成功，用户信息：', data);
              this.$message.success('登录成功');
              this.$router.push('/MainPage');
            } else {
              // 登录失败，显示错误提示
              console.error(result.msg);
              this.$message.error('账号或密码不正确');
            }
          })
          .catch(error => {
            // 处理登录失败的情况
            console.error(error);
          });
    }
  }
};
</script>

<style>
/* ... */
</style>
