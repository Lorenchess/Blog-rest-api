package com.lorenchess.blogrestapi.service.implementations;

import com.lorenchess.blogrestapi.entities.Comment;
import com.lorenchess.blogrestapi.entities.Post;
import com.lorenchess.blogrestapi.exceptions.BlogAPIException;
import com.lorenchess.blogrestapi.exceptions.ResourceNotFoundException;
import com.lorenchess.blogrestapi.payloadDTO.CommentDto;
import com.lorenchess.blogrestapi.repository.CommentRepo;
import com.lorenchess.blogrestapi.repository.PostRepo;
import com.lorenchess.blogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final ModelMapper mapper;


    public CommentServiceImpl(CommentRepo commentRepo, PostRepo postRepo, ModelMapper mapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.mapper = mapper;
    }


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        //convert comment dto to entity
        Comment commentEntity = mapToEntity(commentDto);

        //retrieve post entity by id
        Post post = findPostById(postId);

        //link the post found by id with the comment that we are creating, remember we have a @ManyToOne relationship in the entity, here we add the post to the comment we are creating
        commentEntity.setPost(post);

        //save comment
        Comment comment = commentRepo.save(commentEntity);
        //map back to DTO and return it
        return mapToDTO(comment);
    }

    /**
     * Returning a List of comments according to the post identified by the id sent in the URI
     * */
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //using the custom query findByPostId in the Repo to find the list of comments for specific post, identified by the id passed in the URI
        List<Comment> comments = commentRepo.findByPostId(postId);

        //in-line return and converting the list of comments entities to DTO
        return comments.stream().map(comment -> mapToDTO(comment)).toList();
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        Comment commentResponse = findCommentIdByPostId(postId, commentId);

        return mapToDTO(commentResponse);

    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        Comment commentEntity = findCommentIdByPostId(postId, commentId);

        //update the comment with the commentRequest passed on the URI
        commentEntity.setName(commentRequest.getName());
        commentEntity.setEmail(commentRequest.getEmail());
        commentEntity.setBody(commentRequest.getBody());

        //save comment into database
        Comment updatedComment = commentRepo.save(commentEntity);

        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Comment commentEntity = findCommentIdByPostId(postId, commentId);
        commentRepo.delete(commentEntity);
    }

    //helper methods to convert from entity to DTO or from DTO to entity
    private CommentDto mapToDTO(Comment commentEntity) {
        return mapper.map(commentEntity, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }

    private Comment findCommentById(Long commentId) {
        return commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
    }

    private Post findPostById(Long postId) {
        return postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
    }

    private Comment findCommentIdByPostId(Long postId, Long commentId) {
        Post postEntity = findPostById(postId);
        Comment comment = findCommentById(commentId);

        //check if comment belong to post
        if (!comment.getPost().getId().equals(postEntity.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return comment;
    }
}
