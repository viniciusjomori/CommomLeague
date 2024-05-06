package br.edu.ifsp.commomleague.request;

import java.io.IOException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Configuration
public class RequestConfig {
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
            .errorHandler(responseErrorHandler())
            .defaultHeader("Content-Type", "application/json; charset=utf-8")
            .build();
        return restTemplate;
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
    
    public ResponseErrorHandler responseErrorHandler() {
        return new ResponseErrorHandler() {
            
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {}
            
        };
    }
}
