package com.eventtix.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizerResponse {
    private String token;
    private String email;
    private Role role;
    private Long organizerid;
}
