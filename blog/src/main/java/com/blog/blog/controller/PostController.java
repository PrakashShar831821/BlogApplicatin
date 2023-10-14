package com.blog.blog.controller;

import com.blog.blog.payload.PostDto;
import com.blog.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto=postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    @GetMapping
    public List<PostDto> listAllPosts(
            @RequestParam(value="pageNumber",defaultValue ="0",required = false ) Integer pageNumber,
            @RequestParam(value="pageSize",defaultValue ="10",required = false )Integer pageSize,
            @RequestParam(value="sortBy",defaultValue ="id",required = false )String sortBy,
            @RequestParam(value="sortDir",defaultValue ="acs",required = false )String sortDir
           ){
        List<PostDto> postDto=postService.listAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return postDto;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("id") Long id){
        PostDto dto=postService.updatePost(postDto,id);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable("id") Long id){
        this.postService.deletePost(id);

    }
}
