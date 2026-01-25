package com.subscription.service.client;

import com.subscription.service.models.response.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    @Value("${user.service.base-url}") //TODO:: Burayı daha generic yönetilebilir hale getir. Mesela her ortamın ayrı app-prop'u olmalı ve url ona göre belirlenmeli.
    private String baseUrl;

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserResponse getUserById(Long userId){
        String url = baseUrl + "users/" + userId;
        return restTemplate.getForObject(url, UserResponse.class);
    }
}
