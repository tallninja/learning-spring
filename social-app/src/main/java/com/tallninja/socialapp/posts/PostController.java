package com.tallninja.socialapp.posts;

import com.tallninja.socialapp.exceptions.ApiRequestException;
import com.tallninja.socialapp.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PostController {

    public static final String POSTS_PATH = "/api/v1/posts";
    public static final String POST_PATH = POSTS_PATH + "/{id}";

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(POSTS_PATH)
    public ResponseEntity<List<Post>> fetchAllPosts() {
        List<Post> posts = postService.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(POST_PATH)
    public ResponseEntity<Post> fetchPost(@PathVariable("id") UUID id) {
        try {
            Post post = postService.findOneById(id);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof NotFoundException)
                throw new ApiRequestException(e.getMessage(), HttpStatus.NOT_FOUND);
            throw e;
        }
    }

    @PostMapping(POSTS_PATH)
    public ResponseEntity<Post> createPost(@RequestBody Post postData) {
        Post post = postService.create(postData);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, POSTS_PATH + "/" + post.getId().toString());
        return new ResponseEntity<>(post, headers, HttpStatus.CREATED);
    }

    @PutMapping(POST_PATH)
    public ResponseEntity<Post> updatePost(@PathVariable("id") UUID id, @RequestBody Post postData) {
        try {
            Post post = postService.update(id, postData);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof NotFoundException)
                throw new ApiRequestException(e.getMessage(), HttpStatus.NOT_FOUND);
            throw e;
        }
    }

    @DeleteMapping(POST_PATH)
    public ResponseEntity<Post> deletePost(@PathVariable("id") UUID id) {
        try {
            Post post = this.postService.delete(id);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof  NotFoundException)
                throw new ApiRequestException(e.getMessage(), HttpStatus.NOT_FOUND);
            throw e;
        }
    }
}
