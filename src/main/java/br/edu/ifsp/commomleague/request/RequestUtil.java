package br.edu.ifsp.commomleague.request;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Component
public class RequestUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    public HttpDTO get(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(
            url, String.class);
        return createHttpDTO(response);
    }

    public HttpDTO post(String url, Object body) {
        String jsonBody = gson.toJson(body);
        ResponseEntity<String> response = restTemplate.postForEntity(
            url, jsonBody, String.class);
        return createHttpDTO(response);
    }

    public HttpDTO put(String url, Object body) {
        String jsonBody = gson.toJson(body);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody);
        ResponseEntity<String> response = restTemplate.exchange(
            url, HttpMethod.PUT, requestEntity, String.class);
        return createHttpDTO(response);
    }

    public HttpDTO delete(String url) {
        ResponseEntity<String> response = restTemplate.exchange(
            url, HttpMethod.DELETE, null, String.class);
        return createHttpDTO(response);
    }

    private HttpDTO createHttpDTO(ResponseEntity<String> response) {
        return new HttpDTO(
            response.getBody(),
            response.getStatusCode()
        );
    }

}