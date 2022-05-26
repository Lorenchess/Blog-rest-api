package com.lorenchess.blogrestapi.payloadDTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Class created with the reason to not expose directly the entity class.
 * */

@Data
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should have at list 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should have at list 2 characters")
    private String description;

    @NotEmpty
    @Size(min = 2, message = "Post content should have at list 2 characters")
    private String content;

    private Set<CommentDto> comments;
}
