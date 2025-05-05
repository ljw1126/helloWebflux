package com.example.hellowebflux.controller;

import com.example.hellowebflux.controller.dto.PostR2dbcCreateRequest;
import com.example.hellowebflux.controller.dto.PostR2dbcResponse;
import com.example.hellowebflux.service.PostR2dbcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostR2dbcController {
    private final PostR2dbcService postR2dbcService;

    @PostMapping
    public Mono<PostR2dbcResponse> create(@RequestBody PostR2dbcCreateRequest request) {
        return postR2dbcService.create(request.getUserId(), request.getTitle(), request.getContent())
                .map(PostR2dbcResponse::of);
    }

    @GetMapping
    public Flux<PostR2dbcResponse> findAll() {
        return postR2dbcService.findAll()
                .map(PostR2dbcResponse::of);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PostR2dbcResponse>> findById(@PathVariable Long id) {
        return postR2dbcService.findById(id)
                .map(p -> ResponseEntity.ok(PostR2dbcResponse.of(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> deleteById(@PathVariable Long id) {
        return postR2dbcService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
