package com.tallninja.socialapp.posts;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @NotBlank
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @NotBlank
    @Size(min = 4, max = 50)
    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @PrePersist
    void onPrePersist() {
        this.setCreatedAt(ZonedDateTime.now(ZoneId.of("UTC")));
        this.setUpdatedAt(ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @PreUpdate
    void onPreUpdate() {
        this.setUpdatedAt(ZonedDateTime.now(ZoneId.of("UTC")));
    }
}
