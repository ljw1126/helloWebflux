package com.example.hellowebflux.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostR2dbcCreateRequest {
    private Long userId;
    private String title;
    private String content;
}
