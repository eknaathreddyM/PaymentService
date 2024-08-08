package com.er.app.blog_post.controller;

import com.er.app.blog_post.Dto.CommentDto;
import com.er.app.blog_post.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post/{postId}/comment")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId,
                                                    @RequestBody @Valid CommentDto commentDto)      {
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAllComments")
    public  ResponseEntity<List<CommentDto>> getAllByPostId(@PathVariable long postId){
        return new ResponseEntity<>(commentService.getAllComments(postId),HttpStatus.OK);
    }

    @GetMapping("get/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId,
                                                     @PathVariable long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long postId,
                                                    @PathVariable long commentId,
                                                    @RequestBody @Valid CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentById(postId,commentId,commentDto),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long postId,
                                                @PathVariable long commentId){
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>(String.format("Comment with id %s has been deleted successfully",commentId),HttpStatus.OK);
    }



}
