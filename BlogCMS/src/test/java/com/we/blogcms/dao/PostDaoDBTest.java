/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.TestApplicationConfiguration;
import com.we.blogcms.model.Author;
import com.we.blogcms.model.Body;
import com.we.blogcms.model.Post;
import com.we.blogcms.model.Tag;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ciruf
 */
@ExtendWith(PostListParameterResolver.class)
@ExtendWith(BodyParameterResolver.class)
@ExtendWith(AuthorParameterResolver.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class PostDaoDBTest {
    final List<Tag> testTags = new ArrayList<>();
    final Tag tag1 = new Tag();
    final Tag tag2 = new Tag();
    
    @Autowired
    TagDao tagDao;
    
    @Autowired
    BodyDao bodyDao;
    
    @Autowired
    PostDao postDao;
    
    @Autowired
    AuthorDao authorDao;
    
    public PostDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        final List<Author> allAuthors = authorDao.getAllAuthors();
        for (Author author: allAuthors) {
            authorDao.deleteAuthor(author);
        }
        
        
        final List<Tag> allTags = tagDao.getAllTags();
        for (Tag tag: allTags) {
            tagDao.deleteTagById(tag.getTagId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addPost and getPostById methods, of class PostDaoDB.
     */
    @Test
    public void testAddGetPost(List<Post> postList, Body body, Author author) {
        
        final Post postToAdd = postList.get(0);
        body = bodyDao.addBody(body);
        author = authorDao.addAuthor(author);
        tag1.setTag("This-is-a-test-tag-1");
        tag2.setTag("This-is-a-test-tag-2");
        testTags.add(tag1);
        testTags.add(tag2);
        final Tag tag1ToAdd = testTags.get(0);
        final Tag tag2ToAdd = testTags.get(1);
        
        final Tag tag1Added = tagDao.addTag(tag1ToAdd);
        final Tag tag2Added = tagDao.addTag(tag2ToAdd);
        testTags.set(0, tag1Added);
        testTags.set(1, tag2Added);
        
        postToAdd.setBody(body);
        postToAdd.setAuthor(author);
        postToAdd.setTags(testTags);
        
        final Post postAdded = postDao.addPost(postToAdd);
        postToAdd.setCreatedAt(postAdded.getCreatedAt());
        postToAdd.setUpdatedAt(postAdded.getUpdatedAt());
        postToAdd.setAuthor(postAdded.getAuthor());
        postToAdd.setPostId(postAdded.getPostId());
        postToAdd.setActivationDate(postAdded.getActivationDate());
        postToAdd.setExpirationDate(postAdded.getExpirationDate());
        assertEquals(postToAdd, postAdded);
        final Post postRetrieved = postDao.getPostById(postAdded.getPostId());
        assertEquals(postAdded, postRetrieved);
        
        
    }

    /**
     * Test of getAllPosts method, of class PostDaoDB.
     */
    @Test
    public void testGetAllPosts() {
    }

    /**
     * Test of getPostsForAuthor method, of class PostDaoDB.
     */
    @Test
    public void testGetPostsForAuthor() {
    }

    /**
     * Test of getAllPostsForStatuses method, of class PostDaoDB.
     */
    @Test
    public void testGetAllPostsForStatuses() {
    }

    /**
     * Test of getLatestPostsForStatuses method, of class PostDaoDB.
     */
    @Test
    public void testGetLatestPostsForStatuses() {
    }

    /**
     * Test of getPostsForStatusesByTags method, of class PostDaoDB.
     */
    @Test
    public void testGetPostsForStatusesByTags() {
    }


    /**
     * Test of updatePost method, of class PostDaoDB.
     */
    @Test
    public void testUpdatePost() {
    }

    /**
     * Test of deletePostById method, of class PostDaoDB.
     */
    @Test
    public void testDeletePostById() {
    }
    
}
