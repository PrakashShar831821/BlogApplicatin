package com.blog.blog.service.impl;

import com.blog.blog.entity.Post;
import com.blog.blog.payload.PostDto;
import com.blog.blog.repository.PostRepository;
import com.blog.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(@RequestBody PostDto postDto) {
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        Post post=mapToEntity(postDto);
        Post newPost = postRepository.save(post);

//        PostDto dto=new PostDto();
//        dto.setId(newPost.getId());
//        dto.setTitle(newPost.getTitle());
//        dto.setDescription(newPost.getDescription());
//        dto.setContent(newPost.getContent());
        PostDto newDto=mapToDto(newPost);

        return newDto;
    }
    public List<PostDto> listAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir){

        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pagable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> posts=postRepository.findAll(pagable);
        List<Post> allPost=posts.getContent();
        List<PostDto> postDtos=allPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }
    public PostDto updatePost(PostDto postDto,Long id){
        Post post= postRepository.findById(id).get();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatepost=postRepository.save(post);
        PostDto newPost1=mapToDto(updatepost);
        return newPost1;
    }
    public void deletePost(Long id){
       Post post=postRepository.findById(id).get();
       postRepository.delete(post);
    }
    private PostDto mapToDto(Post post){
        PostDto dto=modelMapper.map(post,PostDto.class);
//        PostDto dto=new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }
    private Post mapToEntity(PostDto postDto){
        Post post=modelMapper.map(postDto,Post.class);
//        Post post=new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
