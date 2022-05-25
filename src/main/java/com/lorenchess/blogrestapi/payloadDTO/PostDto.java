package com.lorenchess.blogrestapi.payloadDTO;

import lombok.Data;

/**
 * Class created with the reason to not expose directly the entity class.
 * */

@Data
public class PostDto {
    private Long id;

    private String title;

    private String description;

    private String content;
}
