package com.lorenchess.blogrestapi.controller;

import com.lorenchess.blogrestapi.payloadDTO.PostDto;
import com.lorenchess.blogrestapi.payloadDTO.PostResponse;
import com.lorenchess.blogrestapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.lorenchess.blogrestapi.utils.AppConstants.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public PostResponse getAllPost(@RequestParam(value="pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                   @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                   @RequestParam(value="sortBy", defaultValue = DEFAULT_SORT_BY, required = false)String sortBy,
                                   @RequestParam(value = "sortDir",defaultValue = DEFAULT_SORT_DIRECTION, required = false)String sortDir) {
        return postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto>createPost(@Valid @RequestBody PostDto postDto) {
      return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto>getPostById(@PathVariable(name="id") Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto>updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);

        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }










}
