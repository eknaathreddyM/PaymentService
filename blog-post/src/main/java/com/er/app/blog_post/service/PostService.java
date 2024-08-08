package com.er.app.blog_post.service;

import com.er.app.blog_post.Dto.PostDto;
import com.er.app.blog_post.Dto.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto getPostById(Long id);
    PostDto updateById(Long id, PostDto postDto);
    void deletePostById(Long id);
}
