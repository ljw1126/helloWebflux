package com.example.hellowebflux.service;

import com.example.hellowebflux.client.PostClient;
import com.example.hellowebflux.client.PostResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostClient postClient;

    public Mono<PostResponse> getPostContent(Long id) {
        return postClient.getPost(id)
            .onErrorResume(ex -> Mono.just(new PostResponse(id.toString(), "Fallback data : %d".formatted(id))))
            .log();
    }

    public Flux<PostResponse> getMultiplePostContent(List<Long> ids) {
        return Flux.fromIterable(ids)
                .flatMap(this::getPostContent)
                .log();
    }

    public Flux<PostResponse> getParallelMultiplePostContent(List<Long> ids) {
        return Flux.fromIterable(ids)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(this::getPostContent)
                .log()
                .sequential();
    }
}
