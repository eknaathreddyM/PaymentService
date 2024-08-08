package com.er.app.blog_post.controller;

import com.er.app.blog_post.Dto.PostDto;
import com.er.app.blog_post.Dto.PostResponse;
import com.er.app.blog_post.constants.AppConstants;
import com.er.app.blog_post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private PostService postService;

    PostController(PostService postService){
        this.postService=postService;
    }

    //post blog post api...
    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }


    //get all posts api
    @GetMapping("/getAll")
    public ResponseEntity<PostResponse>getAllPosts(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return  new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    //get post by id
    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    //update post by id
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updateById(@PathVariable Long id, @RequestBody @Valid PostDto postDto){
        return new ResponseEntity<>(postService.updateById(id,postDto),HttpStatus.OK);
    }

    //delete post by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>(String.format("Post with id %s has been deleted successfully",id),HttpStatus.OK);
    }
}
