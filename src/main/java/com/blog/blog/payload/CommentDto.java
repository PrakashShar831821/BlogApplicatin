package com.blog.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class CommentDto {
    private Long id;

    private String body;
    @Email(message="Please enter valid email ")
    private String email;

    private String name;
}

