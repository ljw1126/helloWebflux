package com.example.hellowebflux.service;

import com.example.hellowebflux.domain.User;
import com.example.hellowebflux.repository.UserR2dbcRepository;
import com.example.hellowebflux.repository.UserRedisRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserR2dbcRepository userR2dbcRepository;
    private final UserRedisRepository userRedisRepository;

    public Mono<User> create(String name, String email) {
        User user = User.builder().name(name).email(email).build();
        return userR2dbcRepository.save(user);
    }

    public Flux<User> findAll() {
        return userR2dbcRepository.findAll();
    }

    public Mono<User> findById(Long id) {
        return userRedisRepository.get(id)
                .switchIfEmpty(userR2dbcRepository.findById(id)
                        .flatMap(u -> userRedisRepository.set(u, Duration.ofSeconds(30))
                                .then(Mono.just(u)))
                );
    }

    public Mono<Void> deleteById(Long id) {
        return userR2dbcRepository.deleteById(id)
                .then(userRedisRepository.unlink(id))
                .then(Mono.empty());
    }

    public Mono<User> update(Long id, String name, String email) {
        return userR2dbcRepository.findById(id)
                .flatMap(u -> {
                    u.setName(name);
                    u.setEmail(email);
                    return userR2dbcRepository.save(u);
                })
                .flatMap(u -> userRedisRepository.unlink(id).then(Mono.just(u)));
    }
}
