package br.com.douglasvinicius.estacionamento.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.douglasvinicius.estacionamento.models.User;
import br.com.douglasvinicius.estacionamento.models.Enum.UserRole;
import br.com.douglasvinicius.estacionamento.repository.UserRepository;
import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public AdminUserConfig(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    var admin = userRepository.findByEmail("admin@admin.com");
    admin.ifPresentOrElse(
        user -> {
          System.out.println("Usuario já existe");
        },
        () -> {
          var user = new User();
          user.setEmail("admin@admin.com");
          user.setName("Admin");
          user.setPassword(passwordEncoder.encode("admin"));
          user.setRole(UserRole.ADMIN);
          userRepository.save(user);
        });

  }

}
