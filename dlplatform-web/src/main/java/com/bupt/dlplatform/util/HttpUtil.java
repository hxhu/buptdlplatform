package com.bupt.dlplatform.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HttpUtil {
    @Autowired
    private RestTemplate restTemplate1;

    /**
     * POST请求-JSON参数
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public <T> T httpPostJson(String url, Map<String, Object> params, Map<String, String> headers, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach((o1, o2) -> httpHeaders.add(o1, o2));
        }
        // json方式传参
        return restTemplate1.postForObject(url, new HttpEntity(params, httpHeaders), clazz);
    }

    /**
     * POST请求-FROM参数
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public <T> T httpPostFrom(String url, Map<String, Object> params, Map<String, String> headers, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        params = params == null ? new LinkedHashMap<>() : params;
        if (headers != null) {
            headers.forEach((o1, o2) -> httpHeaders.add(o1, o2));
        }
        MultiValueMap<String, Object> stringObjectLinkedMultiValueMap = new LinkedMultiValueMap<>();
        params.forEach((o1, o2) -> stringObjectLinkedMultiValueMap.add(o1, o2));
        // 表单方式传参
        // json方式传参
        return restTemplate1.postForObject(url, new HttpEntity(stringObjectLinkedMultiValueMap, httpHeaders), clazz);
    }

    /**
     * GET请求-?号参数
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public <T> T httpGetTraditional(String url, Map<String, Object> params, Map<String, String> headers, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        params = params == null ? new LinkedHashMap<>() : params;
        if (headers != null) {
            headers.forEach((o1, o2) -> httpHeaders.add(o1, o2));
        }
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        params.forEach((o1, o2) -> sb.append(o1).append("=").append(o2).append("&"));
        url = sb.toString().replaceAll("&$+|\\?$+", "");
        ResponseEntity<T> exchange = restTemplate1.exchange(url, HttpMethod.GET, new HttpEntity(null, httpHeaders), clazz);
        return exchange.getBody();
    }

    /**
     * GET请求-分隔符参数
     *
     * @param url
     * @param params LinkedHashMap
     * @param headers
     * @return
     */
    public <T> T httpGetPlaceholder(String url, List<String> params, Map<String, String> headers, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        params = params == null ? new ArrayList<>() : params;
        if (headers != null) {
            headers.forEach((o1, o2) -> httpHeaders.add(o1, o2));
        }
        StringBuilder sb = new StringBuilder(url);
        params.forEach(o2 -> sb.append("/").append(o2));
        url = sb.toString().replaceAll("&$+|\\?$+", "");
        ResponseEntity<T> exchange = restTemplate1.exchange(url, HttpMethod.GET, new HttpEntity(null, httpHeaders), clazz);
        return exchange.getBody();
    }

    /**
     * DELETE请求-?号参数
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public <T> T httpDeleteTraditional(String url, Map<String, Object> params, Map<String, String> headers, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        params = params == null ? new LinkedHashMap<>() : params;
        if (headers != null) {
            headers.forEach((o1, o2) -> httpHeaders.add(o1, o2));
        }
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        params.forEach((o1, o2) -> sb.append(o1).append("=").append(o2).append("&"));
        url = sb.toString().replaceAll("&$+|\\?$+", "");
        ResponseEntity<T> exchange = restTemplate1.exchange(url, HttpMethod.DELETE, new HttpEntity(null, httpHeaders), clazz);
        return exchange.getBody();
    }

    /**
     * DELETE请求-分隔符参数
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public <T> T httpDeletePlaceholder(String url, List<String> params, Map<String, String> headers, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        params = params == null ? new ArrayList<>() : params;
        if (headers != null) {
            headers.forEach((o1, o2) -> httpHeaders.add(o1, o2));
        }
        StringBuilder sb = new StringBuilder(url);
        params.forEach(o2 -> sb.append("/").append(o2));
        url = sb.toString().replaceAll("&$+|\\?$+", "");
        ResponseEntity<T> exchange = restTemplate1.exchange(url, HttpMethod.DELETE, new HttpEntity(null, httpHeaders), clazz);
        return exchange.getBody();
    }
}
