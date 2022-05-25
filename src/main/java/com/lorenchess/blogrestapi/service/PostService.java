package com.lorenchess.blogrestapi.service;

import com.lorenchess.blogrestapi.payloadDTO.PostDto;
import com.lorenchess.blogrestapi.payloadDTO.PostResponse;

public interface PostService {

    PostDto createPost(PostDto post);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long postId);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);
}
