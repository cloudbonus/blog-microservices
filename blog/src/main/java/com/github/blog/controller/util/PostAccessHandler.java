package com.github.blog.controller.util;

import com.github.blog.controller.dto.common.OrderDto;
import com.github.blog.controller.dto.common.PostDto;
import com.github.blog.controller.dto.request.filter.PostFilterRequest;
import com.github.blog.repository.entity.util.OrderState;
import com.github.blog.service.OrderService;
import com.github.blog.service.PostService;
import com.github.blog.service.exception.impl.CustomException;
import com.github.blog.service.security.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Raman Haurylau
 */
@Component("postAccess")
@RequiredArgsConstructor
public class PostAccessHandler {

    private final AuthenticatedUserService authenticatedUserService;

    private final OrderService orderService;

    private final PostService postService;

    public boolean verifyPostOwnershipIfPurchased(Long id) {
        try {
            Long sessionUserId = authenticatedUserService.getAuthenticatedUser().getId();
            OrderDto orderDto = orderService.findByPostId(id);
            return orderDto.userId().equals(sessionUserId);
        } catch (CustomException e) {
            return true;
        }
    }

    public boolean verifyOwnership(Long id) {
        try {
            Long sessionUserId = authenticatedUserService.getAuthenticatedUser().getId();
            PostDto postDto = postService.findById(id);
            return postDto.userId().equals(sessionUserId);
        } catch (CustomException e) {
            return false;
        }
    }

    public boolean canFilter(PostFilterRequest requestFilter) {
        if (!requestFilter.state().equals(OrderState.COMMITED.name()) && requestFilter.username() != null) {
            return requestFilter.username().equals(authenticatedUserService.getAuthenticatedUser().getUsername());
        }
        return true;
    }
}