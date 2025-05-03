package com.example.hellowebflux.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.hellowebflux.domain.User;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class UserRepositoryTest {
  private final UserRepository userRepository = new UserRepositoryImpl();

  @Test
  void save() {
    String name = "tester";
    String email = "tester@gmail.com";

    StepVerifier.create(userRepository.save(of(name, email)))
        .assertNext(
            u -> {
              assertThat(u.getId()).isEqualTo(1L);
              assertThat(u.getName()).isEqualTo(name);
              assertThat(u.getEmail()).isEqualTo(email);
            })
        .verifyComplete();
  }

  @Test
  void findAll() {
    userRepository.save(of("tester1", "tester1@gmail.com"));
    userRepository.save(of("tester2", "tester2@gmail.com"));
    userRepository.save(of("tester3", "tester3@gmail.com"));

    StepVerifier.create(userRepository.findAll()).expectNextCount(3L).verifyComplete();
  }

  @Test
  void findById() {
    userRepository.save(of("tester1", "tester1@gmail.com"));
    userRepository.save(of("tester2", "tester2@gmail.com"));

    StepVerifier.create(userRepository.findById(1L))
        .assertNext(
            u -> {
              assertThat(u.getId()).isEqualTo(1L);
              assertThat(u.getName()).isEqualTo("tester1");
              assertThat(u.getEmail()).isEqualTo("tester1@gmail.com");
            })
        .verifyComplete();
  }

  @Test
  void deleteById() {
    userRepository.save(of("tester1", "tester1@gmail.com"));
    userRepository.save(of("tester2", "tester2@gmail.com"));

    StepVerifier.create(userRepository.deleteById(2L)).expectNext(1).verifyComplete();
  }

  private static User of(String name, String email) {
    return User.builder().name(name).email(email).build();
  }
}
