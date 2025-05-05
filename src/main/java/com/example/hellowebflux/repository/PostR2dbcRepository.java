package com.example.hellowebflux.repository;

import com.example.hellowebflux.domain.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostR2dbcRepository extends ReactiveCrudRepository<Post, Long> {}
