package com.tallninja.socialapp.posts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull  // cannot be null
    @NotBlank // cannot be an empty string
    private String title;

    @NotNull
    @NotBlank
    private String content;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
