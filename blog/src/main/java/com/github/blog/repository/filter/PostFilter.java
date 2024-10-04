package com.github.blog.repository.filter;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Raman Haurylau
 */
@Getter
@Setter
public class PostFilter {
    private String username;
    private Long tagId;
    private String state;
}
