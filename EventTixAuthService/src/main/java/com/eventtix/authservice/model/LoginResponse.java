package com.eventtix.authservice.model;

import jdk.jfr.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private Role role;
   // private List<Event> events;


}