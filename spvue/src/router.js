import Vue from 'vue';
import VueRouter from 'vue-router';
// 导入需要跳转的组件
import MainPage from './components/MainPage';
import UserLogin from "@/components/UserLogin";
import MyCourse from "@/components/MyCourse";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'UserLogin',
        component: UserLogin,
        meta: { requiresAuth: false }
    },
    {
        path: '/MainPage',
        name: 'MainPage',
        component: MainPage,
        meta: { requiresAuth: true }
    },
    {
        path: '/MyCourse',
        name: 'MyCourse',
        component: MyCourse,
        meta: { requiresAuth: true }
    }
];

const router = new VueRouter({
    routes
});

// 全局路由守卫：未登录访问受保护页面时跳回登录页
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');
    if (to.meta.requiresAuth && !token) {
        next({ path: '/' });
    } else {
        next();
    }
});

export default router;
