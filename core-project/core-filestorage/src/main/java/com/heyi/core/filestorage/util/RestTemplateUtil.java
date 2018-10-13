package com.heyi.core.filestorage.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RestTemplateUtil
 * @Description: TODO
 * @Author: lidong
 * @CreateDate: 2018/8/30$ 1:56 PM$
 * @UpdateUser: lidong
 * @UpdateDate: 2018/8/30$ 1:56 PM$
 * @UpdateRemark: TODO
 * @Version: 1.0
 **/
public class RestTemplateUtil {

    /**
     * 没有参数
     *
     * @param url
     * @return
     */
    public static Object get(String url) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            System.out.println("Exception = " + e.getMessage());
            return null;
        }
    }

    /**
     * 带有参数
     *
     * @param url
     * @param map
     * @return
     */
    public static Object get(String url, Map map) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, Object.class, map);
        } catch (Exception e) {
            System.out.println("Exception = " + e.getMessage());
            return null;
        }
    }

    /**
     * 带有参数
     *
     * @param url
     * @param map
     * @return
     */
    public static Object get(String url, Object map) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, Object.class, MapUtil.object2Map(map));
        } catch (Exception e) {
            System.out.println("Exception = " + e.getMessage());
            return null;
        }
    }

    public static <T> ResponseEntity<T> get(String url, Map map, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        if (map == null) map = new HashMap();
        try {
            return restTemplate.getForEntity(url, clazz, map);
        } catch (Exception e) {
            System.out.println("Exception = " + e.getMessage());
            return null;
        }
    }

    /**
     * 没有参数
     *
     * @param url
     * @return
     */
    public static ResponseEntity post(String url) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, headers);
            return restTemplate.postForEntity(url, httpEntity, Object.class);
        } catch (Exception e) {
            System.out.println("Exception = " + e.getMessage());
            return null;
        }
    }

    /**
     * 带有参数
     *
     * @param url
     * @param map
     * @return
     */
    public static ResponseEntity post(String url, Object map) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(MapUtil.object2Map(map), headers);
            return restTemplate.postForEntity(url, httpEntity, Object.class);
        } catch (Exception e) {
            System.out.println("Exception = " + e.getMessage());
            return null;
        }
    }

    /**
     * 带有参数
     *
     * @param url
     * @param data
     * @return
     */
    public static ResponseEntity post(String url, Map data) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(data, headers);
            return restTemplate.postForEntity(url, httpEntity, Object.class);
        } catch (Exception e) {
            System.out.println("Exception = " + e.getMessage());
            return null;
        }
    }
}
