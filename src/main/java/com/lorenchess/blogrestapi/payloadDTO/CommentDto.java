package com.lorenchess.blogrestapi.payloadDTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Class created with the reason to not expose directly the entity class.
 * */

@Data
public class CommentDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Name should have at list 2 characters")
    private String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body must be 10 characters minimum")
    private String body;
}
