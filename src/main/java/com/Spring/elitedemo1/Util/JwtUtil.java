    package com.Spring.elitedemo1.Util;
    import java.util.Date;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.stereotype.Component;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;

    @Component
    public class JwtUtil
    {

        private static final byte[] KEY =
                "mysecretkeymysecretkeymysecretkey123".getBytes();

        public String generateToken(String email, String Luserid) {
            return Jwts.builder()
                    .setSubject(email)                // ✅ email for Spring Security
                    .claim("userId", Luserid)         // ✅ userId as extra claim
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 min
                    .signWith(Keys.hmacShaKeyFor(KEY), SignatureAlgorithm.HS256)
                    .compact();


        }
        // ✅ extract EMAIL
        public String extractEmail(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }

        // ✅ extract USER ID
        // ✅ FIXED
        public String extractUserId(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userId", String.class);
        }

        // ✅ ADD THIS METHOD
        public boolean isTokenValid(String token) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(KEY))
                        .build()
                        .parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
