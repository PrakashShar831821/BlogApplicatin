package com.blog.blog.service;

import com.blog.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto> getAllCommentByPostId(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(long postId,long commentId,CommentDto commentDto);

    void deletecomment(long postId,long commentId);


}
