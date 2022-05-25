package com.lorenchess.blogrestapi.payloadDTO;

import lombok.Data;

import java.util.Set;

/**
 * Class created with the reason to not expose directly the entity class.
 * */

@Data
public class PostDto {
    private Long id;

    private String title;

    private String description;

    private String content;

    private Set<CommentDto> comments;
}
