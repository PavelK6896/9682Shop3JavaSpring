package app.web.pavelk.shop3.config.jwt.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
