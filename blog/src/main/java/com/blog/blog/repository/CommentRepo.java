package com.blog.blog.repository;

import com.blog.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(Long id);
}
