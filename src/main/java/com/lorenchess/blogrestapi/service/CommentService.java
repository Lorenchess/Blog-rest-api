package com.lorenchess.blogrestapi.service;

import com.lorenchess.blogrestapi.payloadDTO.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest);

    void deleteCommentById(Long postId, Long commentId);

}
