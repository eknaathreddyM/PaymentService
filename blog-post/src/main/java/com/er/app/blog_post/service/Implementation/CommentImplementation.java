package com.er.app.blog_post.service.Implementation;

import com.er.app.blog_post.Dto.CommentDto;
import com.er.app.blog_post.entity.Comment;
import com.er.app.blog_post.entity.Post;
import com.er.app.blog_post.exception.BlogApiException;
import com.er.app.blog_post.exception.ResourceNotFoundException;
import com.er.app.blog_post.repository.CommentRepository;
import com.er.app.blog_post.repository.PostRepository;
import com.er.app.blog_post.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CommentImplementation implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;


    public CommentImplementation(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper=mapper;
    }




    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment=mapToEntity(commentDto);
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        comment.setPost(post);
        comment=commentRepository.save(comment);

        return mapToDto(comment);
    }




    @Override
    public List<CommentDto> getAllComments(Long postId) {
        List<Comment> commentList=commentRepository.findByPostId(postId);
        return commentList.stream().map(this::mapToDto).toList();

    }




    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Comment comment=commentValidation(postId,commentId);
        return mapToDto(comment);
    }




    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {
       Comment comment=commentValidation(postId,commentId);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setMessage(commentDto.getMessage());

        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {

        Comment comment=commentValidation(postId,commentId);
        commentRepository.deleteById(commentId);
    }


    private Comment commentValidation(Long postId, Long commentId){
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","id",commentId));

        if(!comment.getPost().getId().equals(postId)){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,String.format("Comment not found in the postId: %s",postId));
        }
        return comment;
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto=mapper.map(comment, CommentDto.class);

        return commentDto;
    }

    private  Comment mapToEntity(CommentDto commentDto){
        Comment comment=mapper.map(commentDto,Comment.class);

        return comment;
    }
}
