package br.com.zal.oauth2resourceserver.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AuthResponse {

    private String token;
    private Date expiration;
}
