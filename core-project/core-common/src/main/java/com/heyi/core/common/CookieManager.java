package com.heyi.core.common;

import javax.servlet.http.Cookie;

public class CookieManager {

    private static CookieManager instance;

    private ThreadLocal<String> account;

    public static CookieManager getInstance() {
        if (instance == null) {
            instance = new CookieManager();
        }
        return instance;
    }

    public static Cookie getCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    public void setAccount(ThreadLocal<String> account) {
        this.account = account;
    }

    public ThreadLocal<String> getAccount() {
        return account;
    }
}
