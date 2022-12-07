/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.TestApplicationConfiguration;
import com.we.blogcms.model.Author;
import com.we.blogcms.model.Body;
import com.we.blogcms.model.Post;
import com.we.blogcms.model.Status;
import com.we.blogcms.model.Tag;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author ciruf
 */
@ExtendWith(PostListParameterResolver.class)
@ExtendWith(BodyParameterResolver.class)
@ExtendWith(AuthorParameterResolver.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class AuthorDaoDBTest {
    final List<Tag> testTags = new ArrayList<>();
    final Tag tag1 = new Tag();
    final Tag tag2 = new Tag();
    
    @Autowired
    AuthorDao authorDao;
    
    @Autowired
    TagDao tagDao;
    
    @Autowired
    BodyDao bodyDao;
    
    @Autowired
    PostDao postDao;
    
    public AuthorDaoDBTest() {
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
        final List<Author> allAuthors = authorDao.getAllAuthors();
        for (Author author: allAuthors) {
            authorDao.deleteAuthor(author);
        }
        
        
        final List<Tag> allTags = tagDao.getAllTags();
        for (Tag tag: allTags) {
            tagDao.deleteTagById(tag.getTagId());
        }
    }

    /**
     * Test of addAuthor and getAuthorById methods, of class AuthorDaoDB.
     */
    @Test
    public void testAddGetAuthor(Author author) {
        final Author authorAdded = authorDao.addAuthor(author);
        final Author authorRetrieved = authorDao.getAuthorById(authorAdded.getAuthorId());
        author.setAuthorId(authorAdded.getAuthorId());
        author.setCreatedAt(authorAdded.getCreatedAt());
        author.setUpdatedAt(authorAdded.getUpdatedAt());
        author.setRole(authorAdded.getRole());
        author.setStatus(authorAdded.getStatus());
        author.setPosts(authorAdded.getPosts());
        assertEquals(author, authorAdded);
        assertEquals(authorAdded, authorRetrieved);
    }

    /**
     * Test of updateAuthor method, of class AuthorDaoDB.
     */
    @Test
    public void testUpdateAuthor(List<Post> postList, Body body, Author author) {
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
        
        final List<Post> addedPosts = new ArrayList<>();
        final Set<Post> postSet = new HashSet<Post>(postList);
        for (Post post: postSet) {
            post.setBody(body);
            post.setAuthor(author);
            post.setTags(testTags);
            addedPosts.add(postDao.addPost(post));
        }
        author.setDisplayName("Updated displayName");
        authorDao.updateAuthor(author);
        final Author authorUpdated = authorDao.getAuthorById(author.getAuthorId());
        author.setPosts(authorUpdated.getPosts());
        assertEquals(authorUpdated, author);
    }

    /**
     * Test of deactivateAuthor method, of class AuthorDaoDB.
     */
    @Test
    public void testDeactivateAuthor(List<Post> postList, Body body, Author author) {
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
        
        final List<Post> addedPosts = new ArrayList<>();
        final Set<Post> postSet = new HashSet<Post>(postList);
        for (Post post: postSet) {
            post.setBody(body);
            post.setAuthor(author);
            post.setTags(testTags);
            addedPosts.add(postDao.addPost(post));
        }
        author = authorDao.getAuthorById(author.getAuthorId());
        authorDao.deactivateAuthor(author);
        final Author deactivatedAuthor = authorDao.getAuthorById(author.getAuthorId());
        assertTrue(deactivatedAuthor.getPosts().size() == 0);
        assertTrue(deactivatedAuthor.getStatus() == Status.deleted);
    }

    /**
     * Test of deleteAuthor method, of class AuthorDaoDB.
     */
    @Test
    public void testDeleteAuthor(List<Post> postList, Body body, Author author) {
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
        
        final List<Post> addedPosts = new ArrayList<>();
        final Set<Post> postSet = new HashSet<Post>(postList);
        for (Post post: postSet) {
            post.setBody(body);
            post.setAuthor(author);
            post.setTags(testTags);
            addedPosts.add(postDao.addPost(post));
        }
        authorDao.deleteAuthor(author);
        final Author deletedAuthor = authorDao.getAuthorById(author.getAuthorId());
        assertTrue(deletedAuthor == null);
    }

    /**
     * Test of getAllAuthors method, of class AuthorDaoDB.
     */
    @Test
    public void testGetAllAuthors(List<Post> postList, Body body, Author author) {
        body = bodyDao.addBody(body);
        author = authorDao.addAuthor(author);
        final Author author2 = authorDao.addAuthor(author);

        final List<Author> allAuthors = authorDao.getAllAuthors();
        assertTrue(allAuthors.size() == 2);
        assertTrue(allAuthors.contains(author));
        assertTrue(allAuthors.contains(author2));
    }

    /**
     * Test of getAllAuthorsForStatuses method, of class AuthorDaoDB.
     */
    @Test
    public void testGetAllAuthorsForStatuses(Author author) {
        final Author activeAuthor = authorDao.addAuthor(author);
        activeAuthor.setStatus(Status.active);
        authorDao.updateAuthor(activeAuthor);
        final Author inactiveAuthor = authorDao.addAuthor(author);
        inactiveAuthor.setStatus(Status.inactive);
        authorDao.updateAuthor(inactiveAuthor);
        final Author deletedAuthor = authorDao.addAuthor(author);
        deletedAuthor.setStatus(Status.deleted);
        authorDao.updateAuthor(deletedAuthor);
        final List<Author> allDeletedAuthors = authorDao.getAllAuthorsForStatuses(Status.deleted);
        final List<Author> allInactiveAuthors = authorDao.getAllAuthorsForStatuses(Status.inactive);
        final List<Author> allActiveAuthors = authorDao.getAllAuthorsForStatuses(Status.active);
        
        assertTrue(allDeletedAuthors.size() == 1);
        assertTrue(allDeletedAuthors.contains(deletedAuthor));
        assertTrue(allInactiveAuthors.size() == 1);
        assertTrue(allInactiveAuthors.contains(inactiveAuthor));
        assertTrue(allDeletedAuthors.size() == 1);
        assertTrue(allDeletedAuthors.contains(deletedAuthor));
    }

    /**
     * Test of getPostAuthor method, of class AuthorDaoDB.
     */
    @Test
    public void testGetPostAuthor(List<Post> postList, Body body, Author author) {
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
        
        final List<Post> addedPosts = new ArrayList<>();
        final Set<Post> postSet = new HashSet<Post>(postList);
        for (Post post: postSet) {
            post.setBody(body);
            post.setAuthor(author);
            post.setTags(testTags);
            addedPosts.add(postDao.addPost(post));
        }
        author.setPosts(new ArrayList<>());
        for (Post post: addedPosts) {
            final Author postAuthor =
                    authorDao.getPostAuthor(post.getPostId());
            author.setPosts(postAuthor.getPosts());
            assertEquals(author, postAuthor);
        }
    }
    
}
