package com.project.schoolmanagment.payload.response.user;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private String username;
    private String ssn;
    private String role;
    private String token;
    private String name;
    private String isAdvisory;
}
