package com.maruftech.springblog.service;

import com.maruftech.springblog.entities.Post;
import com.maruftech.springblog.entities.User;
import com.maruftech.springblog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public void insert(Post post) {
        postRepository.save(post);
    }

    public List<Post> findByUser(User user){
        return postRepository.findByCreatorId(user.getId());
    }

    public boolean deletePost(Long postId){
        Optional<Post> thePost = postRepository.findById(postId);
        if(thePost == null)
            return false;
        postRepository.deleteById(postId);
        return true;
    }

    public Post getPost(Long id) {
        Post thePost = postRepository.findById(id).get();
        return thePost;
    }

    public Post find(Long postId) {
        Post thePost= postRepository.findById(postId).get();
        return thePost;
    }
}
