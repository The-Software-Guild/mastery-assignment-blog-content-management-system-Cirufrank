/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Author;
import com.we.blogcms.model.Tag;
import java.util.List;

/**
 *
 * @author ciruf
 */
public interface TagDao {
    /**
     * Adds a tag to the database
     *
     * @param Tag object to save to the database
     * @return Tag added to the database, null otherwise
     */
    public Tag createTag();
    /**
     * Retrieves all tags from the database
     *
     * @param none
     * @return List<Tag> list of tag instances from the database
     */
    public List<Tag> getAllTags();
    /**
     * Retrieves a tag from the database
     *
     * @param int tagId
     * @return Tag object instance representing tag from the 
     * database, null otherwise
     */
    public Tag getTagById(int tagId);
    /**
     * Deletes a tag from the database
     *
     * @param int tagId
     * @return Tag object instance representing tag deleted from the 
     * database, null if no tag was deleted
     */
    public Tag deleteTagById(int tagId);
    //Don't need edit tag method
}
