package com.blog.blog.service.impl;

import com.blog.blog.entity.Comment;
import com.blog.blog.entity.Post;
import com.blog.blog.exception.ResourceNotFoundException;
import com.blog.blog.payload.CommentDto;
import com.blog.blog.repository.CommentRepo;
import com.blog.blog.repository.PostRepository;
import com.blog.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
       Comment comment= mapToEntity(commentDto);
       comment.setPost(post);
       Comment newComment=commentRepo.save(comment);
       CommentDto dto=mapToDto(newComment);
        return dto;
    }

    @Override
    public List<CommentDto> getAllCommentByPostId(Long postId) {
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));

        List<Comment> comments=commentRepo.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
       Comment comment= commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","comment id",commentId));
       CommentDto commentDto=mapToDto(comment);
        return commentDto;
    }

    @Override
    public CommentDto updateComment(long postId,long commentId, CommentDto commentDto) {
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
        Comment comment= commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","comment id",commentId));
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
       Comment updateComment= commentRepo.save(comment);
       CommentDto commentDto1=mapToDto(updateComment);
        return commentDto1;
    }

    @Override
    public void deletecomment(long postId,long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
        Comment comment=commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","comment id",commentId));
        commentRepo.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
       CommentDto commentDto= modelMapper.map(comment,CommentDto.class);
//        CommentDto commentDto=new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=modelMapper.map(commentDto,Comment.class);
//        Comment comment=new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
