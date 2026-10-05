package com.ms.credit.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserManagementClient {

    @Autowired
    private WebClient webClient;

    private final String USER_SERVICE_URL = "http://user-management-service/users";


    public Mono<String> getUserDetails(int userId) {
        return webClient.get()
                        .uri(USER_SERVICE_URL + "/{userId}", userId)
                        .retrieve()
                        .bodyToMono(String.class);
    }
}
