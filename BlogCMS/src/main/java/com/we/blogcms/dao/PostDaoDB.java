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
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    final static String DELIMITER = ",";
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    AuthorDao authorDao;
    
    @Autowired
    BodyDao bodyDao;
    
    @Autowired
    TagDao tagDao;
    
    @Override
    @Transactional
    public Post addPost(Post post) {
        final String ADD_POST_SQL = "INSERT INTO post (status,"
                + "activationDate,expirationDate,title,"
                + "headline) VALUES (" + post.getStatus() + DELIMITER 
                        + post.getActivationDate() + DELIMITER + 
                        post.getExpirationDate() + DELIMITER + 
                        post.getTitle() + DELIMITER + 
                        post.getHeadline() + ")";
        jdbc.update(ADD_POST_SQL);
        int postId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        post.setPostId(postId);
        return post;
    }
    
    @Override
    public List<Post> getAllPosts() {
        final String GET_POSTS_SQL = "SELECT * FROM posts "
                + "WHERE status NOT IN ('" + Status.deleted.toString() + "');";
        List<Post> allPosts = jdbc.query(GET_POSTS_SQL, new PostMapper());
        setTagsBodyAuthorForPosts(allPosts);
        return allPosts;
    }
    
    @Override
    public List<Post> getPostsForStatus(Status status) {
        final String GET_STATUS_POSTS_SQL = "SELECT * FROM post "
                + "WHERE status IN ('" + status + 
                "') ORDER BY createdAt DESC;";
        List<Post> allPostsForStatus = jdbc.query(GET_STATUS_POSTS_SQL, 
                                    new PostMapper());
        setTagsBodyAuthorForPosts(allPostsForStatus);
        return allPostsForStatus;
    }
    
    @Override
    public List<Post> getLatestShowablePosts(int numOfPosts) {
        final String LATEST_SHOWABLE_POSTS_SQL = "SELECT * FROM "
                + "post WHERE status IN ('" + Status.active.toString()
                + "') AND activationDate >= CURRENT_TIMESTAMP "
                + "AND expirationDate < CURRENT_TIMESTAMP ORDER BY createdAt "
                + "DESC LIMIT " + numOfPosts + ";";
        List<Post> latestShowablePosts = jdbc.query(LATEST_SHOWABLE_POSTS_SQL,
                new PostMapper(), numOfPosts);
        setTagsBodyAuthorForPosts(latestShowablePosts);
        return latestShowablePosts;
    }
    
    @Override
    public List<Post> getShowablePosts() {
                final String GET_SHOWABLE_POSTS_SQL = "SELECT * FROM "
                + "post WHERE status IN ('" + Status.active.toString()
                + "') AND activationDate >= CURRENT_TIMESTAMP "
                + "AND expirationDate < CURRENT_TIMESTAMP ORDER BY createdAt DESC;";
        List<Post> showablePosts = jdbc.query(GET_SHOWABLE_POSTS_SQL,
                new PostMapper());
        setTagsBodyAuthorForPosts(showablePosts);
        return showablePosts;
    }
    
    @Override
    public List<Post> getShowablePostsByTags(List<Tag> tags) {
        final String GET_SHOWABLE_POSTS_BY_TAGS_SQL = "SELECT p.* "
                + "FROM post p INNER JOIN posttag pt ON "
                + "p.postId = pt.postId WHERE pt.tagId "
                + "NOT IN " + createNotInTagIdText(tags) + 
                " AND p.status IN ('" + Status.active.toString() +
                "') ORDER BY createdAt DESC;";
        final List<Post> filteredShowablePosts = jdbc.query(
                GET_SHOWABLE_POSTS_BY_TAGS_SQL, new PostMapper());
        setTagsBodyAuthorForPosts(filteredShowablePosts);
        return filteredShowablePosts;
    }
    
    @Override
    public Post getPostById(int postId) {
        final String GET_POST_BY_ID_SQL = "SELECT * FROM post "
                + "WHERE postId = ?;";
        final Post post = jdbc.queryForObject(GET_POST_BY_ID_SQL,
                new PostMapper(), postId);
        setTagsBodyAuthorForPost(post);
        return post;
    }
    
    @Override
    @Transactional
    public void updatePost(Post post) {
        updatePostTags(post);
        final String UPDATE_POST_STATUS_SQL = "UPDATE post "
                + "SET status = ?, title = ?, headline = ?, "
                + "activationDate = ?, expirationDate = ? "
                + "WHERE postId = ?;";
        jdbc.update(UPDATE_POST_STATUS_SQL, post.getStatus(), 
                post.getTitle(), post.getHeadline(), 
                post.getActivationDate(), 
                post.getExpirationDate(),post.getPostId());
    }
    
    @Override
    public void deletePostById(int postId) {
        final String DELETE_POST_BY_ID_SQL = "DELETE FROM "
                + "post WHERE postId = ?;";
        jdbc.update(DELETE_POST_BY_ID_SQL, postId);
    }
    
    private String createNotInTagIdText(List<Tag> tagsParam) {
        String notInString = "(";
        for (int index = 0; index < tagsParam.size(); index += 1) {
            final int FIRST_INDEX = 0, LAST_INDEX = tagsParam.size() - 1;
            final Tag currentTag = tagsParam.get(index);
            if (index == FIRST_INDEX) {
                notInString += currentTag.getTagId();
                continue;
            } 
            if (index == LAST_INDEX) {
                notInString += currentTag.getTagId() + ")";
                break;
            }
            notInString += currentTag.getTagId() + DELIMITER;
        }
        return notInString;
    }
    
    private void updatePostTags(Post post) {
        removedNeededPostTags(post);
        final List<Tag> existingPostTags = getcurrentPostTags(post);
        final List<Tag> tagsToAdd = getTagsToAdd(post.getTags(), 
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
            
    private void removedNeededPostTags(Post post) {
        final String UPDATE_POST_TAGS_SQL = "DELETE FROM posttag pt"
                + "WHERE pt.postId = ? AND pt.tagId NOT IN " + 
                createNotInTagIdText(post.getTags()) + ";";
        jdbc.update(UPDATE_POST_TAGS_SQL, post.getPostId());
    }
    
    private List<Tag> getcurrentPostTags(Post post) {
        final String CURRENT_POST_TAGS_SQL = "SELECT t.* FROM "
                + "tag t INNER JOIN posttag pt ON t.tagId = pt.tagId "
                + "AND p.postId = ?;";
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
        final List<Tag> postTags = tagDao.getPostTags(post.getPostId());
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
            post.setActivationDate(rs.getTimestamp("activationDate").toLocalDateTime());
            post.setExpirationDate(rs.getTimestamp("expirationDate").toLocalDateTime());
            post.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            post.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
            return post;      
        }
    } 
}
