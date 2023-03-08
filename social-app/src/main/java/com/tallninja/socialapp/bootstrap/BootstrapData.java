package com.tallninja.socialapp.bootstrap;

import com.tallninja.socialapp.posts.Post;
import com.tallninja.socialapp.posts.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        Post testPost = Post.builder()
                .title("Test Post")
                .content("Test post content")
                .build();

        Post savedPost = postRepository.save(testPost);

        System.out.println(savedPost);
    }
}
