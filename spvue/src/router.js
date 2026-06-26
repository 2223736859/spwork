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
        component: UserLogin
    },
    {
        path: '/MainPage',
        name: 'MainPage',
        component: MainPage
    },
    {
        path: '/MyCourse',
        name: 'MyCourse',
        component: MyCourse
    }
];

const router = new VueRouter({
    routes
});

export default router;