package com.example.hellowebflux.repository;

import com.example.hellowebflux.domain.User;
import java.time.Duration;
import reactor.core.publisher.Mono;

public interface UserRedisRepository {
    Mono<User> get(Long id);
    Mono<Boolean> set(User user, Duration ttl);
    Mono<Long> unlink(Long id);
}
