package com.example.hellowebflux.repository;

import com.example.hellowebflux.domain.Post;
import com.example.hellowebflux.domain.User;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class PostCustomR2dbcRepositoryImpl implements PostCustomR2dbcRepository{
    private final DatabaseClient databaseClient;

    // TODO. Null Object Pattern 사용해서 USERS NPE 방지
    // TODO. Testcontainers 테스트 작성
    @Override
    public Flux<Post> findAllByUserId(Long userId) {
    String sql = """
            SELECT  p.id as pid, 
                    p.user_id as userId, 
                    p.title, 
                    p.content, 
                    p.created_at as createdAt, 
                    p.updated_at as updatedAt,
                    u.id as uid, 
                    u.name as name, 
                    u.email as email, 
                    u.created_at as uCreatedAt, 
                    u.updated_at as uUpdatedAt
            FROM posts p LEFT JOIN users u ON p.user_id = u.id
            WHERE p.user_id = :userId
        """;

    return databaseClient
        .sql(sql)
        .bind("userId", userId)
        .fetch()
        .all()
        .map(
            row ->
                Post.builder()
                    .id(((Number)row.get("pid")).longValue())
                    .userId(((Number)row.get("userId")).longValue())
                    .title((String) row.get("title"))
                    .content((String) row.get("content"))
                    .user(User.builder()
                            .id(((Number)row.get("uid")).longValue())
                            .name((String) row.get("name"))
                            .email((String) row.get("email"))
                            .createdAt(toLocalDateTime(row.get("uCreatedAt")))
                            .updatedAt(toLocalDateTime(row.get("uUpdatedAt")))
                            .build())
                    .createdAt(toLocalDateTime(row.get("createdAt")))
                    .updatedAt(toLocalDateTime(row.get("updatedAt")))
                    .build())
            .log();
    }

    private LocalDateTime toLocalDateTime(Object time) {
        if (time instanceof ZonedDateTime zdt) {
            return zdt.toLocalDateTime();
        }
        return time instanceof LocalDateTime ldt ? ldt : null;
    }
}
