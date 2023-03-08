package com.tallninja.socialapp.posts;

import com.tallninja.socialapp.exceptions.ApiRequestException;
import com.tallninja.socialapp.exceptions.NotFoundException;
import com.tallninja.socialapp.posts.dto.PostDto;
import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostControllerIT {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostController postController;

    @Test
    void testFetchAllPosts() {
        ResponseEntity<List<PostDto>> response = postController.fetchAllPosts();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).size())
                .isEqualTo(3);
    }

    @Transactional // make the test transactional
    @Rollback // roll back changes after test is complete
    @Test
    void testEmptyPosts() {
        // delete all records in the DB
        postRepository.deleteAll();

        ResponseEntity<List<PostDto>> response = postController.fetchAllPosts();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).size())
                .isEqualTo(0);
    }

    @Test
    void testFetchPost() {
        PostDto post = Objects.requireNonNull(postController
                        .fetchAllPosts()
                        .getBody())
                .get(0);
        ResponseEntity<PostDto> response = postController.fetchPost(post.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        PostDto _post = response.getBody();
        assertThat(_post).isNotNull();
        assertThat(_post.getId()).isEqualTo(post.getId());
    }

    @Test
    void testFetchPost_throwsIfNotFound() {
        assertThrows(ApiRequestException.class, () -> {
            postController.fetchPost(UUID.randomUUID());
        });
    }

    @Transactional
    @Rollback
    @Test
    void testCreatePost() {
        String postTitle = "Test Title 4";
        String postContent = "Test Post 4 Content";
        PostDto post = PostDto.builder()
                .title(postTitle)
                .content(postContent)
                .build();
        ResponseEntity<PostDto> response = postController.createPost(post);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo(postTitle);
        assertThat(response.getBody().getContent()).isEqualTo(postContent);
    }

    @Transactional
    @Rollback
    @Test()
    void testUpdatePost() {
        PostDto post = Objects.requireNonNull(postController
                        .fetchAllPosts()
                        .getBody())
                .get(0);

        String newPostTitle = "Updated Title";
        post.setTitle(newPostTitle);

        ResponseEntity<PostDto> response = postController.updatePost(post.getId(), post);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo(newPostTitle);
    }

    @Test
    void testUpdatePost_throwsExceptionIfPostNotFound() {
        assertThrows(ApiRequestException.class, () -> {
            postController.updatePost(UUID.randomUUID(), new PostDto());
        });
    }

    @Transactional
    @Rollback
    @Test
    void testDeletePost() {
        PostDto post = Objects.requireNonNull(postController
                        .fetchAllPosts()
                        .getBody())
                .get(0);

        ResponseEntity<PostDto> response = postController.deletePost(post.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(post.getId());
    }

    @Test
    void testDeletePost_throwsExceptionIfPostNotFound() {
        assertThrows(ApiRequestException.class, () -> {
            postController.deletePost(UUID.randomUUID());
        });
    }
}