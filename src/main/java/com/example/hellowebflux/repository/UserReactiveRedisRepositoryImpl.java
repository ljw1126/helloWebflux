package com.example.hellowebflux.repository;

import com.example.hellowebflux.domain.User;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserReactiveRedisRepositoryImpl implements UserRedisRepository{
    private static final String USER_KEY = "users:%d";

    private final ReactiveRedisTemplate<String, User> reactiveRedisTemplate;

    @Override
    public Mono<User> get(Long id) {
        return reactiveRedisTemplate.opsForValue().get(getUserCacheKey(id));
    }

    @Override
    public Mono<Boolean> set(User user, Duration ttl) {
        return reactiveRedisTemplate.opsForValue().set(getUserCacheKey(user.getId()), user, ttl);
    }

    @Override
    public Mono<Long> unlink(Long id) {
        return reactiveRedisTemplate.unlink(getUserCacheKey(id));
    }

    private String getUserCacheKey(Long id) {
        return USER_KEY.formatted(id);
    }
}
