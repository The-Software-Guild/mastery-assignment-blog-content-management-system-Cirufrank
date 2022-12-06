/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.we.blogcms.model.Author;
import com.we.blogcms.model.Post;
import com.we.blogcms.model.Role;
import com.we.blogcms.model.Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ciruf
 */
@Repository
public class AuthorDaoDB implements AuthorDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    PostDao postDao;
    
    @Autowired
    DaoHelper daoHelper;
    
    @Override
    @Transactional
    public Author addAuthor(Author author) {
        final String ADD_AUTHOR_SQL = "INSERT INTO "
                + "author (firstName, lastName, "
                + "displayName, email, "
                + "password) VALUES(?,?,?,?,?)";
        jdbc.update(ADD_AUTHOR_SQL, 
                author.getFirstName(),
                author.getLastName(),
                author.getDisplayName(),
                author.getEmail(),
                author.getPassword());
        final int authorId = jdbc.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);
        author = getAuthorById(authorId);
        return author;
    }
    
    @Override
    public void updateAuthor(Author author) {
        final String UPDATE_AUTHOR_SQL = "UPDATE author SET "
                + "status = ?, firstName = ?, lastName = ?, role = ?,"
                + " displayName = ?, email = ?, password = ? "
                + "WHERE authorId = ?";
        jdbc.update(UPDATE_AUTHOR_SQL, 
                author.getStatus(),
                author.getFirstName(), 
                author.getLastName(),
                author.getRole(),
                author.getDisplayName(),
                author.getEmail(),
                author.getPassword(), 
                author.getAuthorId());
    }
    
    @Override
    @Transactional
    public void deactivateAuthor(Author author) {
        final String DEACTIVATE_AUTHOR_SQL = "UPDATE "
                + "author SET status = '" + Status.deleted.toString()
                + "' WHERE authorId = ?;";
        jdbc.update(DEACTIVATE_AUTHOR_SQL, author.getAuthorId());
        deactivatePostsForAuthor(author);
    }
    
    @Override
    @Transactional
    public void deleteAuthor(Author author) {
        deleteAuthorPosts(author);
        final String DELETE_AUTHOR_SQL = "DELETE FROM "
                + "author WHERE authorId = ?;";
        jdbc.update(DELETE_AUTHOR_SQL, author.getAuthorId());
    }
    
    private void deleteAuthorPosts(Author author) {
        for (Post post: author.getPosts()) {
            postDao.deletePost(post);
        }
    }
    
    @Override
    @Transactional
    public List<Author> getAllAuthors() {
        final String GET_ALL_AUTHORS_SQL = "SELECT * FROM "
                + "author;";
        final List<Author> allAuthors = jdbc.query(
                GET_ALL_AUTHORS_SQL, new AuthorMapper());
        setPostsForAllAuthorsTesting(allAuthors);
        return allAuthors;
    }
    
    @Override
    @Transactional
    public List<Author> getAllAuthorsForStatuses(Status... statuses) {
        final String GET_ALL_AUTHORS_FOR_STATUSES_SQL = "SELECT * FROM "
                + "author WHERE status IN " + daoHelper.createInStatusText(statuses)
                + ";";
        final List<Author> authorsForStatuses = jdbc.query(
                GET_ALL_AUTHORS_FOR_STATUSES_SQL, new AuthorMapper());
        setPostsForAuthors(authorsForStatuses);
        return authorsForStatuses;
        
    }
    
    @Override
    @Transactional
    public Author getAuthorById(int authorId) {
        final String GET_AUTHOR_BY_ID_SQL = "SELECT * FROM "
                + "author WHERE authorId = ?;";
        final Author author = jdbc.queryForObject(GET_AUTHOR_BY_ID_SQL, 
                new AuthorMapper(), authorId);
        setPostsForAuthor(author);
        return author;
    }
    
    @Override
    @Transactional
    public Author getPostAuthor(int postId) {
        final String GET_POST_AUTHOR_SQL = "SELECT a.* FROM "
                + "author a INNER JOIN postauthor pa ON "
                + "a.authorId = pa.authorId WHERE postId = ?;";
        final Author postAuthor = jdbc.queryForObject(
                GET_POST_AUTHOR_SQL, new AuthorMapper(), postId);
        return postAuthor;
    }
    
    //This method is for if an author deactivates their 
    //account
    //Their data and post data will remain, but will show 
    //as having a status of deleted in the database
    private void deactivatePostsForAuthor(Author author) {
        for (Post post: author.getPosts()) {
            post.setStatus(Status.deleted);
            postDao.updatePost(post);
        }
    }
    
    private void setPostsForAuthor(Author author) {
        final List<Post> postsForAuthor = postDao.getPostsForAuthor(
                author.getAuthorId(), Status.active,Status.pending,
                Status.inactive);
        author.setPosts(postsForAuthor);
    }
    
    private void setPostsForAuthorTesting(Author author) {
        final List<Post> postsForAuthor = postDao.getPostsForAuthor(
                author.getAuthorId(), Status.active,Status.pending,
                Status.inactive, Status.deleted);
        author.setPosts(postsForAuthor);
    }
    
    private void setPostsForAuthors(List<Author> authors) {
        for (Author author: authors) {
            setPostsForAuthor(author);
        }
    }
    
    private void setPostsForAllAuthorsTesting(List<Author> authors) {
        for (Author author: authors) {
            setPostsForAuthorTesting(author);
        }
    }
    
    final public static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int index) throws SQLException {
            Author author = new Author();
            author.setAuthorId(rs.getByte("authorId"));
            author.setStatus(Status.valueOf(rs.getString("status")));
            author.setFirstName(rs.getString("firstName"));
            author.setLastName(rs.getString("lastName"));
            author.setDisplayName(rs.getString("displayName"));
            author.setRole(Role.valueOf(rs.getString("role")));
            author.setEmail(rs.getString("email"));
            author.setPassword(rs.getString("password"));
            author.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            author.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
            return author;
        }
    }
    
}
