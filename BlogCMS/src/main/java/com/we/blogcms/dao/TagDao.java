/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Author;
import com.we.blogcms.model.Status;
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
    public Tag addTag(Tag tag);
    /**
     * Retrieves all tags from the database
     *
     * @param none
     * @return List<Tag> list of tag instances from the database
     */
    public List<Tag> getAllTags();
    /**
     * Retrieves all tags from the database of a certain status
     *
     * @param Status[] array of statuses the returned tags should 
     * be in
     * @return List<Tag> list of tag instances from the database
     */
    public List<Tag> getAllTagsForStatuses(Status... statuses);
    /**
     * Retrieves all tags associated with a post from 
     * the database
     *
     * @param int postId
     * @return List<Tag> list of tag instances from 
     * the database that are associated with the 
     * specified post
     */
    public List<Tag> getPostTagsForStatuses(int postId, Status... statuses);
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
    public void deleteTagById(int tagId);
    //Don't need edit tag method
}
