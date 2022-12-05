/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.TestApplicationConfiguration;
import com.we.blogcms.model.Author;
import com.we.blogcms.model.Tag;
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
     * Test of addPost method, of class PostDaoDB.
     */
    @Test
    public void testAddPost() {
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
     * Test of getPostById method, of class PostDaoDB.
     */
    @Test
    public void testGetPostById() {
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
