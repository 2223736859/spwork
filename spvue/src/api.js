import axios from 'axios';

// 创建Axios实例
const api = axios.create({
    baseURL: 'http://localhost:8090', // 设置后端接口的基础URL
    timeout: 5000, // 设置请求超时时间
});

// 导出Axios实例
export default api;
