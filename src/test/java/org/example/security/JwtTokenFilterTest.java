package org.example.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenFilterTest {

    private JwtTokenFilter jwtTokenFilter;
    private final String testSecret = "test-secret-key-1234567890-1234567890-123456";
    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        this.secretKey = Keys.hmacShaKeyFor(testSecret.getBytes());

        this.jwtTokenFilter = new JwtTokenFilter();
        this.jwtTokenFilter.setSecretKeyRaw(testSecret);
    }

    @Test
    void doFilter_ShouldSetAttributes_WhenValidToken() throws IOException, ServletException {
        String username = "testUser";
        UUID gameId = UUID.randomUUID();
        String validToken = Jwts.builder()
                .setSubject(username)
                .claim("gameId", gameId.toString())
                .signWith(secretKey)
                .compact();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + validToken);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        jwtTokenFilter.doFilter(request, response, filterChain);

        assertEquals(username, request.getAttribute("username"));
        assertEquals(gameId, request.getAttribute("gameId"));
        assertEquals(200, response.getStatus());
    }

    @Test
    void doFilter_ShouldReturn401_WhenMissingAuthorizationHeader() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        jwtTokenFilter.doFilter(request, response, filterChain);

        assertEquals(401, response.getStatus());
        assertTrue(Objects.requireNonNull(response.getErrorMessage()).contains("Missing or invalid Authorization header"));
    }
}