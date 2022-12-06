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
     * Test of addPost and getPostById methods, of class PostDaoDB.
     */
    @Test
    public void testAddGetUpdatePost(List<Post> postList, Body body, Author author) {
        
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
    public void testGetAllPosts(List<Post> postList, Body body, Author author) {
        
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
        
        assertEquals(addedPosts.size(), 4);
        final List<Post> allPosts = postDao.getAllPosts();
        for (Post post: allPosts) {
            assertTrue(addedPosts.contains(post));
        }
        
        
        
    }

    /**
     * Test of getPostsForAuthor method, of class PostDaoDB.
     */
    @Test
    public void testGetPostsForAuthor(List<Post> postList, Body body, Author author) {
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
        final Set<Post> postSet = new HashSet<>(postList);
        for (Post post: postSet) {
            post.setBody(body);
            post.setAuthor(author);
            post.setTags(testTags);
            addedPosts.add(postDao.addPost(post));
        }
        
        final List<Post> deletedPostForAuthor = postDao.getPostsForAuthor(author.getAuthorId(), Status.deleted);
        assertTrue(deletedPostForAuthor.size() == 1);
        assertTrue(deletedPostForAuthor.get(0).getStatus() == Status.deleted);
        
        final List<Post> activePostForAuthor = postDao.getPostsForAuthor(author.getAuthorId(), Status.active);
        assertTrue(activePostForAuthor.size() == 1);
        assertTrue(activePostForAuthor.get(0).getStatus() == Status.active);
        
        final List<Post> inactivePostForAuthor = postDao.getPostsForAuthor(author.getAuthorId(), Status.inactive);
        assertTrue(inactivePostForAuthor.size() == 1);
        assertTrue(inactivePostForAuthor.get(0).getStatus() == Status.inactive);
        
        final List<Post> pendingPostForAuthor = postDao.getPostsForAuthor(author.getAuthorId(), Status.pending);
        assertTrue(pendingPostForAuthor.size() == 1);
        assertTrue(pendingPostForAuthor.get(0).getStatus() == Status.pending);
    }

    /**
     * Test of getAllPostsForStatuses method, of class PostDaoDB.
     */
    @Test
    public void testGetAllPostsForStatuses(List<Post> postList, Body body, Author author) {
        
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
        final Set<Post> postSet = new HashSet<>(postList);
        for (Post post: postSet) {
            post.setBody(body);
            post.setAuthor(author);
            post.setTags(testTags);
            addedPosts.add(postDao.addPost(post));
        }
        
        final List<Post> deletedPosts = postDao.getAllPostsForStatuses(Status.deleted);
        assertTrue(deletedPosts.size() == 1);
        assertTrue(deletedPosts.get(0).getStatus() == Status.deleted);
        
        final List<Post> activePosts = postDao.getAllPostsForStatuses(Status.active);
        //Active post is not yet activated so it will not show
        assertTrue(activePosts.size() == 0);
        
        final List<Post> inactivePosts = postDao.getAllPostsForStatuses(Status.inactive);
        assertTrue(inactivePosts.size() == 1);
        assertTrue(inactivePosts.get(0).getStatus() == Status.inactive);
       
        
        final List<Post> pendingPosts = postDao.getAllPostsForStatuses(Status.pending);
        //Pending should not come since it is expired
        assertTrue(pendingPosts.size() == 0);

        final List<Post> allActivatedAndNotExpiredPosts = postDao.getAllPostsForStatuses(Status.active, Status.pending,
                Status.inactive, Status.deleted);
        //Tests that only not expired and posts with the activation date in the past or equl to now
        //Come through
        assertEquals(2, allActivatedAndNotExpiredPosts.size());

        for (Post post: allActivatedAndNotExpiredPosts) {
            assertTrue(addedPosts.contains(post));
        }
    }

    /**
     * Test of getLatestPostsForStatuses method, of class PostDaoDB.
     */
    @Test
    public void testGetLatestPostsForStatuses(List<Post> postList, Body body, Author author) {
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
        final Set<Post> postSet = new HashSet<>(postList);
        for (Post post: postSet) {
            post.setBody(body);
            post.setAuthor(author);
            post.setTags(testTags);
            addedPosts.add(postDao.addPost(post));
        }
        
        
        
        final List<Post> deletedPosts = postDao.getLatestPostsForStatuses(1,Status.deleted);
        assertTrue(deletedPosts.size() == 1);
        assertTrue(deletedPosts.get(0).getStatus() == Status.deleted);
        
        final List<Post> activePosts = postDao.getLatestPostsForStatuses(1,Status.active);
        //Active post is not yet activated so it will not show
        assertTrue(activePosts.size() == 0);
        
        final List<Post> inactivePosts = postDao.getLatestPostsForStatuses(1,Status.inactive);
        assertTrue(inactivePosts.size() == 1);
        assertTrue(inactivePosts.get(0).getStatus() == Status.inactive);
       
        
        final List<Post> pendingPosts = postDao.getLatestPostsForStatuses(1,Status.pending);
        //Pending should not come since it is expired
        assertTrue(pendingPosts.size() == 0);

        final List<Post> latestActivatedAndNotExpiredPosts = postDao.getLatestPostsForStatuses(1,Status.active, Status.pending,
                Status.inactive, Status.deleted);
        //Tests that only not expired and posts with the activation date in the past or equl to now
        //Come through
        assertEquals(1, latestActivatedAndNotExpiredPosts.size());
        final List<Post> latest2ActivatedAndNotExpiredPosts = postDao.getLatestPostsForStatuses(2,Status.active, Status.pending,
                Status.inactive, Status.deleted);
        assertEquals(2, latest2ActivatedAndNotExpiredPosts.size());
        assertTrue(latest2ActivatedAndNotExpiredPosts.get(0).getCreatedAt().compareTo(latest2ActivatedAndNotExpiredPosts.get(1).getCreatedAt()) >= 0);

        for (Post post: latestActivatedAndNotExpiredPosts) {
            assertTrue(addedPosts.contains(post));
        }
    }

    /**
     * Test of getPostsForStatusesByTags method, of class PostDaoDB.
     */
    @Test
    public void testGetPostsForStatusesByTags(List<Post> postList, Body body, Author author) {
        
        
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
        Set<Post> postSet = new HashSet<Post>(postList);
        for (Post post: postSet) {
            post.setBody(body);
            post.setAuthor(author);
            post.setTags(testTags);
            addedPosts.add(postDao.addPost(post));
        }
        
        
        
        final List<Post> deletedPosts = postDao.getPostsForStatusesByTags(testTags,Status.deleted);
        assertTrue(deletedPosts.size() == 2);
        assertTrue(deletedPosts.get(0).getStatus() == Status.deleted);
        
        final List<Post> activePosts = postDao.getPostsForStatusesByTags(testTags,Status.active);
        //Active post is not yet activated so it will not show
        assertTrue(activePosts.size() == 0);
        
        final List<Post> inactivePosts = postDao.getPostsForStatusesByTags(testTags,Status.inactive);
        assertTrue(inactivePosts.size() == 2);
       
        
        final List<Post> pendingPosts = postDao.getPostsForStatusesByTags(testTags,Status.pending);
        //Pending should not come since it is expired
        assertTrue(pendingPosts.size() == 0);

        final List<Post> activatedAndNotExpiredPostsWithTags = postDao.getPostsForStatusesByTags(testTags,Status.active, Status.pending,
                Status.inactive, Status.deleted);
        //Tests that only not expired and posts with the activation date in the past or equl to now
        //Come through
        assertEquals(4, activatedAndNotExpiredPostsWithTags.size());
        assertTrue(activatedAndNotExpiredPostsWithTags.get(0).getCreatedAt().compareTo(
        activatedAndNotExpiredPostsWithTags.get(2).getCreatedAt()) >=0);
        

        for (Post post: activatedAndNotExpiredPostsWithTags) {
            assertTrue(addedPosts.contains(post));
        }
    }


    /**
     * Test of updatePost method, of class PostDaoDB.
     */
    @Test
    public void testUpdatePost(List<Post> postList, Body body, Author author) {
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
        postAdded.setTitle("This has been updated");
        Tag newTag = new Tag();
        newTag.setStatus(Status.active);
        newTag.setTag("This update post has this tag");
        newTag = tagDao.addTag(tag1);
        final List<Tag> postTags = postAdded.getTags();
        postTags.add(newTag);
        postAdded.setTags(postTags);
        postDao.updatePost(postAdded);
        final Post postUpdated = postDao.getPostById(postAdded.getPostId());
        assertTrue(postUpdated.getTags().contains(newTag));
        assertNotEquals(postAdded, postUpdated);
    }

    /**
     * Test of deletePostById method, of class PostDaoDB.
     */
    @Test
    public void testDeletePostById(List<Post> postList, Body body, Author author) {
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
        postDao.deletePost(postAdded);
        final Post postRetrieved = postDao.getPostById(postAdded.getPostId());
        assertNull(postRetrieved);
    }
    
}
