package com.lorenchess.blogrestapi.payloadDTO;

import lombok.Data;

/**
 * Class created with the reason to not expose directly the entity class.
 * */

@Data
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
}
