package com.subscription.service.client;

import com.subscription.service.models.response.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    @Value("${user.service.base-url}")
    private String baseUrl;

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserResponse getUserById(Long userId){
        String url = baseUrl + "api/v1/users" + userId;
        return restTemplate.getForObject(url, UserResponse.class);
    }
}
