package com.example.hellowebflux.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PostClient {
  private final WebClient webClient;
  private final String url = "http://localhost:9090/posts/%d";

  public Mono<PostResponse> getPost(Long id) {
    return webClient
        .get()
        .uri(url.formatted(id))
        .retrieve()
        .bodyToMono(PostResponse.class);
  }
}
