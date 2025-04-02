package com.franchise.franchises.backend.controller;

import com.franchise.franchises.backend.model.User;
import com.franchise.franchises.backend.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Clave secreta para JWT
    private static final String SECRET_KEY = "d7D3e2L9M0nP8wA5F6zG7xK1Q4vB2cY8d7D3e2L9M0nP8wA5F6zG7xK1Q4vB2cY8!!";
    private static final Key signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    @PostMapping("/login")
    public String login(@RequestBody User loginUser) {
        logger.info("Intento de inicio de sesión para usuario: {}", loginUser.getUsername());

        User user = userService.getUserByUsername(loginUser.getUsername())
                .orElseThrow(() -> {
                    logger.warn("Usuario no encontrado: {}", loginUser.getUsername());
                    return new RuntimeException("User not found");
                });

        if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            logger.warn("Contraseña incorrecta para usuario: {}", loginUser.getUsername());
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = generateJwtToken(user.getUsername());
        logger.info("Token generado correctamente para usuario: {}", user.getUsername());
        return token;
    }

    // Método para generar JWT
    private String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expira en 1 día
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
