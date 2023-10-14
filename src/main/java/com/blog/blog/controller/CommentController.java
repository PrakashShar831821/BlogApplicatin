package com.blog.blog.controller;

import com.blog.blog.payload.CommentDto;
import com.blog.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value ="postId") long postId, @RequestBody CommentDto commentDto){
        CommentDto newcommentDto=commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(newcommentDto, HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId")
                                                Long postId){
        return commentService.getAllCommentByPostId(postId);
    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentsById(@PathVariable(value = "postId")Long postId,
                                            @PathVariable(value="commentId") Long commentId){
        CommentDto commentDto=commentService.getCommentById(postId,commentId);

        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long postId,@PathVariable long commentId,@RequestBody CommentDto commentDto){
        CommentDto commentDto1=commentService.updateComment(postId,commentId,commentDto);
        return new ResponseEntity<>(commentDto1, HttpStatus.OK);
    }
    @DeleteMapping("/post/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable long postId,@PathVariable long commentId){
        commentService.deletecomment(postId,commentId);
        return "delete comment successfully !!";
    }
}
