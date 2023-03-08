package com.tallninja.socialapp.posts.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private UUID id;
    private String title;
    private String content;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
