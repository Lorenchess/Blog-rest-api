package com.lorenchess.blogrestapi.service.implementations;

import com.lorenchess.blogrestapi.entities.Post;
import com.lorenchess.blogrestapi.exceptions.ResourceNotFoundException;
import com.lorenchess.blogrestapi.payloadDTO.PostDto;
import com.lorenchess.blogrestapi.payloadDTO.PostResponse;
import com.lorenchess.blogrestapi.repository.PostRepo;
import com.lorenchess.blogrestapi.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    public PostServiceImpl(PostRepo postRepo) {
        this.postRepo = postRepo;
    }


    @Override
    public PostDto createPost(PostDto postDto) {
        //convert DTO to entity, so we can save the new entity into db
        Post post = mapToEntity(postDto);

        //saving the entity Post into the database
        Post postEntity = postRepo.save(post);

        //convert entity to DTO and return it
        return mapToDTO(postEntity);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        //condition to sort
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepo.findAll(pageable);

        //Get content for Page object
        List<Post> listOfPosts = posts.getContent();

        //convert the list of post entities to dto
       List<PostDto> content = listOfPosts.stream().map(p -> mapToDTO(p)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(Long postId) {
        //find the post entity by id
       Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id", postId));
       //converted to dto and return it
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        //get post entity by id from the database
        Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));

        //update the post found by id in the db with the post sent in the json parameter
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        //save updated post entity to db
        Post updatedPost = postRepo.save(post);

        //convert to dto and return
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        //find the post entity by id that is requested to delete
        Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        //just delete it, the method is void since there is nothing to be returned
        postRepo.delete(post);
    }

    //Helper method to map Entity to DTO response to user
    private PostDto mapToDTO(Post post) {
        PostDto postResponse = new PostDto();
        BeanUtils.copyProperties(post, postResponse);
        return postResponse;
    }

    //Helper method to map DTO to Entity
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        return post;
    }
}
