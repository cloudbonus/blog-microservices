package com.github.blog.service.impl;

import com.github.blog.controller.dto.common.CommentDto;
import com.github.blog.controller.dto.request.CommentRequest;
import com.github.blog.controller.dto.request.PageableRequest;
import com.github.blog.controller.dto.request.filter.CommentFilterRequest;
import com.github.blog.controller.dto.response.PageResponse;
import com.github.blog.repository.CommentRepository;
import com.github.blog.repository.PostRepository;
import com.github.blog.repository.UserRepository;
import com.github.blog.repository.entity.Comment;
import com.github.blog.repository.entity.Post;
import com.github.blog.repository.entity.User;
import com.github.blog.repository.filter.CommentFilter;
import com.github.blog.repository.specification.CommentSpecification;
import com.github.blog.service.CommentService;
import com.github.blog.service.exception.ExceptionEnum;
import com.github.blog.service.exception.impl.CustomException;
import com.github.blog.service.mapper.CommentMapper;
import com.github.blog.service.util.UserAccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Raman Haurylau
 */
@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final CommentMapper commentMapper;

    private final UserAccessHandler userAccessHandler;

    @Override
    public CommentDto create(CommentRequest request) {
        log.debug("Creating a new comment with request: {}", request);
        Comment comment = commentMapper.toEntity(request);
        User user = userRepository
                .findById(userAccessHandler.getUserId())
                .orElseThrow(() -> new CustomException(ExceptionEnum.USER_NOT_FOUND));

        Post post = postRepository
                .findById(request.postId())
                .orElseThrow(() -> new CustomException(ExceptionEnum.POST_NOT_FOUND));

        comment.setUser(user);
        comment.setPost(post);

        Comment createdComment = commentRepository.save(comment);
        log.debug("Created comment: {}", createdComment);

        return commentMapper.toDto(createdComment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto findById(Long id) {
        log.debug("Finding comment by ID: {}", id);
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.COMMENT_NOT_FOUND));

        log.debug("Comment found with ID: {}", id);
        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CommentDto> findAll(CommentFilterRequest requestFilter, PageableRequest pageableRequest) {
        log.debug("Finding all comments with filter: {} and pageable: {}", requestFilter, pageableRequest);
        CommentFilter filter = commentMapper.toEntity(requestFilter);

        Pageable pageable = PageRequest.of(pageableRequest.pageNumber(), pageableRequest.pageSize(), pageableRequest.getSort());
        Specification<Comment> spec = CommentSpecification.filterBy(filter);
        Page<Comment> comments = commentRepository.findAll(spec, pageable);

        if (comments.isEmpty()) {
            throw new CustomException(ExceptionEnum.COMMENTS_NOT_FOUND);
        }

        log.debug("Found {} comments", comments.getTotalElements());
        return commentMapper.toDto(comments);
    }

    @Override
    public CommentDto update(Long id, CommentRequest request) {
        log.debug("Updating comment with comments: {} and request: {}", id, request);
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.COMMENT_NOT_FOUND));

        comment = commentMapper.partialUpdate(request, comment);
        log.debug("Comment updated successfully with ID: {}", id);

        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDto delete(Long id) {
        log.debug("Deleting comment with ID: {}", id);
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ExceptionEnum.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);
        log.debug("Comment deleted successfully with ID: {}", id);

        return commentMapper.toDto(comment);
    }
}

