package com.example.hellowebflux.repository;

import com.example.hellowebflux.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserR2dbcRepository extends ReactiveCrudRepository<User, Long> {}
