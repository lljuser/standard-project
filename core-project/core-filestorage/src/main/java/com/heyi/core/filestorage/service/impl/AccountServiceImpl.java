package com.heyi.core.filestorage.service.impl;

import com.heyi.core.common.CookieManager;
import com.heyi.core.filestorage.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String COOKIE_ACCOUNT = "account";

    @Override
    public void login(Map<String, Object> loginInfo, HttpServletResponse response) throws Exception {
        String username = (String) loginInfo.get("username");
        if (StringUtils.isEmpty(username.trim())) throw new Exception("用户名（IP）不能为空");

        Cookie cookie = CookieManager.getCookie(COOKIE_ACCOUNT, username, 1000 * 60 * 60 * 24);
        response.addCookie(cookie);
    }

    @Override
    public void logout(HttpServletResponse response) {
        Cookie cookie = CookieManager.getCookie(COOKIE_ACCOUNT, "", 0);
        response.addCookie(cookie);
    }
}
