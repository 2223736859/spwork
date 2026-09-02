import axios from 'axios';

// 全局 axios 配置
// 项目中组件均使用 import axios from 'axios'，即共享此全局实例
axios.defaults.baseURL = 'http://localhost:8090';
axios.defaults.timeout = 5000;

// 请求拦截器：自动在请求头中携带 JWT 令牌
axios.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = 'Bearer ' + token;
    }
    return config;
  },
  error => Promise.reject(error)
);

// 响应拦截器：遇到 401 未授权时清空登录态并跳回登录页
axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('currentUser');
      window.location.href = '/';
    }
    return Promise.reject(error);
  }
);

export default axios;
