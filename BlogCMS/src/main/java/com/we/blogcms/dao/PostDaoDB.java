/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.dao.TagDaoDB.TagMapper;
import com.we.blogcms.model.Author;
import com.we.blogcms.model.Body;
import com.we.blogcms.model.Post;
import com.we.blogcms.model.Status;
import com.we.blogcms.model.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ciruf
 */
@Repository
public class PostDaoDB implements PostDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    DaoHelper daoHelper;
    
    AuthorDao authorDao;
    
    @Autowired
    BodyDao bodyDao;
    
    @Autowired
    TagDao tagDao;
    
    @Autowired
    public PostDaoDB(@Lazy AuthorDao authorDao) {
        this.authorDao = authorDao;
    }
    
    @Override
    @Transactional
    public Post addPost(Post post) {
        String ADD_POST_SQL = "";
        if (post.getActivationDate() != null && 
                post.getExpirationDate() != null) {
            ADD_POST_SQL = "INSERT INTO post(status,"
                + "activationDate,expirationDate,title,"
                + "headline) VALUES(" + daoHelper.SINGLE_QUOTE + post.getStatus() + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + Timestamp.valueOf(post.getActivationDate()) 
                + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + Timestamp.valueOf(post.getExpirationDate())
                + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + post.getTitle() + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + post.getHeadline() + daoHelper.SINGLE_QUOTE + ");";
        }
        if (post.getActivationDate() == null &&
                post.getExpirationDate() != null) {
           ADD_POST_SQL = "INSERT INTO post(status,"
                + "expirationDate,title,"
                + "headline) VALUES(" + daoHelper.SINGLE_QUOTE + post.getStatus() + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + Timestamp.valueOf(post.getExpirationDate())
                + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + post.getTitle() + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + post.getHeadline() + daoHelper.SINGLE_QUOTE + ");";
        }
        
        if (post.getActivationDate() != null && 
                post.getExpirationDate() == null) {
        ADD_POST_SQL = "INSERT INTO post(status,"
                + "activationDate,title,"
                + "headline) VALUES(" + daoHelper.SINGLE_QUOTE + post.getStatus() + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + Timestamp.valueOf(post.getActivationDate()) 
                + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + post.getTitle() + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + post.getHeadline() + daoHelper.SINGLE_QUOTE + ");";
        }
        if (post.getActivationDate() == null && 
                post.getExpirationDate() == null) {
            ADD_POST_SQL = "INSERT INTO post(status,"
                + "title,"
                + "headline) VALUES(" + daoHelper.SINGLE_QUOTE + post.getStatus() + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + post.getTitle() + daoHelper.SINGLE_QUOTE + daoHelper.DELIMITER
                + daoHelper.SINGLE_QUOTE + post.getHeadline() + daoHelper.SINGLE_QUOTE + ");";
        }
        jdbc.update(ADD_POST_SQL);
        int postId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        post.setPostId(postId);
        updatePostTags(post);
        addPostBody(post.getBody().getBodyId(), post.getPostId());
        addPostAuthor(post.getAuthor().getAuthorId(), post.getPostId());
        post = getPostById(post.getPostId());
        return post;
    }
    
    @Override
    public List<Post> getAllPosts() {
        final String GET_POSTS_SQL = "SELECT * FROM post;";
        try {
            List<Post> allPosts = jdbc.query(GET_POSTS_SQL, new PostMapper());
            setTagsBodyAuthorForPosts(allPosts);
            return allPosts;
        } catch(DataAccessException error) {
            return null;
        }
    }
    
    @Override
    public List<Post> getPostsForAuthor(int authorId, Status... statuses) {
        final String GET_POSTS_FOR_AUTHOR_SQL = "SELECT p.* "
                + "FROM post p INNER JOIN postauthor pa ON "
                + "p.postId = pa.postId WHERE "
                + "pa.authorId = ? AND p.status IN "
                + daoHelper.createInStatusText(statuses) + ";";
        try {
            final List<Post> postsForAuthor = jdbc.query(
                GET_POSTS_FOR_AUTHOR_SQL, new PostMapper(),
                authorId);
            setTagsBodyAuthorForPosts(postsForAuthor);
            return postsForAuthor;
        } catch(DataAccessException error) {
            return null;
        }
    }
    
    @Override
    public List<Post> getAllPostsForStatuses(Status... statuses) {
        final String GET_STATUS_POSTS_SQL = "SELECT * FROM post "
                + "WHERE status IN " + daoHelper.createInStatusText(statuses) + 
                " AND (activationDate <= CURRENT_TIMESTAMP OR activationDate IS NULL)"
                + "AND (expirationDate > CURRENT_TIMESTAMP OR expirationDate IS NULL) "
                + "ORDER BY createdAt DESC;";
        try {
            List<Post> allPostsForStatus = jdbc.query(GET_STATUS_POSTS_SQL, 
                                    new PostMapper());
            setTagsBodyAuthorForPosts(allPostsForStatus);
            return allPostsForStatus;
        } catch(DataAccessException error) {
            return null;
        }
    }
    
    @Override
    public List<Post> getLatestPostsForStatuses(int numOfPosts, Status... statuses) {
        final String LATEST_SHOWABLE_POSTS_SQL = "SELECT * FROM "
                + "post WHERE status IN " + daoHelper.createInStatusText(statuses)
                + " AND (activationDate <= CURRENT_TIMESTAMP OR activationDate IS NULL)"
                + " AND (expirationDate > CURRENT_TIMESTAMP OR expirationDate IS NULL) ORDER BY createdAt "
                + "DESC LIMIT ?;";
        try {
            List<Post> latestShowablePosts = jdbc.query(LATEST_SHOWABLE_POSTS_SQL,
                new PostMapper(), numOfPosts);
            setTagsBodyAuthorForPosts(latestShowablePosts);
            return latestShowablePosts;
        } catch(DataAccessException error) {
            return null;
        }
    }
    
    @Override
    public List<Post> getPostsForStatusesByTags(List<Tag> tags, Status... statuses) {
        final String GET_SHOWABLE_POSTS_BY_TAGS_SQL = "SELECT p.* "
                + "FROM post p INNER JOIN posttag pt ON "
                + "p.postId = pt.postId AND pt.tagId "
                + "IN " + createInTagIdText(tags) + 
                " WHERE p.status IN " + daoHelper.createInStatusText(statuses) + 
                " AND (activationDate <= CURRENT_TIMESTAMP OR activationDate IS NULL)"
                + "AND (expirationDate > CURRENT_TIMESTAMP OR expirationDate IS NULL) "
                + "ORDER BY createdAt DESC;";
        try {
            final List<Post> filteredShowablePosts = jdbc.query(
                GET_SHOWABLE_POSTS_BY_TAGS_SQL, new PostMapper());
            setTagsBodyAuthorForPosts(filteredShowablePosts);
            return filteredShowablePosts;
        } catch(DataAccessException error) {
            return null;
        }
    }
    
    @Override
    public Post getPostById(int postId) {
        final String GET_POST_BY_ID_SQL = "SELECT * FROM post "
                + "WHERE postId = ?;";
        try {
            final Post post = jdbc.queryForObject(GET_POST_BY_ID_SQL,
                new PostMapper(), postId);
            setTagsBodyAuthorForPost(post);
            return post;
        } catch(DataAccessException error) {
            return null;
        }
    }
    
    @Override
    @Transactional
    public void updatePost(Post post) {
        updatePostTags(post);
        final String UPDATE_POST_STATUS_SQL = "UPDATE post "
                + "SET status = ?, title = ?, headline = ?, "
                + "activationDate = ?, expirationDate = ? "
                + "WHERE postId = ?;";
        jdbc.update(UPDATE_POST_STATUS_SQL, post.getStatus().toString(), 
                post.getTitle(), post.getHeadline(), 
                post.getActivationDate(), 
                post.getExpirationDate(),post.getPostId());
    }
    
    @Override
    @Transactional
    public void deletePost(Post post) {
        deletePostTags(post);
        deletePostAuthor(post.getPostId());
        deletePostBody(post);
        final String DELETE_POST_BY_ID_SQL = "DELETE FROM "
                + "post WHERE postId = ?;";
        jdbc.update(DELETE_POST_BY_ID_SQL, post.getPostId());
    }
    
    private void deletePostTags(Post post) {
        for (Tag tag: post.getTags()) {
            tagDao.deleteTagById(tag.getTagId());
        }
    }
    
    private void deletePostAuthor(int postId) {
        final String DELETE_POST_AUTHOR_SQL = "DELETE FROM postauthor "
                + "WHERE postId = ?;";
        jdbc.update(DELETE_POST_AUTHOR_SQL, postId);
    }
    
    private void deletePostBody(Post post) {
        bodyDao.deleteBodyById(post.getBody().getBodyId());
    }
    private String createInTagIdText(List<Tag> tagsParam) {
        String notInString = "(";
        for (int index = 0; index < tagsParam.size(); index += 1) {
            final int FIRST_INDEX = 0, LAST_INDEX = tagsParam.size() - 1;
            final Tag currentTag = tagsParam.get(index);
            if (index == LAST_INDEX) {
                notInString += currentTag.getTagId() + ")";
                break;
            }
            notInString += currentTag.getTagId() + daoHelper.DELIMITER;
        }
        return notInString;
    }
    
    private void updatePostTags(Post post) {
        List<Tag> tagsToAdd = new ArrayList<>();
        if (post.getTags() == null) return;
        removedNeededPostTags(post);
        final List<Tag> existingPostTags = getcurrentPostTags(post);
        tagsToAdd = getTagsToAdd(post.getTags(), 
                existingPostTags);
        addNewPostTags(tagsToAdd, post.getPostId());
    }
    
    
    private void addNewPostTags(List<Tag> tagsToAdd, int postId) {
        for (Tag tag: tagsToAdd) {
            addPostTag(tag.getTagId(), postId);
        }
    }
    
    private void addPostTag(int tagId, int postId) {
        final String ADD_TAG_SQL = "INSERT INTO posttag(tagId,postId) "
                + "VALUES(?,?);";
        jdbc.update(ADD_TAG_SQL, tagId, postId);
    }
    
    private void addPostBody(int bodyId, int postId) {
        final String ADD_BODY_SQL = "INSERT INTO postbody(bodyId,postId) "
                + "VALUES(?,?);";
        jdbc.update(ADD_BODY_SQL, bodyId, postId);
    }
    
    private void addPostAuthor(int authorId, int postId) {
        final String ADD_AUTHOR_SQL = "INSERT INTO postauthor(authorId,postId) "
                + "VALUES(?,?);";
        jdbc.update(ADD_AUTHOR_SQL, authorId, postId);
    }
            
    private void removedNeededPostTags(Post post) {
        final String UPDATE_POST_TAGS_SQL = "DELETE FROM posttag pt"
                + " WHERE pt.postId = ? AND pt.tagId NOT IN " + 
                createInTagIdText(post.getTags()) + ";";
        jdbc.update(UPDATE_POST_TAGS_SQL, post.getPostId());
    }
    
    private List<Tag> getcurrentPostTags(Post post) {
        final String CURRENT_POST_TAGS_SQL = "SELECT t.* FROM "
                + "tag t INNER JOIN posttag pt ON t.tagId = pt.tagId "
                + "AND pt.postId = ?;";
        final List<Tag> currentTags = jdbc.query(CURRENT_POST_TAGS_SQL, 
                new TagMapper(), post.getPostId());
        return currentTags;
    }
    
    private List<Tag> getTagsToAdd(List<Tag> currentPostTags, List<Tag> existingTags) {
        final List<Tag> tagsToUpdate = new ArrayList<>();
        for (Tag tag: currentPostTags) {
            if (!existingTags.contains(tag)) {
                tagsToUpdate.add(tag);
            }
        }
        return tagsToUpdate;
    }
    
    
    private void setTagsBodyAuthorForPosts(List<Post> posts) {
        for (Post post: posts) {
            setTagsBodyAuthorForPost(post);
        }
    }
    
    private void setTagsBodyAuthorForPost(Post post) {
        setTagsForPost(post);
        setBodyForPost(post);
        setAuthorForPost(post);
    }
    
    private void setTagsForPost(Post post) {
        final List<Tag> postTags = tagDao.getPostTagsForStatuses(post.getPostId(),Status.active, Status.deleted);
        post.setTags(postTags);
    }
    private void setBodyForPost(Post post) {
        final Body postBody = bodyDao.getPostBody(post.getPostId());
        post.setBody(postBody);
    }
    private void setAuthorForPost(Post post) {
        final Author postAuthor = authorDao.getPostAuthor(post.getPostId());
        post.setAuthor(postAuthor);
    }
    
    final public static class PostMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int index) throws SQLException {
            final Post post = new Post();
            post.setPostId(rs.getInt("postId"));
            post.setTitle(rs.getString("title"));
            post.setHeadline(rs.getString("headline"));
            post.setStatus(Status.valueOf(rs.getString("status")));
            if (rs.getTimestamp("activationDate") != null ) {
                post.setActivationDate(
                    rs.getTimestamp("activationDate").toLocalDateTime());
            }
            
            if (rs.getTimestamp("expirationDate") != null) {
                post.setExpirationDate(rs.getTimestamp("expirationDate").toLocalDateTime());
            }
            
            post.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            post.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
            return post;      
        }
    } 
}
