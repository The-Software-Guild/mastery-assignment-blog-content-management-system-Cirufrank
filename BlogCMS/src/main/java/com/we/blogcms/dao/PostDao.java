/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Author;
import com.we.blogcms.model.Post;
import com.we.blogcms.model.Status;
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
    public Post addPost(Post post);
    /**
     * Retrieves all posts from the database
     *
     * @param none
     * @return List<Post> list of post instances from the database
     */
    public List<Post> getAllPosts();
    /**
     * Retrieves all posts (active, inactive, and pending) from the database
     * that are associated with the author specified
     *
     * @param int authorId
     * @param Status... array of statuses the posts should be in
     * @return List<Post> list of post instances from the database associated 
     * with the author specified and that have the statuses specified
     */
    public List<Post> getPostsForAuthor(int authorId, Status... statuses);
    /**
     * Retrieves all posts of the specified statuses from the database
     *
     * @param none
     * @return List<Post> list of post instances with the specified 
     * statuses, from the database
     */
    public List<Post> getAllPostsForStatuses(Status... statuses);
    /**
     * Retrieves the specified amount of active and non-expired posts
     * posts from the databases
     *
     * @param int number latest posts to retrieve
     * @return List<Post> list of specified latest post instances 
     * from the database
     */
    public List<Post> getLatestPostsForStatuses(int numOfPosts, Status... statuses);
    /**
     * Retrieves all posts of the statuses specified from the database that 
     * have the tag(s) specified ordered from most recent to oldest
     *
     * @param List<Tag> list of tags to match
     * @param Status[]... array of statuses the posts must be in
     * @return List<Post> list of all posts from the 
     * database that match the tags specified and have the statuses
     * specified
     */
    public List<Post> getPostsForStatusesByTags(List<Tag> tags, Status... statuses);
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
     * @param Post post object to delete assocaited information 
     * from the database
     * @return Post object instance representing post deleted from the 
     * database, null if no post was deleted
     */
    public void deletePost(Post post);
     /**
     * Updates a post within the database
     *
     * @param Post post object instance
     * @return Post object instance representing post updated in the 
     * database, null if no post was updated
     */
    public void updatePost(Post post);
    //ABOVE FOR TESTING ONLY SINCE WE ONLY DEACTIVATE POSTS WHICH CAN BE DONE 
    //THROUGH 
   
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
