package com.er.app.blog_post.repository;

import com.er.app.blog_post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
