package com.digital_guru.hackathonbackend.security;

import com.digital_guru.hackathonbackend.exception.AuthException;
import com.digital_guru.hackathonbackend.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

public class JwtHandler {

    private final String secret;

    public JwtHandler(String secret) {
        this.secret = secret;
    }

    public Mono<VerificationResult> check(String accessToken) {
        return Mono.just(verify(accessToken))
                .onErrorResume(e -> Mono.error(new UnauthorizedException(e.getMessage())));
    }

    private VerificationResult verify(String token) {
        Claims claims = getClaimsFromToken(token);
        final Date expirationDate = claims.getExpiration();
        if (expirationDate.before(new Date(Instant.now().toEpochMilli()))) {
            throw new AuthException("Token expired", "TOKEN_EXPIRE");
        }
        return new VerificationResult(claims, token);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8)))
                .parseClaimsJws(token)
                .getBody();
    }

    public static class VerificationResult {
        public Claims claims;
        public String token;

        public VerificationResult(Claims claims, String token) {
            this.claims = claims;
            this.token = token;
        }
    }
}
