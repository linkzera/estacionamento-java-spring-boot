package br.com.douglasvinicius.estacionamento.controllers;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglasvinicius.estacionamento.models.User;
import br.com.douglasvinicius.estacionamento.models.DTO.CreateUserDTO;
import br.com.douglasvinicius.estacionamento.models.DTO.LoginRequestDTO;
import br.com.douglasvinicius.estacionamento.models.Enum.UserRole;
import br.com.douglasvinicius.estacionamento.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtEncoder jwtEncoder;
    @Autowired
    BCryptPasswordEncoder passwordEconder;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        var user = userRepository.findByEmail(loginRequestDTO.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequestDTO, passwordEconder)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciais inválidas"));
        }

        var now = Instant.now();
        var expiresAt = 6000L;

        var scopes = user.get().getRole().toString();

        var claims = JwtClaimsSet.builder()
                .subject(user.get().getId().toString())
                .expiresAt(now.plusSeconds(expiresAt))
                .issuedAt(now)
                .claim("scope", scopes)
                .build();

        var token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token, "expiresAt", expiresAt));
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Credenciais inválidas"));
        }

        var user = new User();
        BeanUtils.copyProperties(createUserDTO, user);
        user.setPassword(passwordEconder.encode(createUserDTO.password()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Object> findAll(JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));
        if (user.isEmpty() || !user.get().getRole().equals(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Acesso negado"));
        }

        var users = userRepository.findAll();
              
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("me")
    public ResponseEntity<Object> me(JwtAuthenticationToken token) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(UUID.fromString(token.getName())));
    }

}
