/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Author;
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
     * Retrieves an author from the database
     *
     * @param int authorId
     * @return Author object instance representing author from the 
     * database, null otherwise
     */
    public Author getAuthorById(int authorId);
    /**
     * Deletes an author from the database
     *
     * @param int authorId
     * @return Author object instance representing author deleted from the 
     * database, null if no author was deleted
     */
    public Author deleteAuthorById(int authorId);
    /**
     * Updates an author within the database
     *
     * @param Author author object instance
     * @return Author object instance representing author updated in the 
     * database, null if no author was updated
     */
    public Author updateAuthor(Author author);
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
