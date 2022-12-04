/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Author;
import com.we.blogcms.model.Post;
import com.we.blogcms.model.Tag;
import java.util.List;

/**
 *
 * @author ciruf
 */
public interface PostDao {
    /**
     * Adds a post to the database
     *
     * @param Post object to save to the database
     * @return Post added to the database, null otherwise
     */
    public Post addPost();
    /**
     * Retrieves all posts (active, inactive, and pending) from the database
     *
     * @param none
     * @return List<Post> list of post instances from the database
     */
    public List<Post> getAllPosts();
    /**
     * Retrieves all active posts from the database
     *
     * @param none
     * @return List<Post> list of post instances with an 
     * active status from the database
     */
    public List<Post> getAllActivePosts();
    /**
     * Retrieves the specified amount of active and non-expired posts
     * posts from the databases
     *
     * @param int number latest posts to retrieve
     * @return List<Post> list of specified latest post instances 
     * from the database
     */
    public List<Post> getLatestShowablePosts(int numOfPosts);
    /**
     * Retrieves all active, non-expired posts from the database,
     * ordered from most recent to oldest
     *
     * @param none
     * @return List<Post> list of all showable posts from the 
     * database
     */
    public List<Post> getShowablePosts();
    /**
     * Retrieves all active, non-expired posts from the database that 
     * have the tag(s) specified ordered from most recent to oldest
     *
     * @param none
     * @return List<Post> list of all showable posts from the 
     * database that match the tags specified
     */
    public List<Post> getShowablePostsByTags(List<Tag> tags);
    /**
     * Retrieves all pending posts from the database
     *
     * @param none
     * @return List<Post> list of post instances with a
     * pending status from the database
     */
    public List<Post> getAllPendingPosts();
    /**
     * Retrieves all inactive posts from the database
     *
     * @param none
     * @return List<Post> list of post instances with an
     * inactive status from the database
     */
    public List<Post> getAllInactivePosts();
    /**
     * Retrieves an author from the database
     *
     * @param int postId
     * @return Post object instance representing post from the 
     * database, null otherwise
     */
    public Post getPostById(int postId);
    /**
     * Gives post in the database a status of deleted
     *
     * @param int postId
     * @return Post object instance representing post deleted from the 
     * database, null if no post was deleted
     */
    public Post deletePostById(int postId);
    /**
     * Updates a post within the database
     *
     * @param Post post object instance
     * @return Post object instance representing post updated in the 
     * database, null if no post was updated
     */
    public Author updatePost(Post post);
//    /**
//     * Sets a posts' body object
//     *
//     * @param Post post to set the body for
//     * @return void
//     */
//    private void setBodyForPost(Post post);
//    /**
//     * Sets a posts' tags object list
//     *
//     * @param Post post to set the tags for
//     * @return void
//     */
//    private void setTagsForPost(Post post);
        /**
//     * Saves a posts' body object to the postbody table 
//     * within the database
//     *
//     * @param Post post to save the body for
//     * @return void
//     */
//    private void saveBodyForPost(Post post);
//    /**
//     * Save a posts' tags to the posttage table in the 
//     * database
//     *
//     * @param Post post to save the tags for
//     * @return void
//     */
//    private void saveTagsForPost(Post post);
}
