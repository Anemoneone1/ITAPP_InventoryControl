package com.itapp.inventorycontrol.security;

import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${security_key}")
    private String SECRET_KEY;
    private final TokenRepository tokenRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 182))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isTokenValid(String token, User userDetails) {
        String username = extractUsername(token);
        Optional<Token> savedToken = tokenRepository.findByToken(token);
        if (savedToken.isEmpty()) return false;
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        Boolean expired = extractExpirationDate(token).before(new Date());
        if (expired) {
            deleteToken(token);
        }
        return expired;
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void saveToken(Long userId, String jwtToken) {
        Token token = new Token();
        token.setUserId(userId);
        token.setToken(jwtToken);
        tokenRepository.save(token);
    }

    public void deleteToken(String token) {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ICException(ICErrorType.IC_101));
        tokenRepository.delete(savedToken);
    }

    public void deleteAllTokensOfUser(Long userId) {
        tokenRepository.deleteAllByUserId(userId);
    }
}
