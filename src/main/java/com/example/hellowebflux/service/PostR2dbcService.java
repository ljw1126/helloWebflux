package com.example.hellowebflux.service;

import com.example.hellowebflux.domain.Post;
import com.example.hellowebflux.repository.PostR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostR2dbcService {
    private final PostR2dbcRepository postR2dbcRepository;

    public Mono<Post> create(Long userId, String title, String content) {
        Post post = Post.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .build();
        return postR2dbcRepository.save(post);
    }

    public Flux<Post> findAll() {
        return postR2dbcRepository.findAll();
    }

    public Mono<Post> findById(Long id) {
        return postR2dbcRepository.findById(id);
    }

    public Flux<Post> findAllByUserId(Long userId) {
        return postR2dbcRepository.findAllByUserId(userId);
    }

    public Mono<Void> deleteById(Long id) {
        return postR2dbcRepository.deleteById(id);
    }
}
