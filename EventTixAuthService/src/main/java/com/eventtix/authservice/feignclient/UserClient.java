package com.eventtix.authservice.feignclient;
//import com.eventtix.userservice.model.User;

import com.eventtix.authservice.model.LoginRequest;
import com.eventtix.authservice.model.User;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "EventTixUserService", url = "http://localhost:8081")
public interface UserClient {
    @GetMapping("/users/email")
    User getUserByEmail(@RequestParam String email);

    @GetMapping("/users/validate")
    @Headers("C")
    User validateUser(@RequestBody LoginRequest request);

}
