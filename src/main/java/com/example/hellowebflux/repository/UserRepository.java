package com.example.hellowebflux.repository;

import com.example.hellowebflux.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> save(User user);

    Flux<User> findAll();

    Mono<User> findById(Long id);

    Mono<Integer> deleteById(Long id);
}
