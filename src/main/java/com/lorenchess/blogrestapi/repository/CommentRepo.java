package com.lorenchess.blogrestapi.repository;

import com.lorenchess.blogrestapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The @Repository is not necessary here because we have a SimpleJpaRepository that has @Repository annotation on it
 * */
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);
}
