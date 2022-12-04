/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Author;
import com.we.blogcms.model.Body;
import java.util.List;

/**
 *
 * @author ciruf
 */
public interface BodyDao {
    /**
     * Adds a (post) Body to the database
     *
     * @param Body object to save to the database
     * @return Body added to the database, null otherwise
     */
    public Body addBody(Body body);
    /**
     * Retrieves all post bodies from the database
     *
     * @param none
     * @return List<Body> list of Body instances from the database
     */
    public List<Body> getAllPostBodies();
    //Don't need getAllBody's method
    /**
     * Retrieves a (post) Body from the database
     *
     * @param int bodyId
     * @return Body object instance representing body from the 
     * database, null otherwise
     */
    public Body getBodyById(int bodyId);
    /**
     * Retrieves a (post) Body from the database of the post 
     * specified
     *
     * @param int postId
     * @return Body object instance representing body from the 
     * database for post
     */
    public Body getPostBody(int postId);
    /**
     * Deletes a body from the database
     *
     * @param int bodyId
     * @return Body object instance representing body deleted from the 
     * database, null if no body was deleted
     */
    public void deleteBodyById(int bodyId);
    /**
     * Updates a body within the database
     *
     * @param Body body object instance
     * @return Body object instance representing body updated in the 
     * database, null if no body was updated
     */
    public Body updateBody(Body body);
}
