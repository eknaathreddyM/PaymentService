package com.er.app.blog_post.service.Implementation;

import com.er.app.blog_post.Dto.PostDto;
import com.er.app.blog_post.Dto.PostResponse;
import com.er.app.blog_post.entity.Post;
import com.er.app.blog_post.exception.ResourceNotFoundException;
import com.er.app.blog_post.repository.PostRepository;
import com.er.app.blog_post.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostImplementation implements PostService {

    private  PostRepository postRepository;

    private ModelMapper mapper;


    PostImplementation(PostRepository postRepository,ModelMapper mapper){
        this.postRepository=postRepository;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
       Post post=mapToEntity(postDto);
        post=postRepository.save(post);
        return mapToDto(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
       PageRequest pageRequest= PageRequest.of(pageNo,pageSize,sort);

        Page<Post> posts= postRepository.findAll(pageRequest);
        List<Post> listOfPosts=posts.getContent();
        List<PostDto> postDtoList= listOfPosts.stream().map(this::mapToDto).toList();

        PostResponse postResponse=new PostResponse();
        postResponse.setPosts(postDtoList);
        postResponse.setLast(posts.isLast());
        postResponse.setPageSize(posts.getSize());
        postResponse.setPageNo(posts.getNumber());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());

        return postResponse;

    }

    @Override
    public PostDto getPostById(Long id) {
        Post post= postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updateById(Long id, PostDto postDto) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id", id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost=postRepository.save(post);

        return mapToDto(updatedPost);

    }

    @Override
    public void deletePostById(Long id) {
        Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","Id",id));
        postRepository.delete(post);
    }


    //map to PostDto frm Post entity
    private  PostDto mapToDto(Post post){
        return mapper.map(post,PostDto.class);
    }

    private Post mapToEntity(PostDto postDto){

        return mapper.map(postDto,Post.class);
    }


}
