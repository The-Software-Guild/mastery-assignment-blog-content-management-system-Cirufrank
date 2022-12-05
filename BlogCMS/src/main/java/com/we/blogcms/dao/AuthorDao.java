/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Author;
import com.we.blogcms.model.Status;
import java.util.List;

/**
 *
 * @author ciruf
 */
public interface AuthorDao {
    /**
     * Adds an author to the database
     *
     * @param Author object to save to the database
     * @return Author added to the database, null otherwise
     */
    public Author addAuthor(Author author);
    /**
     * Retrieves all authors from the database
     *
     * @param none
     * @return List<Author> list of author instances from the database
     */
    public List<Author> getAllAuthors();
    /**
     * Retrieves all authors from the database
     *
     * @param none
     * @return List<Author> list of author instances from the database
     */
    public List<Author> getAllAuthorsForStatuses(Status... statuses);
    /**
     * Retrieves an author from the database
     *
     * @param int authorId
     * @return Author object instance representing author from the 
     * database, null otherwise
     */
    public Author getAuthorById(int authorId);
    /**
     * Retrieves the author of a post from the database
     *
     * @param int postId
     * @return Author object instance representing author from the 
     * database the post is associated with
     */
    public Author getPostAuthor(int postId);
    /**
     * Deletes an author from the database along with 
     * their associated data
     *
     * @param Author object instance representing an author 
     * and their assocaited data
     * @return void
     */
    public void deleteAuthor(Author author);
    /**
     * Sets an author's status to deleted within the
     * database and does so for their posts as well
     *
     * @param Author object instance representing an 
     * author to deactivate
     * @return void
     */
    public void deactivateAuthor(Author author);
    /**
     * Updates an author within the database
     *
     * @param Author author object instance
     * @return Author object instance representing author updated in the 
     * database, null if no author was updated
     */
    public void updateAuthor(Author author);
//    /**
//     * Sets the posts for an author 
//     *
//     * @param Author author object instance
//     * @return void
//     */
//    private void setPostsForAuthor(Author author);
//    /**
//     * Saves author posts to the database's postauthor table
//     *
//     * @param Author author object instance
//     * @return void
//     */
//    private void savePostsForAuthor(Author author);
}
