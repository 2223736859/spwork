**已实现功能**

1. **注册 / 登录**：按学号 + 密码登录（登录后用户信息存 localStorage）
2. **课程列表**：展示全部课程
3. **选课**：带 "重复选课" 校验，重复选报会报错
4. **模糊查询课程**：按课程名 / 教师名模糊搜索（LIKE）
5. **我的选课**：通过 courseselection 联表 JOIN 查询个人已选课程
6. **退课**：删除选课记录
7. **个人信息修改**：可编辑姓名、学号、密码
8. **用BCrypt加密密码**：不再使用明文密码
- `注册`：`encode()` 加密后再入库
- `登录`：改用 `passwordEncoder.matches()` 校验
- `改密`：只有用户传入新密码才加密更新，否则保留原密码
9. **JWT登录验证**：
- `JwtUtil`：令牌生成 / 解析 / 校验，密钥和有效期在 `application.yml` 中定义
- `JwtInterceptor`：拦截所有请求，从 `Authorization: Bearer <token>` 取令牌校验，通过后把 `stuId` 写入 `UserContext`（ThreadLocal），失败返回 401；放行 OPTIONS 预检
- `UserContext`：ThreadLocal 持有当前登录用户 ID，请求结束自动清除
- `WebConfig`：统一 CORS 配置（替代原来分散在各 Controller 的 `@CrossOrigin`）+ 注册拦截器，放行 `/student/login`、`/student/insert`（防止反复生成token）
- `LoginController.login`：校验密码后签发 JWT，返回 `LoginVo(token, stuId, stuNo, stuName)`
- `api.js`：全局 axios 请求拦截器自动携带 Token，响应拦截器遇 401 清登录态跳回登录页
- `router.js`：全局路由守卫，未登录访问 `/MainPage`、`/MyCourse` 自动跳登录
- `UserLogin.vue`：登录成功后分别存 `token` 和用户信息（不存密码）
- `main.js`：引入 `./api` 使全局 axios 配置生效