package com.lorenchess.blogrestapi.controller;

import com.lorenchess.blogrestapi.entities.Comment;
import com.lorenchess.blogrestapi.payloadDTO.CommentDto;
import com.lorenchess.blogrestapi.payloadDTO.PostDto;
import com.lorenchess.blogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId, @Valid @RequestBody CommentDto commentDto) {
      return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto>getCommentById(@PathVariable(name = "postId")Long postId,
                                                    @PathVariable(name = "commentId")Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId")Long postId,
                                                    @PathVariable(name = "id")Long commentId,
                                                    @Valid @RequestBody CommentDto commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentRequest));
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId")Long postId,
                                                @PathVariable(name = "id")Long commentId) {
        commentService.deleteCommentById(postId, commentId);

        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }

}
