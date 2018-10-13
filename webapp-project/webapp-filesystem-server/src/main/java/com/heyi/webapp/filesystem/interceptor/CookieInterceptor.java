package com.heyi.webapp.filesystem.interceptor;

import com.heyi.core.common.CookieManager;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        if (ObjectUtils.isEmpty(cookies)) {
            return true;
        }
        String account = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("account")) {
                account = cookie.getValue();
                break;
            }
        }
        ThreadLocal<String> threadLocalAccount = new ThreadLocal<>();
        threadLocalAccount.set(account);
        CookieManager.getInstance().setAccount(threadLocalAccount);
        return true;
    }
}
