package com.blog.blog.service;

import com.blog.blog.entity.Post;
import com.blog.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> listAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
   PostDto updatePost(PostDto postDto,Long id);

   void deletePost(Long id);
}
