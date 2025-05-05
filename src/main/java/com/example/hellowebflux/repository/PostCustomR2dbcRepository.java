package com.example.hellowebflux.repository;

import com.example.hellowebflux.domain.Post;
import reactor.core.publisher.Flux;

public interface PostCustomR2dbcRepository {
    Flux<Post> findAllByUserId(Long userId);
}
