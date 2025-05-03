package com.example.hellowebflux.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.hellowebflux.controller.dto.UserCreateRequest;
import com.example.hellowebflux.controller.dto.UserResponse;
import com.example.hellowebflux.controller.dto.UserUpdateRequest;
import com.example.hellowebflux.domain.User;
import com.example.hellowebflux.service.UserService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(UserController.class)
class UserControllerTest {
  @Autowired private WebTestClient webTestClient;

  @MockitoBean private UserService userService;

  @Test
  void create() {
    String name = "tester";
    String email = "tester@gmail.com";

    when(userService.create(name, email))
        .thenReturn(Mono.just(new User(1L, name, email, LocalDateTime.now(), LocalDateTime.now())));

    webTestClient
        .post()
        .uri("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateRequest(name, email))
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(UserResponse.class)
        .value(
            response -> {
              assertThat(response.getName()).isEqualTo(name);
              assertThat(response.getEmail()).isEqualTo(email);
            });
  }

  @Test
  void findAll() {
    LocalDateTime now = LocalDateTime.now();
    when(userService.findAll())
        .thenReturn(
            Flux.just(
                new User(1L, "tester1", "tester1@gmail.com", now, now),
                new User(2L, "tester2", "tester2@gmail.com", now, now),
                new User(3L, "tester3", "tester3@gmail.com", now, now)));

    webTestClient
        .get()
        .uri("/users")
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBodyList(UserResponse.class)
        .hasSize(3);
  }

  @Test
  void findById() {
    String name = "tester";
    String email = "tester@gmail.com";

    when(userService.findById(1L))
        .thenReturn(Mono.just(new User(1L, name, email, LocalDateTime.now(), LocalDateTime.now())));

    webTestClient
        .get()
        .uri("/users/1")
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(UserResponse.class)
        .value(
            response -> {
              assertThat(response.getName()).isEqualTo(name);
              assertThat(response.getEmail()).isEqualTo(email);
            });
  }

  @Test
  void notFoundUser() {
    when(userService.findById(1L)).thenReturn(Mono.empty());

    webTestClient.get().uri("/users/1").exchange().expectStatus().is4xxClientError();
  }

  @Test
  void delete() {
    when(userService.deleteById(1L)).thenReturn(Mono.just(1));

    webTestClient.delete().uri("/users/1").exchange().expectStatus().is2xxSuccessful();
  }

  @Test
  void update() {
    String name = "tester";
    String email = "tester@gmail.com";

    when(userService.update(1L, name, email))
        .thenReturn(Mono.just(new User(1L, name, email, LocalDateTime.now(), LocalDateTime.now())));

    webTestClient
        .put()
        .uri("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserUpdateRequest(name, email))
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(UserResponse.class)
        .value(
            response -> {
              assertThat(response.getName()).isEqualTo(name);
              assertThat(response.getEmail()).isEqualTo(email);
            });
  }
}
