package com.example.spwork.context;

/**
 * 当前登录用户上下文（基于 ThreadLocal）
 * 拦截器校验 Token 通过后写入 stuId，请求结束后清除
 * 业务层可通过 UserContext.getStuId() 获取当前登录用户，避免信任前端传参
 */
public class UserContext {

    private static final ThreadLocal<Integer> STU_ID = new ThreadLocal<>();

    public static void setStuId(Integer stuId) {
        STU_ID.set(stuId);
    }

    public static Integer getStuId() {
        return STU_ID.get();
    }

    public static void clear() {
        STU_ID.remove();
    }
}
