package com.er.app.blog_post.service;

import com.er.app.blog_post.Dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long id, CommentDto commentDto);
    List<CommentDto> getAllComments(Long postId);
    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto);

    void deleteCommentById(Long postId, Long commentId);
}
