package com.heyi.core.filestorage.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface AccountService {

    void login(Map<String, Object> loginInfo, HttpServletResponse response) throws Exception;

    void logout(HttpServletResponse response);
}
