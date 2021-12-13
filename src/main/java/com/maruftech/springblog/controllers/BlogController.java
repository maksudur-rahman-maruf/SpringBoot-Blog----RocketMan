package com.maruftech.springblog.controllers;

import com.maruftech.springblog.entities.Comment;
import com.maruftech.springblog.entities.Post;
import com.maruftech.springblog.entities.User;
import com.maruftech.springblog.pojos.CommentPojo;
import com.maruftech.springblog.service.CommentService;
import com.maruftech.springblog.service.PostService;
import com.maruftech.springblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping(value="/posts")
    public List<Post> posts(){
        return postService.getAllPosts();
    }

    @GetMapping(value="/the_post/{id}")
    public Post getPostById(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PostMapping(value="/post")
    public String publishPost(@RequestBody Post post){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if(post.getDateCreated() == null)
            post.setDateCreated(new Date());
        post.setCreator(userService.getUser(userDetails.getUsername()));
//        post.setCreator(userService.getUser(currentUser));
        postService.insert(post);
        return "Post was published";
    }

    @GetMapping(value="/posts/{username}")
    public List<Post> postsByUser(@PathVariable String username){
        return postService.findByUser(userService.getUser(username));
    }

    @DeleteMapping(value = "/post/{id}")
    public boolean deletePost(@PathVariable Long id){
        return postService.deletePost(id);
    }

    @DeleteMapping(value = "/comment/{id}")
    public boolean deleteComment(@PathVariable Long id){
        return commentService.deleteComment(id);
    }


    @GetMapping(value = "/comments/{postId}")
    public List<Comment> getComments(@PathVariable Long postId){
        return commentService.getComments(postId);
    }

    @PostMapping(value = "/post/postComment")
    public boolean postComment(@RequestBody CommentPojo comment){
        Post post = postService.find(comment.getPostId());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User creator = userService.getUser(userDetails.getUsername());
        if(post == null || creator == null)
            return false;

        commentService.comment(new Comment(comment.getText(),post,creator));
        return true;
    }

}
