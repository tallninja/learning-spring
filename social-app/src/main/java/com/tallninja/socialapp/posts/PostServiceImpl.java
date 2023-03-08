package com.tallninja.socialapp.posts;

import com.tallninja.socialapp.exceptions.ApiRequestException;
import com.tallninja.socialapp.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Post findOneById(UUID id) {
        Optional<Post> post = this.postRepository.findById(id);
        if (post.isEmpty()) throw new NotFoundException("Post Not Found");
        return post.get();
    }

    @Override
    public Post create(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post update(UUID id, Post postData) {
        Post post = this.findOneById(id);
        post.setTitle(postData.getTitle());
        post.setContent(postData.getContent());
        return postRepository.save(post);
    }

    @Override
    public Post delete(UUID id) {
        Post post = this.findOneById(id);
        postRepository.delete(post);
        return post;
    }
}
