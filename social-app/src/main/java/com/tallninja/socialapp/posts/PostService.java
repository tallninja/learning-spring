package com.tallninja.socialapp.posts;

import java.util.List;
import java.util.UUID;

public interface PostService {

    public List<Post> findAll();

    public Post findOneById(UUID id);

    public Post create(Post post);

    public Post update(UUID id, Post postData);

    public Post delete(UUID id);
}
