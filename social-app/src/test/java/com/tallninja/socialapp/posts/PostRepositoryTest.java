package com.tallninja.socialapp.posts;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void testSavedPost() {
        Post testPost = Post.builder()
                .title("Test Post")
                .content("Test post content")
                .build();

        Post savedPost = postRepository.save(testPost);
        System.out.println(savedPost);
        assertThat(savedPost).isNotNull();
    }

}