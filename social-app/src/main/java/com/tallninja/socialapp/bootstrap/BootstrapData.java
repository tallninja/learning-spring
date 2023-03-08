package com.tallninja.socialapp.bootstrap;

import com.tallninja.socialapp.posts.Post;
import com.tallninja.socialapp.posts.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        if (postRepository.count() == 0) {

            List<Post> testPosts = new ArrayList<>();
            testPosts.add(Post.builder()
                    .title("Test Post 1")
                    .content("Test post 1 content")
                    .build());
            testPosts.add(Post.builder()
                    .title("Test Post 2")
                    .content("Test post 2 content")
                    .build());
            testPosts.add(Post.builder()
                    .title("Test Post 3")
                    .content("Test post 3 content")
                    .build());

            List<Post> savedPosts = postRepository.saveAll(testPosts);
            System.out.println(savedPosts);
        }
    }
}
