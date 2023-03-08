package com.tallninja.socialapp.posts;

import com.tallninja.socialapp.exceptions.ApiRequestException;
import com.tallninja.socialapp.exceptions.NotFoundException;
import com.tallninja.socialapp.posts.dto.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PostController {

    public static final String POSTS_PATH = "/api/v1/posts";
    public static final String POST_PATH = POSTS_PATH + "/{id}";

    private final PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(POSTS_PATH)
    public ResponseEntity<List<PostDto>> fetchAllPosts() {
        List<Post> posts = postService.findAll();
        List<PostDto> postDtos = posts.stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping(POST_PATH)
    public ResponseEntity<PostDto> fetchPost(@PathVariable("id") UUID id) {
        try {
            Post post = postService.findOneById(id);
            return new ResponseEntity<>(this.convertToDto(post), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof NotFoundException)
                throw new ApiRequestException(e.getMessage(), HttpStatus.NOT_FOUND);
            throw e;
        }
    }

    @PostMapping(POSTS_PATH)
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postData) {
        Post post = postService.create(concertToEntity(postData));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, POSTS_PATH + "/" + post.getId().toString());
        return new ResponseEntity<>(this.convertToDto(post), headers, HttpStatus.CREATED);
    }

    @PutMapping(POST_PATH)
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") UUID id, @RequestBody PostDto postData) {
        try {
            Post postEntity = postService.update(id, this.concertToEntity(postData));
            return new ResponseEntity<>(this.convertToDto(postEntity), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof NotFoundException)
                throw new ApiRequestException(e.getMessage(), HttpStatus.NOT_FOUND);
            throw e;
        }
    }

    @DeleteMapping(POST_PATH)
    public ResponseEntity<PostDto> deletePost(@PathVariable("id") UUID id) {
        try {
            Post post = this.postService.delete(id);
            return new ResponseEntity<>(convertToDto(post), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof  NotFoundException)
                throw new ApiRequestException(e.getMessage(), HttpStatus.NOT_FOUND);
            throw e;
        }
    }

    private PostDto convertToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    private Post concertToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
