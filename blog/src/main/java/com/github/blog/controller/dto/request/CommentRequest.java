package com.github.blog.controller.dto.request;

import com.github.blog.controller.util.marker.BaseMarker;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

/**
 * @author Raman Haurylau
 */
public record CommentRequest(
        @NotNull(message = "Post ID is mandatory", groups = BaseMarker.Create.class) @Null(message = "Post ID should be null", groups = BaseMarker.Update.class) Long postId,
        @NotBlank(message = "Content is mandatory") @Size(message = "Content should be between 10 and 10.000", min = 10, max = 10000) String content) {

}
